package com.chenhu.learning.repository;

import com.chenhu.learning.entity.SqlApiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 陈虎
 * @description
 * @date 2022-09-20 10:39
 */
public interface SqlApiRepository extends JpaRepository<SqlApiEntity,Integer> {
    SqlApiEntity findByApiPath(String apiPath);
}
