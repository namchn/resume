package com.nc.resume._project.resume.configuration;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Configuration
public class HttpClientConfig {
    
	/*  */
	@Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(50000); // 연결 타임아웃 50초
	    factory.setReadTimeout(100000);   // 읽기 타임아웃 100초
        return new RestTemplate(factory);
    }
	
	
	/*
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
    */
    
    @Bean
    public WebClient webClient() {
        // 커넥션 풀 설정
        ConnectionProvider connectionProvider = ConnectionProvider.builder("custom")
                //.maxConnections(200) // 최대 커넥션 수
                //.pendingAcquireMaxCount(-1) // 무제한 대기
                //.maxIdleTime(Duration.ofSeconds(30)) // 유휴 연결 유지 시간
                //.maxLifeTime(Duration.ofMinutes(5)) // 연결 최대 수명
                .build();

        HttpClient httpClient = HttpClient.create(connectionProvider)
                .responseTimeout(Duration.ofSeconds(10)) // 응답 타임아웃
                .option(io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // 연결 타임아웃
                .compress(true) // gzip 압축
                // 필요 시 프록시 설정
                //.proxy(proxy -> proxy.type(ProxyProvider.Proxy.HTTP).host("proxy.example.com").port(8080))
                ;

        ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);

        return WebClient.builder()
                .clientConnector(connector)
               // .baseUrl("https://api.example.com") // 기본 URL (필요시)
                .defaultHeader("Accept", "application/json")
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}