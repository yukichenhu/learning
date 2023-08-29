package com.chenhu.learning;

import com.chenhu.learning.entity.EventInfo;
import com.chenhu.learning.repository.EventInfoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenhu
 * @description
 * @date 2023-08-25 10:34
 */
@SpringBootTest
public class EventInfoTest {

    @Autowired
    private EventInfoRepository eventInfoRepository;

    @Test
    public void mockEvents(){
        List<String> userIds=eventInfoRepository.randomUserIds();
        String[] deviceTypes = new String[]{"Android", "IOS", "Windows", "MAC"};
        String[] activities = new String[]{"七夕活动", "中秋活动", "国庆活动"};
        List<EventInfo> events=userIds.stream().map(e-> EventInfo.builder()
                .userId(e)
                .eventTime(LocalDateTime.now())
                .createTime(LocalDateTime.now())
                .eventName("活动参与")
                .activityName(activities[new SecureRandom().nextInt(activities.length)])
                .deviceType(deviceTypes[new SecureRandom().nextInt(deviceTypes.length)])
                .build()).collect(Collectors.toList());
        eventInfoRepository.saveAll(events);
    }
}
