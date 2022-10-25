package com.uang.feipi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.uang.feipi.poji.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {

}
