/*
 *
 *  *
 *  *  * Copyright (c) 2022. Jiangsu Hongwangweb Technology Co.,Ltd.
 *  *  * Licensed under the private license, you may not use this file except you get the License.
 *  *
 *
 */

package com.chenhu.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-29 10:24
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
               .allowedOrigins("*")
               .allowedHeaders("*")
               .allowedMethods("*");
    }
}
