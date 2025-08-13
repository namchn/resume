package com.nc.resume._project.resume.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.nc.resume._project.resume.dto.ChatLogResponse;
import com.nc.resume._project.resume.dto.MessageBodyResponse;
import com.nc.resume._project.resume.dto.ResumeRequest;
import com.nc.resume._project.resume.util.InputSanitizer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class LlmService  {
	
	final private LlmCallCounter llmCallCounter;

	final private InputSanitizer inputSanitizer;
	
	final private AiClient openAiClient;
	
	final private ChatLogWriterService chatLogWriterService;
	final private DataLoggingService dataLoggingService;
	
	
	
	/**
     * ChatGPT API 호출 (비동기)
     */
	@Async("openaiExecutor") // 동시 요청 수 제한 (스레드 풀로 조절)
	public CompletableFuture<String> getChatCompletion(String userMessage) {
    	
		// 비용때문에 임시로 10000번 넘게 호출하면 중지함.-> 재기동 필요
		if(llmCallCounter.getCount() >10000) {
			return CompletableFuture.completedFuture("최대응답 호출수를 초과했습니다 관리자에게 문의해주세요.");
		}
		
		// POST 요청 보내기
		String content = openAiClient.call(userMessage);
		
		log.info("ChatGPT 응답: {}", content);
        llmCallCounter.increment();
        log.info("ChatGPT 응답수: {}", llmCallCounter.getCount());
        
        return CompletableFuture.completedFuture(content);
    }
	
	
	@Async("openaiExecutor") // 동시 요청 수 제한 (스레드 풀로 조절)
	public CompletableFuture<String> getChatCompletionWebClient(String userMessage) {

	    if (llmCallCounter.getCount() > 10000) {
	        return CompletableFuture.completedFuture("최대응답 호출수를 초과했습니다 관리자에게 문의해주세요.");
	    }

	    return openAiClient.callAsync(userMessage) // Mono<String> 반환
	            .doOnNext(content -> {
	                log.info("ChatGPT 응답: {}", content);
	                llmCallCounter.increment();
	                log.info("ChatGPT 응답수: {}", llmCallCounter.getCount());
	            })
	            .toFuture(); // Mono → CompletableFuture 변환
	}
	
	
	
	/**
     * 두 개의 프롬프트 요청을 병렬 처리하고 결과 반환
     */
	public MessageBodyResponse getPromptResponses(ResumeRequest resumeRequest) throws InterruptedException, ExecutionException {
		
		//보안 검증
		String basicContent = inputSanitizer.sanitize(resumeRequest.toPromptBase());
		// 심층적인 면접질문 
		String questionTemplate = resumeRequest.toPromptQuestion1(basicContent);
		//자기 개발 학습 경로를 제안
		String studyMapTemplate =resumeRequest.toPromptQuestion2(basicContent);
		
		//테스트는?
		questionTemplate ="대한민국의수도는?";
		studyMapTemplate ="미국의수도는?";
		
		//CompletableFuture<String> questionResponse = getChatCompletion(questionTemplate);
		//CompletableFuture<String> studyMapResponse = getChatCompletion(studyMapTemplate);
		CompletableFuture<String> questionResponse = getChatCompletionWebClient(questionTemplate);
		CompletableFuture<String> studyMapResponse = getChatCompletionWebClient(studyMapTemplate);
		
		// 모든 비동기 작업이 완료될 때까지 기다림
	    CompletableFuture.allOf(questionResponse, studyMapResponse).join();
		
	    // 결과 수집
	    String response1;
		String response2;
	    try {
		   response1 = questionResponse.get(10, TimeUnit.SECONDS);
		   response2 = studyMapResponse.get(10, TimeUnit.SECONDS);
		} catch (Exception e) {
	        log.error("ChatGPT 병렬 호출 중 하나 이상 실패", e);
	        response1 = "질문 응답 생성 실패";
	        response2 = "스터디맵 응답 생성 실패";
	    }

	    /*
		Map<String, Object> messageBody = new HashMap<>();
		messageBody.put("basicContent",basicContent);
		messageBody.put("message1",response1);
		messageBody.put("message2",response2);
		*/
		
		MessageBodyResponse messageBodyResponse = new MessageBodyResponse(basicContent,response1,response2);
		
		//응답 로그
		//loggingService.chatLogWrite(basicContent, messageBody);
		chatLogWriterService.chatLogWrite(messageBodyResponse);
	    //디비에 저장
		//loggingService.dbSaveAsync("userId",questionTemplate, studyMapTemplate, response1, response2,"gpt-4","ko");
		dataLoggingService.dbSaveAsync(new ChatLogResponse().toEntity("userId",questionTemplate, studyMapTemplate, response1, response2,"gpt-4","ko"));
		
		//return messageBody;
		return messageBodyResponse;
	}
	
}