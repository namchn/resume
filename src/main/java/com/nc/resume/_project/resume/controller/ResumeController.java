package com.nc.resume._project.resume.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nc.resume._project.resume.dto.MessageBodyResponse;
import com.nc.resume._project.resume.dto.ResumeRequest;
import com.nc.resume._project.resume.dto.ResumeResponse;
import com.nc.resume._project.resume.properties.OptionProperties;
import com.nc.resume._project.resume.service.main.MainFacadeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("api/resume")
public class ResumeController  {

	//@Autowired
	
	final private MainFacadeService mainFacadeService;
	final private OptionProperties optionProperties;
	
	@GetMapping("/input")
    public String userDetailPage(Model model) {
        //model.addAttribute("content", "user/detail");
		
		
		List<String> options = List.of("1","2","3","4","5","6","7","8","9","10이상");
        model.addAttribute("yearsOptions", options);
		
		
		/*
		List<String> options = List.of("프론트엔드","백엔드","풀스택","iOS","Android","크로스플랫폼 앱","DevOps","인공지능 · 머신러닝","데이터 엔지니어","게임 클라이언트","게임 서버","VR/AR/MR/XR","소프트웨어 엔지니어","QA 엔지니어","하드웨어 엔지니어");
        model.addAttribute("roleOptions", options);
		
        List<String> checkbox = List.of("C","C++","C#","Java","JavaScript","TypeScript","Python","Ruby","PHP","Kotlin","Go","AngularJS","Electron","Emotion","GraphQL","MobX","NextJS","React-Context","ReactJS","Redux","Recoil","Storybook","Svelte","Tailwind","Unity","VueJS","Vuex","Apache","NGINX","Apollo","ASP.NET","AWS-Kinesis","AWS-SES","Celery","Django","ExpressJS","FastAPI","Flask","GRPC","Hibernate","Koa","Laravel","NestJS","NodeJS","RabbitMQ","Relay","Ruby-on-Rails","Spring","SpringBoot","Swagger","AWS-AuroraDB","AWS-DynamoDB","AWS-MariaDB","CassandraDB","ElasticSearch","MongoDB","MSSQL","MySQL","PostgreSQL","Redis","Airflow","AWS-Athena","Google-BigQuery","Hadoop","Kafka","Keras","Kubeflow","MLflow","Pytorch","Spark","Tableau","Tensorflow","Zeppelin","Flutter","Google-Firebase","Google-Firestore","React-Native","Ansible","AWS-CodePipeline","Azure-DevOps","Bitrise","Circle-CI","Docker","Github","Github-Action","Google-Cloud-Build","Jenkins","Kubernetes","Terraform","Traefik","HTML/CSS","RESTful API","Jira","SCSS","SASS","Android"
        		,"iOS","Firebase","MariaDB","AWS","AWS-RDS","Git","GitLab","AWS-EC2","AWS-S3","AWS-Route53","Notion","Figma","Jandi","NCP","NoSQL","Sentry","RxJS","ES6","Shell Scripting","AWE","AWS-ECS","Fargate","CloudWatch","SQL","SqlAlchemy","AWS-ECR","Jupyter","Tornado","NLP","JSP","AWS-EBS","Docker Compose","AWS-ELB","Styled-Component","New Relic","Azure-Blob Storage","React-Query","Linux","AWS-Lambda","Pandas","NumPy","Scikit-learn","Vert.x","Docker Swarm","Swift","Perl","R","Scala","Rust","Objective-C","BackboneJS","Gatsby","Immer","NuxtJS","OpenGL","ReactiveX","Google-Web-Server","Armeria","AWS-SNS","AWS-SQS","Fastify","Liquibase","MyBatis","Netty","Thrift","Arcus","AWS-DocumentDB","Ceph","CockroachDB","Couchbase","Cubrid","Greenplum","InfluxDB","Memcached","Neo4j","OracleDB","RocksDB","Solr","AWS-Redshift","Clickhouse","Druid","Flink","Fluentd","Google-Data-Studio","Grafana","HBase","Hive","Impala","Kibana","Kuda","Looker","Luigi","Metabase","NIFI","Presto","Prometheus","Ray","Redash","Superset","Zipkin","Alamofire"
        		,"Bazel","Dagger","ExoPlayer","Fastlane","Glide","Lottie","ReactorKit","Realm","Retrofit","RIBs","SnapKit","Unreal","DirectX","PhysX","Bullet","Argo-CD","AWS-CodeBuild","Bitbucket","Capistrano","Central-Dogma","Drone","Gitlab-CI","Go-CD","Gulp","Harbor","Helm","Jaeger","Kube-Bench","OpenEBS","Packer","Rundeck","Saltstack","Spinnaker","Travis-CI","Zabbix","RxSwift","Human Tracking","Pose Estimation","Object Detection","Multi-object Tracking","Gstreamer","NNStreamer","Reactive","3D Rendering","Sagemaker","WebGL","Three.js","Paper.js","Webpack","Babel","Jest","Tibero","Alembic","Structlog","Click","Pytest","Vercel","CloudFront","Vite","AWS-CDK","SAM","Elastic","Ubuntu","Zustand","Radash","Unocss","Ajv","Sketch","Adobe Illustrator","Adobe Photoshop","Xd","Blender","Framer","ProtoPie","Adobe InDesign","Adobe After Effects","Adobe Premiere Pro","Mailchimp","Stibee","Salesforce","HubSpot","Nate","Kakao","Tiktok","Linkedin","Meta","Youtube","Google Ads (SA,DA,App, Video)","Naver (SA,DA,shopping)","Microsoft Word","Microsoft Excel","PowerPoint","Power BI","FineReport","BigQuery","Looker Studio","Appsflyer","Airbridge","Hotjar","GTM","Braze","Amplitude","Branch","GA4","3dsMax","Maya","Final Cut Pro","Dart","GetX","Provide","JQuery","PixiJS","RocketMQ","Stable Diffusion","Midjourney","DALL·E","KingFisher","MapKit","UIView Animation","Confluence","Capture One","Adobe","Lightroom","Rhino","KeyShot","CAD","MVVM","LiveData","coroutines","Webview","Room","ViewModel","ViewBinding","DataStore","Hilt","Retrofit2","Jetpack","Compose","Dio","Socket.IO","Zapier","Mixpanel","Prompt","FLUX","Cisco","Okta","Slack","SSO","ActiveDirectory","ActiveDirectoryCertificateServices","NGFW","SSL","Google-Cloud-Platform","SAP","MCU","FreeRTOS","LVGL","Creatie","Qt creator","Altium","STM32WB","GUI","Axure","Solidity","SpringDataJPA","ABAP","TensorBoard","iBATIS","Nexacro","Jotai","Allo","Loki","Tempo","Vulkan","Matlab","Cinema 4D","Octane Renderer","Redshift Renderer","AWS-CodeArtifact","Webflow","Spline","MSA","ZBrush","Substance Painter","Nuke","Mari","Relate","Mixmax","Altibase","SVN",".Net","QueryDSL","SPSS","D3.js","OpenSearch","Lucene","Classic","ASP","Spine2D","Cocos","Studio","AWS-EKS","AWS-EB","Adobe","Animate","CUDA","AutoCAD","Creo","UG/NX","SolidWorks","FlexSim","ExtendSim","Phase","Maze","PCB-Artwork","JUnit","Stomp.js","Embedded","Linux","Computer","Vision","LLM","Datadog","AWX","Langchain","AWS-bedrock","Prettier","ESLint","TanStackRouter","RadixUI","Postman","Revit","Supabase","OpenStack","Adobe XD"
        		);
        model.addAttribute("checkboxOptions", checkbox);
    	//model.addAttribute("home", "link:");
        */
        
		//프런트에서 구현하는게 나을지도
        List<String> sortedRoles = new ArrayList<>(optionProperties.getRole());
        //Collections.sort(sortedRoles, String.CASE_INSENSITIVE_ORDER);

        List<String> sortedCheckbox = new ArrayList<>(optionProperties.getCheckbox());
        Collections.sort(sortedCheckbox, String.CASE_INSENSITIVE_ORDER);

        model.addAttribute("roleOptions", sortedRoles);
        model.addAttribute("checkboxOptions", sortedCheckbox);
        
        return "resume/inputform";
    }
	@GetMapping("/input2")
    public String userDetailPage2(Model model) {
        //model.addAttribute("content", "user/detail");
		
		List<String> options = List.of("1","2","3","4","5","6","7","8","9","10이상");
        model.addAttribute("yearsOptions", options);
		
		/*
		List<String> options = List.of("프론트엔드","백엔드","풀스택","iOS","Android","크로스플랫폼 앱","DevOps","인공지능 · 머신러닝","데이터 엔지니어","게임 클라이언트","게임 서버","VR/AR/MR/XR","소프트웨어 엔지니어","QA 엔지니어","하드웨어 엔지니어");
        model.addAttribute("roleOptions", options);
		
        List<String> checkbox = List.of("C","C++","C#","Java","JavaScript","TypeScript","Python","Ruby","PHP","Kotlin","Go","AngularJS","Electron","Emotion","GraphQL","MobX","NextJS","React-Context","ReactJS","Redux","Recoil","Storybook","Svelte","Tailwind","Unity","VueJS","Vuex","Apache","NGINX","Apollo","ASP.NET","AWS-Kinesis","AWS-SES","Celery","Django","ExpressJS","FastAPI","Flask","GRPC","Hibernate","Koa","Laravel","NestJS","NodeJS","RabbitMQ","Relay","Ruby-on-Rails","Spring","SpringBoot","Swagger","AWS-AuroraDB","AWS-DynamoDB","AWS-MariaDB","CassandraDB","ElasticSearch","MongoDB","MSSQL","MySQL","PostgreSQL","Redis","Airflow","AWS-Athena","Google-BigQuery","Hadoop","Kafka","Keras","Kubeflow","MLflow","Pytorch","Spark","Tableau","Tensorflow","Zeppelin","Flutter","Google-Firebase","Google-Firestore","React-Native","Ansible","AWS-CodePipeline","Azure-DevOps","Bitrise","Circle-CI","Docker","Github","Github-Action","Google-Cloud-Build","Jenkins","Kubernetes","Terraform","Traefik","HTML/CSS","RESTful API","Jira","SCSS","SASS","Android"
        		,"iOS","Firebase","MariaDB","AWS","AWS-RDS","Git","GitLab","AWS-EC2","AWS-S3","AWS-Route53","Notion","Figma","Jandi","NCP","NoSQL","Sentry","RxJS","ES6","Shell Scripting","AWE","AWS-ECS","Fargate","CloudWatch","SQL","SqlAlchemy","AWS-ECR","Jupyter","Tornado","NLP","JSP","AWS-EBS","Docker Compose","AWS-ELB","Styled-Component","New Relic","Azure-Blob Storage","React-Query","Linux","AWS-Lambda","Pandas","NumPy","Scikit-learn","Vert.x","Docker Swarm","Swift","Perl","R","Scala","Rust","Objective-C","BackboneJS","Gatsby","Immer","NuxtJS","OpenGL","ReactiveX","Google-Web-Server","Armeria","AWS-SNS","AWS-SQS","Fastify","Liquibase","MyBatis","Netty","Thrift","Arcus","AWS-DocumentDB","Ceph","CockroachDB","Couchbase","Cubrid","Greenplum","InfluxDB","Memcached","Neo4j","OracleDB","RocksDB","Solr","AWS-Redshift","Clickhouse","Druid","Flink","Fluentd","Google-Data-Studio","Grafana","HBase","Hive","Impala","Kibana","Kuda","Looker","Luigi","Metabase","NIFI","Presto","Prometheus","Ray","Redash","Superset","Zipkin","Alamofire"
        		,"Bazel","Dagger","ExoPlayer","Fastlane","Glide","Lottie","ReactorKit","Realm","Retrofit","RIBs","SnapKit","Unreal","DirectX","PhysX","Bullet","Argo-CD","AWS-CodeBuild","Bitbucket","Capistrano","Central-Dogma","Drone","Gitlab-CI","Go-CD","Gulp","Harbor","Helm","Jaeger","Kube-Bench","OpenEBS","Packer","Rundeck","Saltstack","Spinnaker","Travis-CI","Zabbix","RxSwift","Human Tracking","Pose Estimation","Object Detection","Multi-object Tracking","Gstreamer","NNStreamer","Reactive","3D Rendering","Sagemaker","WebGL","Three.js","Paper.js","Webpack","Babel","Jest","Tibero","Alembic","Structlog","Click","Pytest","Vercel","CloudFront","Vite","AWS-CDK","SAM","Elastic","Ubuntu","Zustand","Radash","Unocss","Ajv","Sketch","Adobe Illustrator","Adobe Photoshop","Xd","Blender","Framer","ProtoPie","Adobe InDesign","Adobe After Effects","Adobe Premiere Pro","Mailchimp","Stibee","Salesforce","HubSpot","Nate","Kakao","Tiktok","Linkedin","Meta","Youtube","Google Ads (SA,DA,App, Video)","Naver (SA,DA,shopping)","Microsoft Word","Microsoft Excel","PowerPoint","Power BI","FineReport","BigQuery","Looker Studio","Appsflyer","Airbridge","Hotjar","GTM","Braze","Amplitude","Branch","GA4","3dsMax","Maya","Final Cut Pro","Dart","GetX","Provide","JQuery","PixiJS","RocketMQ","Stable Diffusion","Midjourney","DALL·E","KingFisher","MapKit","UIView Animation","Confluence","Capture One","Adobe","Lightroom","Rhino","KeyShot","CAD","MVVM","LiveData","coroutines","Webview","Room","ViewModel","ViewBinding","DataStore","Hilt","Retrofit2","Jetpack","Compose","Dio","Socket.IO","Zapier","Mixpanel","Prompt","FLUX","Cisco","Okta","Slack","SSO","ActiveDirectory","ActiveDirectoryCertificateServices","NGFW","SSL","Google-Cloud-Platform","SAP","MCU","FreeRTOS","LVGL","Creatie","Qt creator","Altium","STM32WB","GUI","Axure","Solidity","SpringDataJPA","ABAP","TensorBoard","iBATIS","Nexacro","Jotai","Allo","Loki","Tempo","Vulkan","Matlab","Cinema 4D","Octane Renderer","Redshift Renderer","AWS-CodeArtifact","Webflow","Spline","MSA","ZBrush","Substance Painter","Nuke","Mari","Relate","Mixmax","Altibase","SVN",".Net","QueryDSL","SPSS","D3.js","OpenSearch","Lucene","Classic","ASP","Spine2D","Cocos","Studio","AWS-EKS","AWS-EB","Adobe","Animate","CUDA","AutoCAD","Creo","UG/NX","SolidWorks","FlexSim","ExtendSim","Phase","Maze","PCB-Artwork","JUnit","Stomp.js","Embedded","Linux","Computer","Vision","LLM","Datadog","AWX","Langchain","AWS-bedrock","Prettier","ESLint","TanStackRouter","RadixUI","Postman","Revit","Supabase","OpenStack","Adobe XD"
        		);
        model.addAttribute("checkboxOptions", checkbox);
    	//model.addAttribute("home", "link:");
        */
        
		//프런트에서 구현하는게 나을지도
        List<String> sortedRoles = new ArrayList<>(optionProperties.getRole());
        //Collections.sort(sortedRoles, String.CASE_INSENSITIVE_ORDER);

        List<String> sortedCheckbox = new ArrayList<>(optionProperties.getCheckbox());
        Collections.sort(sortedCheckbox, String.CASE_INSENSITIVE_ORDER);

        model.addAttribute("roleOptions", sortedRoles);
        model.addAttribute("checkboxOptions", sortedCheckbox);
        
        return "resume/inputform2";
    }
	
