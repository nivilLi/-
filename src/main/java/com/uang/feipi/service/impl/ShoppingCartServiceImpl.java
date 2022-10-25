package com.uang.feipi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.mapper.ShoppingCartMapper;
import com.uang.feipi.poji.ShoppingCart;
import com.uang.feipi.service.ShoppingCartService;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

}
