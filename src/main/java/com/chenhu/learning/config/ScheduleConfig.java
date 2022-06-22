package com.chenhu.learning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 陈虎
 * @date 2022-06-16 15:28
 */
@Configuration
@EnableAsync
public class ScheduleConfig {

    @Bean
    public TaskScheduler taskScheduler(){
        ThreadPoolTaskScheduler taskScheduler=new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(4);
        taskScheduler.setThreadNamePrefix("schedule-task-");
        return taskScheduler;
    }

    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor taskExecutor=new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setThreadNamePrefix("my-task-");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
