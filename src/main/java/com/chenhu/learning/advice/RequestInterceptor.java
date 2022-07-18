package com.chenhu.learning.advice;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author 陈虎
 * @date 2022-07-05 14:41
 */

@Slf4j
public class RequestInterceptor implements MethodInterceptor {
    @Nullable
    @Override
    public Object invoke(@NotNull MethodInvocation invocation) throws Throwable {
        TimeInterval interval= DateUtil.timer();
        log.info("方法调用前");
        Object result=invocation.proceed();
        log.info("方法调用完成，耗时{}毫秒",interval.intervalMs());
        return result;
    }
}
