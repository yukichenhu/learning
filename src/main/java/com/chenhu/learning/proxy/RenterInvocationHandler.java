package com.chenhu.learning.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author 陈虎
 * @date 2022-06-23 14:47
 */
public class RenterInvocationHandler<T> implements InvocationHandler {

    private final T target;

    public RenterInvocationHandler(T target){
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
        System.out.println("租客与中介交流");
        Object result=method.invoke(target,args);
        System.out.println("操作完成");
        return result;
    }
}
