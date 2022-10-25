package com.uang.feipi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uang.feipi.poji.Orders;

public interface OrdersService extends IService<Orders> {
    public void submit(Orders orders);
}
