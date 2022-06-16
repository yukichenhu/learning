package com.chenhu.learning.repository;

import com.chenhu.learning.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;


/**
 * @author 陈虎
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor {
    @Query(value = "select name,email from t_user where name=?1", nativeQuery = true)
    List<Map> findByName(String name);

    @Query("select u from User u where u.name=:name")
    Page<User> findByName(@Param("name") String name, Pageable pageable);

    @Query(nativeQuery = true, value = "select u.* from t_user u join t_position p on u.position_id=p.position_id " +
            "where p.position_id=1")
    Page<User> findALL(Pageable pageable);
}
