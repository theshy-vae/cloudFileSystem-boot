package com.hyj.cloud.model.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@Builder
@TableName("user")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class User implements Serializable {
    private static final long serialVersionUID = -5051120337175047163L;

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField("username")
    private String username;

    @TableField("name")
    private String name;

    @TableField("phone")
    private String phone;

    @JsonIgnore()
    @TableField("password")
    private String password;

    @Builder.Default
    @TableField("avatar")
    private String avatar = "https://img0.baidu.com/it/u=2250892508,1709900654&fm=253&app=138&size=w931&n=0&f=JPEG&fmt=auto?sec=1682182800&t=0deb4ec7ec47af4937dd5d56ad9cf9c9";



    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "modify_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime modifyTime;
}