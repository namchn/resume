package com.nc.resume._project.resume.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nc.resume._project.resume.dto.ResumeResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ValidService {

	/**
     * 요청 유효성 검증 실패 응답
     */
	public ResponseEntity<ResumeResponse> validateRequest (BindingResult bindingResult) {
		
			List<String> errors = bindingResult.getFieldErrors().stream()
			.map(err -> err.getDefaultMessage())
			.toList();
			
			log.warn("유효성 검증 실패: {}", errors);
			return ResponseEntity.badRequest()
					.body(new ResumeResponse("error", null, errors));

			/*
			Map<String, Object> errorBody = new HashMap<>();
			errorBody.put("status", "error");
			errorBody.put("messages", errors);
			
			return ResponseEntity
			.status(HttpStatus.BAD_REQUEST)
			.body(errorBody);
			*/
	}

}
