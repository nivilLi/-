package com.uang.feipi.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLInvalidAuthorizationSpecException;
@Slf4j
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@RestController
public class GlobleExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.info(ex.getMessage());
        String message = "未知错误";
        if (ex.getMessage().contains("Duplicate entry")){
            message = ex.getMessage().split(" ")[2] + "已存在";
        }
        return R.error(message);
    }


    @ExceptionHandler(CustomException.class)
    public R<String> CustomExceptionHandler(CustomException ex){
        log.info(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
