package com.uang.feipi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.uang.feipi.common.R;
import com.uang.feipi.poji.User;
import com.uang.feipi.service.UserService;
import com.uang.feipi.utils.SMSUtils;
import com.uang.feipi.utils.ValidateCodeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/sendMsg")
    public R<String> sendMessage(@RequestBody User user, HttpSession httpSession){
        String phone = user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            String code = ValidateCodeUtils.generateValidateCode4String(4);
            System.out.println(code);
//            SMSUtils.sendMessage("吊人外卖", "", phone, code);
            httpSession.setAttribute(phone, code);
            return R.success("验证码发送成功");
        }
        return R.success("短信发送失败");
    }
    @RequestMapping("/login")
    public R<String> login(@RequestBody Map<String, String> map, HttpSession httpSession) {
//        String code1 = (String)httpSession.getAttribute("phone");
//        String code = map.get("code");
//        if(code1 != null && code.equals(code1)){
//            LambdaQueryWrapper<User> uSerLambdaQueryWrapper = new LambdaQueryWrapper<>();
//            uSerLambdaQueryWrapper.eq(User::getPhone, map.get("phone"));
//            User user = userService.getOne(uSerLambdaQueryWrapper);
//            if(user == null){
//                User user1 = new User();
//                user.setPhone(map.get("phone"));
//                user.setStatus(1);
//                userService.save(user);
//            }
//            httpSession.setAttribute("user", user.getId());
//            return R.success("登录成功");
//        }
//        return R.error("登录失败");
//    }
        LambdaQueryWrapper<User> uSerLambdaQueryWrapper = new LambdaQueryWrapper<>();
            uSerLambdaQueryWrapper.eq(User::getPhone, map.get("phone"));
            User user = userService.getOne(uSerLambdaQueryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(map.get("phone"));
                user.setStatus(1);
                userService.save(user);
            }
            httpSession.setAttribute("user", user.getId());
        return R.success("登陆成功");
    }
}
