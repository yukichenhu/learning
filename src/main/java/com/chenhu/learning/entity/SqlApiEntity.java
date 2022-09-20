package com.chenhu.learning.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-20 10:34
 */
@Data
@Entity
@Table(name = "t_sql_api")
public class SqlApiEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String apiPath;
    private String apiName;
    private String querySql;
    private Integer datasourceId;
}
