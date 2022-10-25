package com.uang.feipi.service.impl;

import com.uang.feipi.mapper.EmployeeMapper;
import com.uang.feipi.poji.Employee;
import com.uang.feipi.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
