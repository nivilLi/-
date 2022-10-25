package com.uang.feipi.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.mapper.OrderDetailMapper;
import com.uang.feipi.poji.OrderDetail;
import com.uang.feipi.service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceIMpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
