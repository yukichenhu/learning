package com.chenhu.learning.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author 陈虎
 * @since 2022-06-02 14:49
 */
public class MyAuditorAware implements AuditorAware<Integer> {
    @NotNull
    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.of(1);
    }
}
