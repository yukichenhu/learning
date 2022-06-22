package com.chenhu.learning;

import cn.hutool.core.util.ObjectUtil;
import com.chenhu.learning.utils.IpUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 陈虎
 * @date 2022-06-15 8:34
 */
public class HutoolTest {

    @Test
    public void testClone() {
        Map<String, Object> outer = new HashMap<>(1);
        Map<String, String> inner = new HashMap<>(1);
        inner.put("name", "test");
        outer.put("inner", inner);

        Map<String, Object> clone = ObjectUtil.cloneByStream(outer);
        Map<String, Object> clone2 = new HashMap<>(outer);
        inner.put("sex", "男");
        System.out.println(outer);
        System.out.println(clone);
        System.out.println(clone2);
    }

    @SneakyThrows
    @Test
    public void test() {
        System.out.println(InetAddress.getLocalHost().getHostAddress());
        System.out.println(IpUtils.getLocalhostExactAddress("192.168.0").getHostAddress());
        System.out.println(IpUtils.getLocalhostExactAddress("172.23").getHostAddress());
    }
}
