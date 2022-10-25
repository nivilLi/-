package com.uang.feipi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.common.CustomException;
import com.uang.feipi.mapper.SetMealMapper;
import com.uang.feipi.poji.Setmeal;
import com.uang.feipi.poji.SetmealDish;
import com.uang.feipi.poji.SetmealDto;
import com.uang.feipi.service.SetMealDishService;
import com.uang.feipi.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, Setmeal> implements SetMealService {

    @Autowired
    private SetMealDishService setMealDishService;

    public void saveWithDish(SetmealDto setmealDto){
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes().stream().map(item -> {
            Long id = setmealDto.getId();
            item.setSetmealId(id);
            return item;
        }).collect(Collectors.toList());
        setMealDishService.saveBatch(setmealDishes);
    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.in(Setmeal::getCategoryId, ids);
        setmealLambdaQueryWrapper.eq(Setmeal::getStatus, 1);
        int count = this.count(setmealLambdaQueryWrapper);
        if (count != 0){
            throw new CustomException("套餐正在售卖中, 不能删除");
        }
        removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> setmealDishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealDishLambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setMealDishService.remove(setmealDishLambdaQueryWrapper);
    }
}
