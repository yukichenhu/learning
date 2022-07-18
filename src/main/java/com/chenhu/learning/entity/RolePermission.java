package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * @author 陈虎
 * @date 2022-06-23 16:36
 */
@Data
@Entity
@Table(name = "t_role_permission")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long relateId;
    private Integer roleId;
    private Integer permissionId;
}
