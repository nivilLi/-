package com.uang.feipi.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uang.feipi.common.R;
import com.uang.feipi.poji.Employee;
import com.uang.feipi.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.accept.ParameterContentNegotiationStrategy;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request,  @RequestBody Employee employee){
        String password = employee.getPassword();
//        这个api以前见过
        String md5 = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Employee::getUsername, employee.getUsername());
        Employee employee1 = employeeService.getOne(wrapper);
        if(employee1 == null){
            return R.error("用户名不存在");
        }
        if(!md5.equals(employee1.getPassword())){
            return R.error("密码错误");
        }
        if(employee1.getStatus() == 0){
            return R.error("账号已被锁定");
        }
        request.getSession().setAttribute("employee", employee1.getId());
        return R.success(employee1);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }


    @PostMapping
    public R<String> save(@RequestBody Employee employee, HttpServletRequest request){
        log.info(employee.toString());
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.save(employee);
        return R.success("新增员工成功");
    }

    /**
     * 员工信息查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(int page, int pageSize, String name) {
        log.info("page={}, pageSize = {}, name= {}", page, pageSize, name);
        Page<Employee> employeePage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(name), Employee::getUsername, name);
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);
        Page<Employee> pageInfo = employeeService.page(employeePage, lambdaQueryWrapper);
        return R.success(pageInfo);
    }


    @PutMapping
    public R<String> updateStatues(HttpServletRequest request, @RequestBody Employee employee){
        log.info(employee.toString());
        Object empId = request.getSession().getAttribute("employee");
//        employee.setUpdateUser((Long) empId);
//        employee.setUpdateTime(LocalDateTime.now());
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getByID(@PathVariable("id")String empId){
        Employee employee = employeeService.getById(empId);
        if (employee == null){
            return R.error("获取用户信息失败");
        }
        return R.success(employee);
    }


}
