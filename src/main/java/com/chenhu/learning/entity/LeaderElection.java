package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author 陈虎
 * @date 2022-06-15 17:35
 */
@Data
@Table(name = "t_leader_election")
@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class LeaderElection {
    @Id
    private String serviceId;
    private String leaderId;
    private String leaderUrl;
    private Date lastActiveTime;
    @Version
    private Integer version;
}
