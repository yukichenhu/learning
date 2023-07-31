package com.chenhu.learning;

import com.chenhu.learning.utils.NetUtils;
import org.junit.jupiter.api.Test;

/**
 * @author chenhu
 * @description
 * @date 2023-01-10 10:57
 */
public class NetUtilsTest {

    @Test
    public void testUrl(){
        System.out.println(NetUtils.checkUrl("http://192.168.0.127:52116/v3/api-docs"));
        System.out.println(NetUtils.checkUrl("http://192.168.0.127:57608/v3/api-docs"));
    }

    @Test
    public void testTelnet(){
        System.out.println(NetUtils.telnet("192.168.0.127",52116,2000));
        System.out.println(NetUtils.telnet("192.168.0.127",57608,2000));
    }
}
