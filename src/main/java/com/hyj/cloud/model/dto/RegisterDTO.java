package com.hyj.cloud.model.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Data
public class RegisterDTO {

    @NotEmpty(message = "请输入账号")
    @Length(min = 2, max = 15, message = "用户名长度在2-15")
    private String username;

    @NotEmpty(message = "请输入密码")
    @Length(min = 5, max = 20, message = "密码长度在5-20")
    private String password;

    @NotEmpty(message = "请输入姓名")
    private String name;

    @NotEmpty(message = "请输入手机号")
    private String phone;
    @NotEmpty(message = "请再次输入密码")
    @Length(min = 5, max = 20, message = "密码长度在5-20")
    private String checkPass;


}
