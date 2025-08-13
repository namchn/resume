package com.nc.resume._project.resume.configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "openAiExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);    // 동시에 실행할 스레드 수 제한
        executor.setMaxPoolSize(10);    // 최대 스레드 수
        executor.setQueueCapacity(200); // 대기 큐 크기
        executor.setThreadNamePrefix("OpenAI-");
        
        // 거부된 작업에 대한 핸들러 설정
        //executor.setRejectedExecutionHandler(new CustomRejectedExecutionHandler());

        //아래와 같이 CallerRunsPolicy를 설정하면, 큐가 꽉 차더라도 호출자가 직접 실행하게 되어 버려지지 않음:
        //executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        
        executor.initialize();
        return executor;
    }
}