package com.chenhu.learning.repository;

import com.chenhu.learning.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 陈虎
 * @date 2022-06-07 17:37
 */
public interface PositionRepository extends JpaRepository<Position, Integer> {

    @Query(nativeQuery = true,value = "update t_position set update_time=?1 where position_id=?2")
    @Modifying
    @Transactional
    int heartBeat(Long updateTime, Long positionId);
}
