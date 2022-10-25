package com.uang.feipi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uang.feipi.poji.Dish;
import com.uang.feipi.poji.DishDto;

public interface DishService extends IService<Dish> {
    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    public void updateWithFlavor(DishDto dishDto);
}
