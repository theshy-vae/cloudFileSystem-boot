package com.hyj.cloud.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserVO {
    private String username;
    private String token;
    private String avatar;
    private boolean isAdmin;
}
