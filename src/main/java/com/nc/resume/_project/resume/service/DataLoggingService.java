package com.nc.resume._project.resume.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nc.resume._project.resume.entity.ChatLog;
import com.nc.resume._project.resume.jpaRepository.ChatLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RequiredArgsConstructor
@Service
public class DataLoggingService {

	final private  ChatLogRepository chatLogRepository;

	
	/**
     * 응답값에 대한 db 저장
     */
	 @Async
	 @Transactional
	 public CompletableFuture<String> dbSaveAsync(String userId, String requestMessage1,String requestMessage2, String responseMessage1, String responseMessage2, String model, String lang) {
	       
	        chatLogRepository.save(ChatLog.builder()
	                .userId(userId)
	                .requestMessage1(requestMessage1)
	                .requestMessage2(requestMessage2)
	                .responseMessage1(responseMessage1)
	                .responseMessage2(responseMessage2)
	                .model(model) // "gpt-4" 
	                .language(lang)
	                .createdAt(LocalDateTime.now())
	                .build());
	
	        return CompletableFuture.completedFuture("");
	 }
	 
	 /**
     * 응답값에 대한 db 저장
     */
	 @Async
	 @Transactional
	 public CompletableFuture<String> dbSaveAsync(ChatLog chatLog) {
	        chatLogRepository.save(chatLog);
	        return CompletableFuture.completedFuture("");
	 }
	
}
