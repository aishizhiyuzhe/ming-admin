package com.ming.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ming.entity.Users;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<Users> {
}
