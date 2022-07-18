package com.chenhu.learning.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author 陈虎
 * @date 2022-06-23 14:57
 */
public class ProxyTest {
    public static void main(String[] args) {
        Tenant renter=new Renter();
        InvocationHandler handler=new RenterInvocationHandler<>(renter);

        Tenant renterProxy= (Tenant) Proxy.newProxyInstance(Tenant.class.getClassLoader(),new Class[]{Tenant.class},handler);
        renterProxy.rentHouse();
        renterProxy.returnHouse();
    }
}
