package com.chenhu.learning.repository;

import com.chenhu.learning.entity.EventInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author chenhu
 * @description
 * @date 2023-08-25 10:32
 */
public interface EventInfoRepository extends JpaRepository<EventInfo, String> {

    @Query(nativeQuery = true, value = "select user_id from cdp_user_info order by rand() limit 500")
    List<String> randomUserIds();
}
