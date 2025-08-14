# resume
llm-resume-consulting-project

# Resume 프로젝트 구조

```
resume/
├── aop/
│ └── ExceptionHandlingAspect : 익셉션 처리 관련 AOP
├── configuration/
│ ├── AsyncConfig : 비동기 처리 쓰레드 관련 값 설정
│ ├── HttpClientConfig : 웹 호출 관련 값 설정
│ └── MessageConfig : 메시지 관련 값 설정
├── controller/
│ └── ResumeController : 메인 컨트롤러
├── dto/
│ ├── ChatLogResponse : ChatLog 응답
│ ├── MessageBodyResponse : ResumeResponse 의 message에 담을 응답
│ ├── OpenAiResponse : API 응답
│ ├── ResumeRequest : API 요청
│ └── ResumeResponse : ResponseEntity에 반환 응답
├── entity/
│ └── ChatLog : 응답결과 엔티티
├── JpaRepository/
│ └── ChatLogRepository : 응답결과 레포지토리
├── properties/
│ ├── LlmProperties : 설정값 모음
│ └── OptionProperties : 화면용 옵션 관련 설정값 모음
├── service/
│ ├── impl/
│ │ └── OpenAiClient : 웹 호출 서비스 구현
│ └── Main/
│ └── MainFacadeService : 주요 서비스 모음
│ ├── ChatLogWriterService : 로그 기록 파일 저장
│ ├── DataLoggingService : 로그 기록 DB 저장
│ ├── LlmCallCounter : 호출수 카운팅
│ ├── AiClient : 웹 호출 서비스 인터페이스
│ ├── LlmService : LLM 호출 전담 (OpenAiClient 감싸기)
│ └── ValidService : 유효성 검증
├── util/
│ ├── InputSanitizer : 텍스트 보안 변환
│ └── MessageSourceUtil : MessageSource 구현 서비스
└── ResumeApplication : 스프링부트 메인
```





# 추가 개발구현 목표

```
1. 개인별 로그인, 개인별 응답 저장 및 불러오기.
2. llm 요청 질문의 구조 세분화 및 디테일 향상  
```
