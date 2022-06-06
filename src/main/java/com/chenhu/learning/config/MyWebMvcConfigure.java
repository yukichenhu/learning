package com.chenhu.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 陈虎
 * @since 2022-06-06 9:28
 */
@Configuration
public class MyWebMvcConfigure implements WebMvcConfigurer {
    @Resource
    private MyPageHandlerMethodArgResolver myPageHandlerMethodArgResolver;
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(myPageHandlerMethodArgResolver);
    }
}
