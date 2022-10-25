package com.uang.feipi.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.mapper.DishFlavorMapper;
import com.uang.feipi.poji.DishFlavor;
import com.uang.feipi.service.DishFlavorService;
import com.uang.feipi.service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
