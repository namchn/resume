package com.nc.resume._project.resume.service.main;

import org.springframework.stereotype.Service;

import com.nc.resume._project.resume.service.LlmService;
import com.nc.resume._project.resume.service.ValidService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Getter
public class MainFacadeService {

	//@Autowired
	final private LlmService llmServiceService;
	final private ValidService validService;
	
}
