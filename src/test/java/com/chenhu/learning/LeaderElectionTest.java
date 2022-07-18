package com.chenhu.learning;

import com.chenhu.learning.controller.LeaderElectionService;
import com.chenhu.learning.utils.AnnotationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 陈虎
 * @date 2022-06-17 9:15
 */
@SpringBootTest
public class LeaderElectionTest {
    @Autowired
    private LeaderElectionService leaderElectionService;

    @Autowired
    private AnnotationUtil annotationUtil;

    @Test
    public void getLeaderId(){
        System.out.println(leaderElectionService.getLeaderUrl());
    }

    @Test
    public void test(){
        annotationUtil.getAnnotationRequestUrls();
    }
}
