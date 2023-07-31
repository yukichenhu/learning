package com.chenhu.learning.utils;

import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

/**
 * @author chenhu
 * @description
 * @date 2023-01-10 10:45
 */
public class NetUtils {

    public static boolean checkUrl(String url) {
        if (ObjectUtils.isEmpty(url)) {
            return false;
        }
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(1000);
            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static boolean telnet(String ip, int port, int timeout) {
        boolean result;
        Socket s = new Socket();
        try {
            s.connect(new InetSocketAddress(ip, port), timeout);
            result = true;
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
}
