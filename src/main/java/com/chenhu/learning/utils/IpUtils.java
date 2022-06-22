package com.chenhu.learning.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author 陈虎
 * @date 2022-06-17 11:49
 */
@Slf4j
public class IpUtils {
    private IpUtils(){

    }

    /**
     * 获取本地ip地址
     * @param preferIps 优先的ip地址 （可选）
     * @return 本地ip地址
     */
    public static InetAddress getLocalhostExactAddress(String... preferIps){
        try {
            InetAddress inetAddress = null;
            //所有所有网卡
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                //获取网卡下所有的ip，找到想要的
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress current = inetAddresses.nextElement();
                    //先判断是否为自定义prefer ip
                    if (containsIp(preferIps, current.getHostAddress())) {
                        return current;
                    }
                    //非回环地址
                    if(current.isLoopbackAddress()){
                        continue;
                    }
                    if (current.isSiteLocalAddress() && ObjectUtils.isEmpty(inetAddress)) {
                        inetAddress = current;
                    }
                }
            }
            return ObjectUtils.isEmpty(inetAddress) ? InetAddress.getLocalHost() : inetAddress;
        } catch (Exception e) {
            log.debug("获取ip出错！");
        }
        //不存在就用回环地址
        return InetAddress.getLoopbackAddress();
    }

    private static boolean containsIp(String[] preferIps,String ip){
        for (String preferIp : preferIps) {
            //排除空的情况
            if(ObjectUtils.isEmpty(preferIp)){
                continue;
            }
            if(ip.contains(preferIp)){
                return true;
            }
        }
        return false;
    }
}
