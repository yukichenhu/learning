package com.chenhu.learning.controller;

import com.chenhu.learning.kafka.KafkaConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenhu
 * @description
 * @date 2023-07-27 14:14
 */
@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class KafkaController {
    private final KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("/send")
    public void sendMsg(String msg){
        kafkaTemplate.send("test-topic",msg);
    }

    @PostMapping("/fetch")
    public void fetchMsg(){
        new KafkaConsumer().fetch();
    }
}
