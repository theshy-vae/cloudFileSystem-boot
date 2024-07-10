package com.hyj.cloud.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

    String userId;
    boolean isAdmin;

}
