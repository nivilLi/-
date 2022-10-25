package com.uang.feipi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uang.feipi.poji.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