	// un
	@PostMapping("/submit")
    public String handleSubmit(
    		@RequestParam("summary") String summary,
    	    @RequestParam("role") String role,
    	    @RequestParam("skills") List<String> skills,
            Model model) {

        // 디버깅 또는 테스트 출력
        System.out.println("Summary: " + summary);
        System.out.println("Role: " + role);
        System.out.println("Skills: " + skills);

        // 이후 AI 분석 처리 로직 연결 가능
        model.addAttribute("summary", summary);
        model.addAttribute("role", role);
        model.addAttribute("skills", skills);
        
        //서비스 로직............ 값 반환

        return "resume/result"; // 결과를 보여줄 템플릿
    }
	
	@PostMapping("/submit2")
	@ResponseBody
	public Map<String, List<String>> handleSubmit(@RequestBody ResumeRequest request) {
	    // 예시 응답 (실제론 OpenAI 응답 붙이면 됨)
	    List<String> questions = List.of(
	        "Spring Boot에서 트랜잭션 관리는 어떻게 하셨나요?",
	        "MSA 구조에서 공통 인증 처리는 어떻게 설계하셨나요?",
	        "Python으로 배치 처리한 경험이 있나요?",
	        "AWS EC2 운영 중 문제가 발생했을 때 어떻게 대응했나요?",
	        "커머스 서비스 트래픽 급증 시 어떻게 대응했는지 설명해주세요."
	    );

	    List<String> learning = List.of(
	        "Redis를 사용한 캐싱 경험 추가",
	        "Kafka를 활용한 비동기 메시징 시스템 학습",
	        "CI/CD 파이프라인 구축 경험 쌓기",
	        "클린 아키텍처 기반 설계 경험 쌓기"
	    );

	    return Map.of(
	        "interviewQuestions", questions,
	        "learningPath", learning
	    );
	}
	
