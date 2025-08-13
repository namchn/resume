package com.nc.resume._project.resume.configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
public class MessageConfig {

	/*
	@Bean
	@Primary
	@Profile("dev")
	public MessageSource messageSourceDev() throws IOException {
	    return createMessageSource(3); // 3초
	}

	@Bean
	@Primary
	@Profile("prod")
	public MessageSource messageSourceProd() throws IOException {
	    return createMessageSource(3600); // 1시간
	}
	
    //@Bean
    public MessageSource createMessageSource(int cacheSeconds) throws IOException {
        //ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(scanMessageBaseNames());
        //messageSource.setBasenames("classpath:my/custom/path1", "classpath:my/custom/path2");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(cacheSeconds);// 캐시 유지 시간 (초 단위) → 3초로 설정하면 사실상 실시간 반영
        return messageSource;
    }
    */
    
    
    /*
    @Bean
    public MessageSource messageSource(int cacheSeconds) throws IOException, URISyntaxException {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(cacheSeconds); // 10초마다 변경 감지 (개발 환경에서만 짧게, 운영에서는 크게)

        // 메시지 파일 경로 수집
        List<String> basenames = scanMessageFiles("messages");
        messageSource.setBasenames(basenames.toArray(new String[0]));

        return messageSource;
    }
    */

    private String[] scanMessageBaseNames() throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // messages 하위 모든 .properties 파일 검색
        Resource[] resources = resolver.getResources("classpath*:messages/**/*.properties");

        Set<String> baseNames = new HashSet<>();

        for (Resource resource : resources) {
            String uri = resource.getURI().toString();

            // classpath 경로 시작 이후만 추출
            int startIdx = uri.indexOf("messages/");
            if (startIdx == -1) continue;

            String relativePath = uri.substring(startIdx); // e.g. messages/errors/error_ko.properties

            // _ko, _en 등 locale, 확장자 제거
            String baseName = relativePath
                    .replaceAll("(_[a-zA-Z]{2}(_[A-Z]{2})?)?\\.properties$", "") // locale 제거
                    .replace('/', '.'); // 폴더 → 점으로 변환 (ResourceBundle 규칙)

            // ReloadableResourceBundleMessageSource는 "classpath:" 접두어 필요
            baseNames.add("classpath:" + baseName);
        }

        return baseNames.toArray(new String[0]);
    }
    
    
    
    /**
     * src/main/resources/messages 하위의 모든 .properties 파일 경로를 스캔
     * @param baseFolder 리소스 기준 폴더명
     */
    private List<String> scanMessageFiles(String baseFolder) throws IOException, URISyntaxException {
        List<String> basenames = new ArrayList<>();

        // 리소스 경로 찾기
        Path basePath = Paths.get(getClass().getClassLoader().getResource(baseFolder).toURI());

        // 모든 하위 파일 검색
        try (var stream = Files.walk(basePath)) {
            basenames = stream
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".properties"))
                    .map(basePath::relativize)
                    .map(Path::toString)
                    .map(p -> p.replace("\\", "/")) // 윈도우 경로 대응
                    .map(p -> "classpath:" + baseFolder + "/" + p.replace(".properties", ""))
                    .distinct()
                    .collect(Collectors.toList());
        }

        return basenames;
    }
}
