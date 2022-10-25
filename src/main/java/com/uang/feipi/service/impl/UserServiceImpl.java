package com.uang.feipi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.mapper.UserMapper;
import com.uang.feipi.poji.User;
import com.uang.feipi.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
