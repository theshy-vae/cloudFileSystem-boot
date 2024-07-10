package com.hyj.cloud.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PassDTO {
    private String userId;
    @NotBlank(message = "旧密码不能为空")
    private String oldPass;
    @NotBlank(message = "新密码不能为空")
    private String newPass;
}
