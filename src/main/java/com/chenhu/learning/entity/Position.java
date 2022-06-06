package com.chenhu.learning.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author 陈虎
 * @since 2022-06-01 13:56
 */
@Data
@Entity
@Table(name = "t_position")
public class Position {
    @Id
    @Column(name = "position_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer positionId;

    private Integer deptId;

    private String positionName;
}
