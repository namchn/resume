package com.nc.resume._project.resume.aop;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class ExceptionHandlingAspect {

    //private static final Logger log = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

	/**
     * Service 계층 메서드 실행 시점
     */
    @Pointcut("execution(* com.nc.resume.service..*(..))")
    public void serviceMethods() {}

    /*
    // 예외 발생 시 처리
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "ex")
    public void handleServiceException(Exception ex) {
        log.error("서비스에서 예외 발생: {}", ex.getMessage(), ex);
        // 추가적인 공통 처리 가능: 예외 감싸기, 알림 전송 등
    }
    */
    
    /**
     * Service / Repository / Component 계층의 모든 public 메서드 예외 처리
     */
    @Around("execution(public * com.nc.resume..*(..)) && " +
            "(@within(org.springframework.stereotype.Service) || " +
            "@within(org.springframework.stereotype.Repository) || " +
            "@within(org.springframework.stereotype.Component))")
    public Object handleAllExceptions(ProceedingJoinPoint joinPoint) throws Throwable {

    	// 실행 위치 정보
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String args = getArgumentsAsString(joinPoint.getArgs());
        
        //시간측정
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
    	try {
    		Object result = joinPoint.proceed(); // 실제 메서드 실행
    		
    		//stopWatch.stop();
        	//log.info("실행시간 : {} s 클래스:{}" ,stopWatch.getTotalTimeSeconds(),className);
    		//log.info("실행시간프리티 : {}" ,stopWatch.prettyPrint());
            return result;
        } catch (Exception ex) {
        	stopWatch.stop();
        	
            // 상세 로그 (내부용)
        	log.error("실행시간프리티 : {}" ,stopWatch.prettyPrint());
        	log.error("실행시간 : {} s 클래스:{}" ,stopWatch.getTotalTimeSeconds(),className);
            log.error("[예외 발생] {}.{}(args: {})\n메시지: {}",
            		className, methodName, args, ex.getMessage(), ex);

            // 사용자에게는 내부 정보 노출 안 함
            throw new RuntimeException("시스템 오류가 발생했습니다. 잠시 후 다시 시도해주세요.", ex);
        }finally {
        	stopWatch.stop();
        	if(stopWatch.getTotalTimeSeconds()>0.2){
            	log.info("실행시간 : {} s 클래스:{} 메소드:{}" ,stopWatch.getTotalTimeSeconds(),className,methodName);
        		log.info("실행시간프리티 : {}" ,stopWatch.prettyPrint());
        	}
        }
    }
        
    /**
     * 메서드 인자 배열을 문자열로 변환
     */
    private String getArgumentsAsString(Object[] args) {
        if (args == null || args.length == 0) return "없음";
        return Arrays.stream(args)
                     .map(arg -> arg != null ? arg.toString() : "null")
                     .collect(Collectors.joining(", "));
    }
}