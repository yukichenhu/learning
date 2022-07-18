package com.chenhu.learning.task;

import com.chenhu.learning.controller.LeaderElectionService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 陈虎
 * @date 2022-06-16 10:50
 */
@Service
@EnableScheduling
public class AutoTask {
    @Resource
    private LeaderElectionService leaderElectionService;

    //@Scheduled(cron = "${leader-election.cron:0/10 * * * * ?}")
    public void electLeader() {
        try {
            leaderElectionService.electLeader();
        } catch (Exception e) {
            System.out.println("竞选leader失败");
        }
    }
}
