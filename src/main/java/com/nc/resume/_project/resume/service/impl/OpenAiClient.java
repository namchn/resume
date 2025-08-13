package com.nc.resume._project.resume.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.nc.resume._project.resume.dto.OpenAiResponse;
import com.nc.resume._project.resume.properties.LlmProperties;
import com.nc.resume._project.resume.service.AiClient;
import com.nc.resume._project.resume.util.MessageSourceUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Component
@RequiredArgsConstructor
@Slf4j
public class OpenAiClient implements AiClient {
	
	private final LlmProperties llmproperties;
	private final MessageSourceUtil messageSourceUtil;
	private final RestTemplate restTemplate;
    private final WebClient webClient;
	
    //private final RestTemplate restTemplate = new RestTemplate();

    /**
     * RestTemplate 동기 호출 방식
     */
    public String call(String userMessage) {
		
		// 요청 메시지 구성 (ChatGPT 모델에 맞는 구조)
		Map<String, Object> message = Map.of(
	                "role", "user",
	                "content", userMessage
	    );
		Map<String, Object> requestBody = Map.of(
				 "model",llmproperties.getModel() ,  // "gpt-3.5-turbo"
	              "messages", List.of(message),
	              "temperature",	Float.parseFloat(llmproperties.getTemperature()	)	//  0.7
        );
  	    // 자신의 API 키 입력
		String API_KEY = llmproperties.getAikey(); 
		String API_URL = llmproperties.getUrl(); 
		
        // HTTP Header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+API_KEY);

        try {
        	HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<OpenAiResponse> response = restTemplate.exchange(
            		API_URL, HttpMethod.POST, entity, OpenAiResponse.class
            );
            /*
	        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
	        Map<String, Object> firstChoice = choices.get(0);
	        Map<String, String> messageObj = (Map<String, String>) firstChoice.get("message");
	        String content = messageObj.get("content");
	        */
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
        	//"OpenAI 호출 실패"
            log.error(messageSourceUtil.getMessage("error.openai.call_fail"), e); 
            //"일시적으로 서비스를 이용할 수 없습니다.";  
            return messageSourceUtil.getMessage("error.openai.service_unavailable"); 
        }
    }
    
    /**
     * WebClient를 사용한 비동기 호출 방식
     */
    public Mono<String> callAsync(String userMessage) {
        Map<String, Object> message = Map.of("role", "user", "content", userMessage);
        Map<String, Object> requestBody = Map.of(
                "model",llmproperties.getModel() ,  // "gpt-3.5-turbo"
                "messages", List.of(message),
                "temperature",	Float.parseFloat(llmproperties.getTemperature()	)	//  0.7
        );

        String apiKey = llmproperties.getAikey();
        String apiUrl = llmproperties.getUrl();

        return webClient.post()
                .uri(apiUrl)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(OpenAiResponse.class)
                .map(resp -> resp.getChoices().get(0).getMessage().getContent())
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2))) //네트워크 일시 장애 등 가벼운 오류에 대해서는 자동 재시도 적용
                .doOnError(e -> log.error("OpenAI 호출 중 에러 발생", e))
                //.onErrorReturn("일시적으로 서비스를 이용할 수 없습니다.")
                .onErrorResume(e -> { //Fallback 메서드 분리
                    // 필요 시 통계나 알림 등도 가능
                	String errorMessage = messageSourceUtil.getMessage("error.openai.service_unavailable", new Object[]{1});
                	//"일시적으로 서비스를 이용할 수 없습니다."
                    return Mono.just(errorMessage);
                });
    }
}