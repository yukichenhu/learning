package com.chenhu.learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author 陈虎
 * @since 2022-06-02 14:51
 */
@Configuration
@EnableJpaAuditing
public class JpaConfig {

    @Bean
    MyAuditorAware myAuditorAware() {
        return new MyAuditorAware();
    }
}
