package com.chenhu.learning;

import cn.hutool.core.util.RuntimeUtil;
import com.chenhu.learning.utils.OsUtils;
import lombok.SneakyThrows;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.ExecuteWatchdog;
import org.junit.jupiter.api.Test;

/**
 * @author 陈虎
 * @date 2022-06-20 13:46
 */
public class OsTest {
    @Test
    @SneakyThrows
    public void test(){
        System.out.println(OsUtils.shellWithResult(new CommandLine("ipconfig"), new ExecuteWatchdog(10000)));
    }

    @Test
    @SneakyThrows
    public void test2(){
        System.out.println(RuntimeUtil.execForStr("cmd /c dir"));
    }
}
