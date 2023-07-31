package com.chenhu.learning.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author chenhu
 * @description
 * @date 2023-07-27 14:21
 */
@Service
public class KafkaConsumer {

    @KafkaListener(topics = "test-topic", groupId = "test")
    public void consume(String message) {
        System.out.println("Received message: " + message);
    }

}

