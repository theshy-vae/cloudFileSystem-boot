package com.hyj.cloud.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hyj.cloud.model.dto.LoginDTO;
import com.hyj.cloud.model.dto.PageDTO;
import com.hyj.cloud.model.dto.PassDTO;
import com.hyj.cloud.model.dto.RegisterDTO;
import com.hyj.cloud.model.po.User;
import com.hyj.cloud.model.query.UserQuery;
import com.hyj.cloud.model.vo.UserVO;

public interface UserService extends IService<User> {
    public User executeRegister(RegisterDTO dto);

    public User getUserByUsername(String username);

    public boolean isAdmin(String userId);
    UserVO executeLogin(LoginDTO dto);

    PageDTO<User> getAllUserList(UserQuery query);

    boolean resetPass(PassDTO dto);
}
