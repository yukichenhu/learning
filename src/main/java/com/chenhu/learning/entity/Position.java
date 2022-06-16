package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

/**
 * @author 陈虎
 * @since 2022-06-01 13:56
 */
@Data
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_position")
@EntityListeners(AuditingEntityListener.class)
public class Position {
    @Id
    @Column(name = "position_id")
    @GenericGenerator(name = "snowId", strategy = "com.chenhu.learning.config.SnowIdGenerator")
    @GeneratedValue(generator = "snowId")
    private Long positionId;

    private Integer deptId;

    private String positionName;

    @LastModifiedDate
    private Long updateTime;
}
