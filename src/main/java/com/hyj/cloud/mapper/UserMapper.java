package com.hyj.cloud.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hyj.cloud.model.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
