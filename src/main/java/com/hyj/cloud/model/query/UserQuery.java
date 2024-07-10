package com.hyj.cloud.model.query;

import lombok.Data;

@Data
public class UserQuery extends PageQuery{
    private String searchKey;
}
