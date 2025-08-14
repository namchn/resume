package com.nc.resume._project.resume.dto;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ResumeRequest {

    private String test; //
    
    @NotBlank(message = "경력연차를 선택해주세요.")
    private String years;

	//@NotBlank(message = "요약은 비워둘 수 없습니다.")
    //private String summary;
	
    @NotBlank(message = "직무를 선택해주세요.")
    private String role; //
    
    @NotBlank(message = "경험업무는 비워둘 수 없습니다.")
    private String experiences;

    @NotEmpty(message = "최소 한 개 이상의 보유기술을 선택해주세요.")
    private List<String> skills;
    
    public String toPromptBase() {
        return "경력: " + years + "년차/" + System.lineSeparator()
             + "직무: " + role + "/" + System.lineSeparator()
             + "경험업무: " + experiences + "/" + System.lineSeparator()
             + "보유기술리스트: " + String.join(",", skills) + "/" + System.lineSeparator();
    }
    
    /*
	경력 :3년차
	/직무:백엔드개발자
	/경험업무: Spring Boot/MSA/Python 기반 커머스 서비스 개발,AWS EC2 운영 경험 
	/보유기술리스트: Spring Boot,linux,Python ,jsp AWS EC2
	*/
    
    public String toPromptQuestion1(String basicContent) {
    	return basicContent
    			+"위와 같은 사용자 정보를 바탕으로 예상되는 심층적인 면접질문 2가지를 만들어줘,경력 년차에 따른 수준을 고려해서";
    }
    
    public String toPromptQuestion2(String basicContent) {
    	return basicContent
    			+"위와 같은 사용자 정보를 바탕으로 심화된 자기 개발 학습 로드맵을 만들어줘,추천해줄만한 도서나 관련웹사이트가 있다면 추가로 알려주고,경력 년차에 따른 수준을 고려해서";
    }
    
}