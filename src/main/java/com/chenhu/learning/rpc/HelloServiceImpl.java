package com.chenhu.learning.rpc;

import org.springframework.util.ObjectUtils;

/**
 * @author 陈虎
 * @description
 * @date 2022-10-13 9:25
 */
public class HelloServiceImpl implements HelloService{
    @Override
    public String hello(String msg) {
        if(ObjectUtils.isEmpty(msg)){
            return "msg is empty";
        }
        return "rpc resp msg:"+msg;
    }

    @Override
    public String log(String msg) {
        return "log success:"+msg;
    }
}
