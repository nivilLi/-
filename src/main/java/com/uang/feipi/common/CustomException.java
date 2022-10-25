package com.uang.feipi.common;

import org.apache.commons.lang.StringUtils;

public class CustomException extends RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
