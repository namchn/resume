package com.nc.resume._project.resume.util;

import org.springframework.stereotype.Service;

@Service
public class InputSanitizer {

    // 모든 입력 필드에 대해 이 메서드를 호출하세요
    public  String sanitize(String input) {
        if (input == null) return null;

        // 1. XSS 방지 (스크립트 태그 및 이벤트 핸들 제거)
        input = input.replaceAll("(?i)<script.*?>.*?</script>", "");
        input = input.replaceAll("(?i)<.*?on\\w+\\s*=.*?>", "");
        input = input.replaceAll("(?i)javascript:", "");


        // JavaScript 이벤트 제거
        //input = input.replaceAll("(?i)on\\w+\\s*=\\s*['\"].*?['\"]", "");
        
        // 2. HTML 태그 제거 (XSS 또는 UI 파괴 방지)
        input = input.replaceAll("<.*?>", "");

        // 3. SQL 인젝션 위험 키워드 제거
        input = input.replaceAll("(?i)\\b(select|insert|update|delete|drop|truncate|exec|union|--|;)\\b", "");

        // 4. 특수문자 이스케이프 (원하면 주석 처리 가능)
        input = input.replaceAll("&", "&amp;");
        input = input.replaceAll("\"", "&quot;");
        input = input.replaceAll("'", "&#x27;");
        input = input.replaceAll("<", "&lt;");
        input = input.replaceAll(">", "&gt;");

        // 위험한 특수문자 제거 또는 이스케이프
        input = input.replaceAll("[<>\"']", "");
        
        return input.trim();
    }
}