package com.chenhu.learning.advice;

import com.chenhu.learning.annotation.AuthAop;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 陈虎
 * @date 2022-07-05 14:58
 */
@ConditionalOnMissingBean(AuthAop.class)
@Configuration
public class InterceptorConfig {
    private final String execution="execution(* com.chenhu.learning.controller.*.*(..))";

    @Bean
    public DefaultPointcutAdvisor defaultPointcutAdvisor(){
        RequestInterceptor interceptor=new RequestInterceptor();
        AspectJExpressionPointcut pointcut=new AspectJExpressionPointcut();
        pointcut.setExpression(execution);

        DefaultPointcutAdvisor pointcutAdvisor=new DefaultPointcutAdvisor();
        pointcutAdvisor.setPointcut(pointcut);
        pointcutAdvisor.setAdvice(interceptor);
        return pointcutAdvisor;
    }
}
