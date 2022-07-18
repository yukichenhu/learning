package com.chenhu.learning.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author 陈虎
 * @date 2022-06-24 14:43
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(MyException.class)
    public Object handle(Exception e){
        return e.getMessage();
    }
}
