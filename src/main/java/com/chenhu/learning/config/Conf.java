package com.chenhu.learning.config;

/**
 * @author 陈虎
 * @description
 * @date 2022-07-08 15:11
 */
public interface Conf {
    void refresh();
    String getWord(String key);
}