	@PostMapping("/submit3")
	@ResponseBody
	public String handleResumeSubmit(@RequestBody ResumeRequest request) {
	    return String.format("요약: %s\n직무: %s\n기술: %s",
	            request.getExperiences(), request.getRole(), String.join(", ", request.getSkills()));
	}
	
	
	@PostMapping("/request")
	public ResponseEntity<ResumeResponse> adviceRequest(@RequestBody @Valid ResumeRequest resumeRequest,
	            BindingResult bindingResult) throws InterruptedException, ExecutionException {
		
		// 내부에서 오류시 예외 throw 리턴
		if (bindingResult.hasErrors()) {
			//return validService.validateRequest(bindingResult);  
			return mainFacadeService.getValidService().validateRequest(bindingResult);
		}
		
		/*   
		 try {
		        // ✅ 인위적 지연 (예: 5초)
		        Thread.sleep(5000);
		    } catch (InterruptedException e) {
		        Thread.currentThread().interrupt();
		    }
		*/    
		
		//llm 호출
		//Map<String, Object> messageBody = llmServiceService.getPromptResponses(resumeRequest);
		//MessageBodyResponse messageBody = llmServiceService.getPromptResponses(resumeRequest);
		MessageBodyResponse messageBody = mainFacadeService.getLlmServiceService().getPromptResponses(resumeRequest);
		
		//테스트 테스트 
		//messageBody = new MessageBodyResponse("basicContent 테스트","response1 테스트","response2 테스트");
		
		//successBody.put("message", response);
		//successBody.put("message", messageBody);
		//successBody.put("message", questionTemplate);
		//return ResponseEntity.ok(successBody);
		return ResponseEntity.ok(new ResumeResponse("success", messageBody, null));
	}
	
}
