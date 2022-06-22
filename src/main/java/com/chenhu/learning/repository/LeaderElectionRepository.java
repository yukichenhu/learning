package com.chenhu.learning.repository;

import com.chenhu.learning.entity.LeaderElection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 陈虎
 * @date 2022-06-15 17:38
 */
public interface LeaderElectionRepository extends JpaRepository<LeaderElection, String> {
    int countByServiceIdAndLeaderId(String serviceId, String leaderId);
}
