package com.nc.resume._project.resume.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeResponse<T> {
    
	    private String status;  // 예: "success", "error"
	    private T message; // 실제 응답 데이터 T = Map<String, Object>..
	    private List<String> errors; // 오류 메시지 리스트

}