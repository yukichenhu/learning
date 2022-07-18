package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 陈虎
 */
@Data
@Entity
@Table(name = "t_user")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GenericGenerator(name = "snowId", strategy = "com.chenhu.learning.config.SnowIdGenerator")
    @GeneratedValue(generator = "snowId")
    private Long id;
    private String name;
    private String email;
    private String contactPhone;
    private Integer positionId;
    private String pwd;
}
