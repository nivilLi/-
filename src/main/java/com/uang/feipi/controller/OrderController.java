package com.uang.feipi.controller;

import com.uang.feipi.common.R;
import com.uang.feipi.poji.Orders;
import com.uang.feipi.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/submit")
    public R<String> order(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("下单成功");
    }
}
