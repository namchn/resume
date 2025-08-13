package com.nc.resume._project.resume.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
//@Setter
@Component
//@ConfigurationProperties(prefix = "llm")
public class LlmProperties {
	
	private final String url;//final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final String aikey;//final String API_KEY = "Bearer sk-..."; 
    private final String chatlogpath;//  D:\\chatgpt
    private final String model;//  gpt-3.5-turbo
    private final String temperature;//  값 범위는 보통 0 ~ 2 사이 (0.7), FAQ처럼 정형화된 답변이 필요할 땐 temperature를 낮게 설정

    public LlmProperties(
            @Value("${llm.aikey.url}") String url,
            @Value("${llm.aikey}") String aikey,
            @Value("${chatlog.file.path}") String chatlogpath,
            @Value("${llm.request.model}") String model,
            @Value("${llm.request.temperature}") String temperature) {
        this.url = url;
        this.aikey = aikey;
        this.chatlogpath = chatlogpath;
        this.model = model;
        this.temperature = temperature;
    }
    
}
