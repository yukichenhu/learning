package com.chenhu.learning.database;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-19 15:51
 */
@Data
@SuperBuilder
public class DatabaseInfo {
    private String driverClass;
    private String driverFileUrl;
    private String url;
    private String username;
    private String password;
}
