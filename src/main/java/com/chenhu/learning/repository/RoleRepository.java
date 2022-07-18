package com.chenhu.learning.repository;

import com.chenhu.learning.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author 陈虎
 * @date 2022-06-24 9:33
 */
public interface RoleRepository extends JpaRepository<Role,Integer> {
    /**
     * 查询用户所有角色
     * @param userId 用户id
     * @return 角色列表
     */
    @Query(nativeQuery = true,value = "select r.* from t_user_role ur join t_role r on ur.role_id=r.role_id where ur.user_id=?1")
    List<Role> findRoles(Long userId);
}
