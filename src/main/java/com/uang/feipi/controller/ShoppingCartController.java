package com.uang.feipi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uang.feipi.common.BaseContext;
import com.uang.feipi.common.R;
import com.uang.feipi.poji.ShoppingCart;
import com.uang.feipi.poji.User;
import com.uang.feipi.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart ){
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
        shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        shoppingCartLambdaQueryWrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        shoppingCartLambdaQueryWrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(shoppingCartLambdaQueryWrapper);
        if(shoppingCart1 == null){
            shoppingCart.setNumber(1);
            shoppingCartService.save(shoppingCart);
        }else {
            shoppingCart.setNumber(shoppingCart1.getNumber() + 1);
            shoppingCartService.updateById(shoppingCart1);
        }
        return R.success(shoppingCart);
        }

        @GetMapping("/list")
        public R<List<ShoppingCart>> list(){
            LambdaQueryWrapper<ShoppingCart> shoppingCartLambdaQueryWrapper = new LambdaQueryWrapper<>();
            shoppingCartLambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            shoppingCartLambdaQueryWrapper.orderByDesc(ShoppingCart::getCreateTime);
            List<ShoppingCart> shoppingCarts = shoppingCartService.list(shoppingCartLambdaQueryWrapper);
            return R.success(shoppingCarts);
        }

        @DeleteMapping("/clean")
        public R<String> clean(){
            LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
            shoppingCartService.remove(wrapper);
            return R.success("删除成功");
        }

    }

