package com.chenhu.learning.database;

import lombok.Data;

import java.util.List;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-19 16:14
 */
@Data
public class QuerySql {
    private String sql;
    private List<String> paramNames;
}
