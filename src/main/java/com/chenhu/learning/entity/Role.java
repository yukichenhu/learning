package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

/**
 * @author 陈虎
 * @date 2022-06-23 16:31
 */
@Data
@Entity
@Table(name = "t_role")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;
    private String roleName;
    private String des;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
