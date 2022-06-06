package com.chenhu.learning.config;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author 陈虎
 * @since 2022-06-06 9:01
 */
public class MyPageRequest extends PageRequest {
    protected MyPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
