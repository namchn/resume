package com.nc.resume._project.resume.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.nc.resume._project.resume.dto.MessageBodyResponse;
import com.nc.resume._project.resume.properties.LlmProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatLogWriterService {

	final private LlmProperties Llmproperties;
	 /**
    * 프롬프트 및 응답을 로그 파일로 저장
    */	
	public void chatLogWrite(String question, Map<String, Object> messageBody ){
		String answer1 = (String) messageBody.get("message1");
		String answer2 = (String) messageBody.get("message2");
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       String timestamp = LocalDateTime.now().format(formatter);

       // 시간 + 내용 조합
       String line1 = "[" + timestamp + "][user-spec] "+ System.lineSeparator() + question;
       String line2 = "[" + timestamp + "][answer] "+ System.lineSeparator()+"첫번째응답:"+ System.lineSeparator()+answer1
       											 + System.lineSeparator()+"두번째응답:"+ System.lineSeparator()+answer2;
		
		//String projectRoot = System.getProperty("user.dir"); // 현재 실행 경로 (프로젝트 루트)
	    //String filePath = projectRoot + File.separator + "chatgpt_result.txt";

		String dirPath = Llmproperties.getChatlogpath();
	    String filePath = dirPath + File.separator +"chatgpt_result.txt";
		
		try {
			Files.createDirectories(Path.of(dirPath));
		    Files.writeString(
		        Path.of(filePath),
		        line1 + System.lineSeparator()+line2 + System.lineSeparator(),
		        StandardOpenOption.CREATE,
		        StandardOpenOption.APPEND
		    );
		} catch (IOException e) {
		    log.error("파일 저장 중 오류 발생", e);
		}
	}
	
	public void chatLogWrite( MessageBodyResponse messageBodyResponse ){
		String basicContent  = messageBodyResponse.getBasicContent();
		String message1 = messageBodyResponse.getMessage1();
		String message2 = messageBodyResponse.getMessage2();
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
       String timestamp = LocalDateTime.now().format(formatter);

       // 시간 + 내용 조합
       String line1 = "[" + timestamp + "][user-spec] "+ System.lineSeparator() + basicContent;
       String line2 = "[" + timestamp + "][answer] "+ System.lineSeparator()+"첫번째응답:"+ System.lineSeparator()+message1
       											 + System.lineSeparator()+"두번째응답:"+ System.lineSeparator()+message2;
		
		//String projectRoot = System.getProperty("user.dir"); // 현재 실행 경로 (프로젝트 루트)
	    //String filePath = projectRoot + File.separator + "chatgpt_result.txt";

		String dirPath = Llmproperties.getChatlogpath();
	    String filePath = dirPath + File.separator +"chatgpt_result.txt";
		
		try {
			Files.createDirectories(Path.of(dirPath));
		    Files.writeString(
		        Path.of(filePath),
		        line1 + System.lineSeparator()+line2 + System.lineSeparator(),
		        StandardOpenOption.CREATE,
		        StandardOpenOption.APPEND
		    );
		} catch (IOException e) {
		    log.error("파일 저장 중 오류 발생", e);
		}
	}
	
}
