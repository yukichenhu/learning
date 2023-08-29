package com.chenhu.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author chenhu
 * @description
 * @date 2023-08-25 10:08
 */
@Data
@Table(name = "cdp_event_info")
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventInfo {
    @Id
    @GenericGenerator(name = "snowId", strategy = "com.chenhu.learning.config.SnowIdGenerator")
    @GeneratedValue(generator = "snowId")
    private String eventId;
    private String eventName;
    private String activityName;
    private String deviceType;
    private String userId;
    private LocalDateTime eventTime;
    private LocalDateTime createTime;
}
