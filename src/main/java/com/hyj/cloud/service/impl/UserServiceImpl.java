package com.hyj.cloud.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hyj.cloud.config.AppConfig;
import com.hyj.cloud.mapper.UserMapper;
import com.hyj.cloud.model.dto.LoginDTO;
import com.hyj.cloud.model.dto.PageDTO;
import com.hyj.cloud.model.dto.PassDTO;
import com.hyj.cloud.model.dto.RegisterDTO;
import com.hyj.cloud.model.po.*;
import com.hyj.cloud.model.query.UserQuery;
import com.hyj.cloud.model.vo.UserVO;
import com.hyj.cloud.pojo.BizException;
import com.hyj.cloud.service.UserService;
import com.hyj.cloud.utils.MD5Utils;
import com.hyj.cloud.utils.UserContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

import static com.hyj.cloud.utils.JwtTokenUtil.createToken;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final AppConfig appConfig;

    @Override
    public User executeRegister(RegisterDTO dto) {
        LambdaQueryWrapper<User> wrapper=new LambdaQueryWrapper<User>().eq(User::getUsername,dto.getUsername());
        User user = baseMapper.selectOne(wrapper);
        User addUser=null;
        if(user !=null){
            System.out.println("账号已存在！");
        }else {
            addUser= User.builder()
                    .username(dto.getUsername())
                    .password(MD5Utils.getPwd(dto.getPassword()))
                    .name(dto.getName())
                    .phone(dto.getPhone())
                    .createTime(LocalDateTime.now())
                    .build();
            baseMapper.insert(addUser);
        }
        return addUser;
    }

    @Override
    public User getUserByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUsername, username));
    }

    @Override
    public boolean isAdmin(String userId) {
        User one = getOne(new LambdaQueryWrapper<User>().eq(User::getId, userId));
        if(one==null){
            throw new BizException("用户不存在");
        }
        if(appConfig.getAdminUsername().equals(one.getUsername()))
            return true;
        return false;
    }

    @Override
    public UserVO executeLogin(LoginDTO dto) {
        String token=null;
        boolean isAdmin = false;
        User user = null;
        try {
            user = getUserByUsername(dto.getUsername());
            String encodePwd = MD5Utils.getPwd(dto.getPassword());
            if(!encodePwd.equals(user.getPassword())){
                throw new BizException("用户名或密码错误");
            }

            if(dto.getUsername().equals(appConfig.getAdminUsername()))
                isAdmin=true;
            token= createToken(user.getId());
        } catch (Exception e) {
            log.warn("用户名或密码错误",dto.getUsername());
            throw new BizException("用户名或密码错误");
        }
        return UserVO.builder().username(user.getUsername()).avatar(user.getAvatar()).token(token).isAdmin(isAdmin).build();
    }

    @Override
    public PageDTO<User> getAllUserList(UserQuery query) {
        Page<User> page = lambdaQuery().like(User::getName,query.getSearchKey()).page(query.toMpPageDefaultSortByCreateTimeDesc());
        return PageDTO.of(page,page.getRecords());
    }

    @Override
    public boolean resetPass(PassDTO dto) {
        String userId = UserContext.getUser();
        boolean isAdmin = isAdmin(userId);

        if(isAdmin)
            userId = dto.getUserId();
        User user = getById(userId);
        if(user==null)
            throw new BizException("用户不存在");
        if(!isAdmin){
            String encodePwd = MD5Utils.getPwd(dto.getOldPass());
            if(!user.getPassword().equals(encodePwd))
                throw new BizException("密码错误");
        }
        user.setPassword(MD5Utils.getPwd(dto.getNewPass()));
        updateById(user);
        return true;
    }
}
