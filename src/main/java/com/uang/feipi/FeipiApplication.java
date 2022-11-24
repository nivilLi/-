package com.uang.feipi;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@EnableCaching
@EnableTransactionManagement
@ServletComponentScan("com.uang.feipi.filter")
@SpringBootApplication
@MapperScan(basePackages = "com.uang.feipi.mapper")
public class FeipiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeipiApplication.class, args);
    }

}
