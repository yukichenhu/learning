package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author 陈虎
 * @date 2022-06-23 16:35
 */
@Data
@Entity
@Table(name = "t_user_role")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relateId;
    private Long userId;
    private Integer roleId;
}
