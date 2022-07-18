package com.chenhu.learning.proxy;

/**
 * @author 陈虎
 * @date 2022-06-23 14:45
 */
public class Renter implements Tenant{
    @Override
    public void rentHouse() {
        System.out.println("租房成功！");
    }

    @Override
    public void returnHouse() {
        System.out.println("退房成功！");
    }
}
