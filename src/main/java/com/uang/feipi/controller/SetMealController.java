package com.uang.feipi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uang.feipi.common.R;
import com.uang.feipi.poji.Category;
import com.uang.feipi.poji.Setmeal;
import com.uang.feipi.poji.SetmealDto;
import com.uang.feipi.service.CategoryService;
import com.uang.feipi.service.SetMealDishService;
import com.uang.feipi.service.SetMealService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/setmeal")
public class SetMealController {
    @Autowired
    private SetMealDishService setMealDishService;

    @Autowired
    private SetMealService setMealService;

    @Autowired
    private CategoryService categoryService;

    @CacheEvict(value = "setMealCache", allEntries = true)
    @PostMapping
    public R<String> save (@RequestBody SetmealDto setmealDto){
        setMealService.saveWithDish(setmealDto);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    private R<Page<SetmealDto>> page(int page, int pageSize, String name){
        Page<Setmeal> setmealPage = new Page<>();
        Page<SetmealDto> setmealDtoPage = new Page<>();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Setmeal::getName, name);
        setmealLambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setMealService.page(setmealPage, setmealLambdaQueryWrapper);
        BeanUtils.copyProperties(setmealPage, setmealDtoPage, "records");
        List<SetmealDto> setmealDtos = setmealPage.getRecords().stream().map(item -> {
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());
        setmealDtoPage.setRecords(setmealDtos);
        return R.success(setmealDtoPage);
    }
    @CacheEvict(value = "setMealCache", allEntries = true)
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        setMealService.removeWithDish(ids);
        return R.success("删除成功");
    }
    @Cacheable(value = "setMealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId()).
                eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        List<Setmeal> list = setMealService.list(setmealLambdaQueryWrapper);
        return R.success(list);
    }


}
