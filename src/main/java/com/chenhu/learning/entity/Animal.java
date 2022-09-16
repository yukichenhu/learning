package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author 陈虎
 * @description
 * @date 2022-08-18 9:05
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    private String name;
    private Detail detail;

    @Data
    public static final class Detail{
        private Integer age;
    }
}
