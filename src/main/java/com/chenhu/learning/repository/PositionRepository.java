package com.chenhu.learning.repository;

import com.chenhu.learning.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 陈虎
 * @date 2022-06-07 17:37
 */
public interface PositionRepository extends JpaRepository<Position, Integer> {
}
