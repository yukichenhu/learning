package com.chenhu.learning.controller;

import com.chenhu.learning.entity.LeaderElection;
import com.chenhu.learning.repository.LeaderElectionRepository;
import com.chenhu.learning.utils.PropertyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Optional;

/**
 * @author 陈虎
 * @date 2022-06-15 17:39
 */
@Slf4j
@RestController
@RequestMapping("leader")
public class LeaderElectionService {
    @Resource
    private ApplicationContext applicationContext;
    @Resource
    private LeaderElectionRepository electionRepository;
    @Value("${leader-election.timeout-sec}")
    private Integer timeoutSec = 20;
    private final String serviceName = "hmesh-manager";

    /**
     * 生成leaderId  ${hostIp}_${pid}
     *
     * @return leaderId
     */
    private String getLeaderId() {
        String hostIp;
        String pId = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
            return hostIp + "_" + pId;
        } catch (UnknownHostException e) {
            log.info("unknown host");
            return "unknown host" + "_" + pId;
        }
    }

    /**
     * 获取本机url ip:port+context-path
     *
     * @return 本机访问url
     */
    private String getLeaderUrl() {
        String hostIp;
        Environment env = applicationContext.getEnvironment();
        String port = env.getProperty("server.port");
        if (ObjectUtils.isEmpty(port)) {
            port = "8080";
        }
        String contextPath = env.getProperty("server.servlet.context-path");
        if (ObjectUtils.isEmpty(contextPath)) {
            contextPath = "/";
        }
        try {
            hostIp = InetAddress.getLocalHost().getHostAddress();
            return "http://" + hostIp + ":" + port + contextPath;
        } catch (UnknownHostException e) {
            return "unknown host:" + port + contextPath;
        }
    }

    /**
     * 选举leader
     */
    @PostMapping("electLeader")
    public void electLeader() {
        Optional<LeaderElection> leader = electionRepository.findById(serviceName);
        LeaderElection leaderElection = LeaderElection.builder()
                .serviceId(serviceName)
                .leaderId(getLeaderId())
                .leaderUrl(getLeaderUrl())
                .lastActiveTime(new Date())
                .build();
        //不存在或者leader为自身，直接插入
        if (!leader.isPresent()) {
            electionRepository.save(leaderElection);
            log.info("{} i am leader now", new Date());
        } else if (leaderElection.getLeaderId().equals(leader.get().getLeaderId())) {
            leader.get().setLastActiveTime(new Date());
            electionRepository.save(leader.get());
        } else {
            //判断是否超时
            long currentTime = leaderElection.getLastActiveTime().getTime();
            long lastActiveTime = leader.get().getLastActiveTime().getTime();
            long timeoutMill=timeoutSec*1000;
            if (currentTime > lastActiveTime + timeoutMill) {
                PropertyUtils.copyNotNullProperty(leaderElection, leader.get());
                electionRepository.save(leader.get());
                log.info("{} i am leader now", new Date());
            }
        }
    }

    /**
     * 获取当前leader
     *
     * @return 当前leader信息
     */
    @PostMapping("getLeader")
    public LeaderElection getLeader() {
        Optional<LeaderElection> leader = electionRepository.findById(serviceName);
        if (!leader.isPresent()) {
            log.info("leader不存在");
            return null;
        } else {
            return leader.get();
        }
    }

    /**
     * 自己是否是leader
     *
     * @return 是否为leader
     */
    @PostMapping("leaderIsMe")
    public boolean leaderIsMe() {
        int count = electionRepository.countByServiceIdAndLeaderId(serviceName, getLeaderId());
        return count > 0;
    }

    /**
     * 指定某个节点为leader
     *
     * @param leaderElection 节点信息
     */
    @PostMapping("assignLeader")
    public void assignLeader(LeaderElection leaderElection) {
        leaderElection.setServiceId(serviceName);
        leaderElection.setLastActiveTime(new Date());
        electionRepository.save(leaderElection);
    }

    /**
     * 强制开启重新选举
     */
    @PostMapping("forceGlobalElectionAgain")
    public void forceGlobalElectionAgain() {
        electionRepository.deleteById(serviceName);
    }
}
