package com.localhost.kanbanboard.config;

import com.localhost.kanbanboard.handler.exception.CustomAsyncExceptionHandler;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.Executor;

/**
 * AsyncConfig
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Override
    public Executor getAsyncExecutor() {
        return new ThreadPoolTaskExecutor();
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new CustomAsyncExceptionHandler();
    }
}