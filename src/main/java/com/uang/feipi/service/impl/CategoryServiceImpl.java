package com.uang.feipi.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uang.feipi.common.CustomException;
import com.uang.feipi.mapper.CategoryMapper;
import com.uang.feipi.poji.Category;
import com.uang.feipi.poji.Dish;
import com.uang.feipi.poji.Setmeal;
import com.uang.feipi.service.CategoryService;
import com.uang.feipi.service.DishService;
import com.uang.feipi.service.SetMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;

    @Autowired
    private SetMealService setMealService;



    public boolean removeById(Serializable id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if(count != 0){
            throw new CustomException("关联了菜品不能删除");
        }
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setMealService.count(setmealLambdaQueryWrapper);
        if(count1 != 0){
            throw new CustomException("关联了套餐不能删除");
        }
       return super.removeById(id);
    }
}
