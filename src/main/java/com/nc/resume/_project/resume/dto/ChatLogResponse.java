package com.nc.resume._project.resume.dto;

import java.time.LocalDateTime;

import com.nc.resume._project.resume.entity.ChatLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatLogResponse {

	private String userId;

	private String requestMessage1;
	    
	private String requestMessage2;

	private String responseMessage1;
	    
	private String responseMessage2;

	private String model; // ex: GPT-4, Claude 등

	private String language; // ex: ko, en 등
	
	/* 엔티티 변환 */
	public ChatLog toEntity(String userId, String requestMessage1,String requestMessage2, String responseMessage1, String responseMessage2, String model, String lang) {
		
		return ChatLog.builder()
        .userId(userId)
        .requestMessage1(requestMessage1)
        .requestMessage2(requestMessage2)
        .responseMessage1(responseMessage1)
        .responseMessage2(responseMessage2)
        .model(model) // "gpt-4" 
        .language(lang)
        .createdAt(LocalDateTime.now())
        .build();
	}
	
}
