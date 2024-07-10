package com.hyj.cloud.controller;

import com.hyj.cloud.annotation.ResponseResult;
import com.hyj.cloud.model.dto.*;
import com.hyj.cloud.model.po.User;
import com.hyj.cloud.model.query.UserQuery;
import com.hyj.cloud.model.vo.UserVO;
import com.hyj.cloud.pojo.BizException;
import com.hyj.cloud.service.UserService;
import com.hyj.cloud.utils.JwtTokenUtil;
import com.hyj.cloud.utils.UserContext;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@ResponseResult
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @PostMapping("/verifyToken")
    public boolean verifyToken(@RequestBody VerifyDTO dto){
        try {
            // 2.判断是否为空
            if(StringUtils.isEmpty(dto.getToken())){
                return false;
            }
            // 3.转为用户id并保存
            String userId = JwtTokenUtil.getUserIdByToken(dto.getToken());
            UserContext.setUser(userId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    //删除用户
    @PostMapping("/register")
    public User register(@RequestBody @Valid RegisterDTO dto){
        User user = userService.executeRegister(dto);
        if(user==null){
            throw new BizException("用户名已存在！");
        }
        return user;
    }

    @PostMapping("/login")
    public UserVO login(@RequestBody @Valid LoginDTO dto){
        return userService.executeLogin(dto);
    }


    @PostMapping("/getAllUserList")
    public PageDTO<User> getAllUserList(@RequestBody UserQuery query){
        return userService.getAllUserList(query);
    }

    @PostMapping("/resetPass")
    public boolean resetPass(@RequestBody PassDTO dto){
        return userService.resetPass(dto);
    }
}
