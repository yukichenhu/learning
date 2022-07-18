package com.chenhu.learning.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 陈虎
 * @date 2022-06-24 14:41
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyException extends RuntimeException {
    private String msg;

    public MyException(String msg){
        super(msg);
    }
}
