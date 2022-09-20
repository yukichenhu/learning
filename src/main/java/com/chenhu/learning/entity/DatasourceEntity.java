package com.chenhu.learning.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-20 10:30
 */
@Data
@Entity
@Table(name = "t_datasource")
public class DatasourceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String driverClass;
    private String driverFileUrl;
    private String url;
    private String username;
    private String password;
}
