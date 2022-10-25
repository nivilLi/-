package com.uang.feipi.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.uang.feipi.poji.Setmeal;
import com.uang.feipi.poji.SetmealDto;

import java.util.List;

public interface SetMealService extends IService<Setmeal> {

    public void saveWithDish(SetmealDto setmealDto);

    public void removeWithDish(List<Long> ids);

}
