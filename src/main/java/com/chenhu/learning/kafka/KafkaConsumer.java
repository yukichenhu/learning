package com.chenhu.learning.kafka;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

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

    /**
     * 抓取消息但是不消费
     */
    public void fetch(){
        System.setProperty("java.security.auth.login.config", "kafka-conf/kafka-jaas.conf");
        System.setProperty("java.security.krb5.conf", "kafka-conf/krb5.conf");

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.5:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "fetch");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1024);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put(SaslConfigs.SASL_MECHANISM, "GSSAPI");
        Consumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        TopicPartition partition=new TopicPartition("test-topic",0);
        consumer.assign(Collections.singletonList(partition));
        long maxOffset=consumer.endOffsets(Collections.singletonList(partition)).get(partition);
        consumer.seek(new TopicPartition("test-topic", 0),maxOffset-3);
        ConsumerRecords<String,String> records=consumer.poll(Duration.ofSeconds(1));
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(record.topic());
            System.out.println(record.offset());
            System.out.println(record.value());
            System.out.println(record.partition());
            System.out.println("----------------");
        }
    }
}

