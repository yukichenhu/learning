package com.chenhu.learning.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @author 陈虎
 * @date 2022-06-14 9:23
 */
@Slf4j
@Component
public class SnowFlakeUtil {

    private static long workerId;

    @Value("${worker-id}")
    public void setWorkerId(long workerId) {
        SnowFlakeUtil.workerId = workerId;
    }

    private static final long dataCenterId=1;
    private static Snowflake snowflake = IdUtil.getSnowflake(workerId,dataCenterId);

    @PostConstruct
    public void init(){
        snowflake = IdUtil.getSnowflake(workerId,dataCenterId);
    }
    public static synchronized long snowflakeId() {
        return snowflake.nextId();
    }

    public static synchronized long snowflakeId(long workerId, long dataCenterId) {
        Snowflake snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
        return snowflake.nextId();
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        Stream.iterate(0, x->x+1).
                limit(20).
                forEach(x->{
                    executorService.submit(()->{
                        long id = SnowFlakeUtil.snowflakeId(1,1);
                        System.out.println(id);
                    });
                });
        executorService.shutdown();
    }
}
