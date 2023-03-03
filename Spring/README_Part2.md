# Part2 스프링 MVC 설정( 완료 )
## 05 스프링 MVC의 기본 구조
- Can not find the tag library descriptor for "http://java.sun.com/jsp/jstl/ core" https://freehoon.tistory.com/78
- pom.xml
```
<dependency>
  <groupId>jstl</groupId>
  <artifactId>jstl</artifactId>
  <version>1.2</version>
  <scope>compile</scope>
</dependency>
<dependency>
  <groupId>taglibs</groupId>
  <artifactId>standard</artifactId>
  <version>1.1.2</version>
  <scope>compile</scope>
</dependency>
```
추가 해주니 문제가 해결됐다. 추가해 줬던 코드를 다시 지워보았다. 더이상 빨간 줄이 생기지 않는다.

- jex01
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
```
Hello world!
The time on the server is 2023? 2? 28? ?? 12? 51? 29? KST.
```
## 06 스프링 MVC의 Controller
### 6.1 @Controller, @RequestMapping(page 128)
```
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/],methods=[GET]}" onto public java.lang.String org.zerock.controller.HomeController.home(java.util.Locale,org.springframework.ui.Model)
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/*]}" onto public void org.zerock.controller.SampleController.basic()
```
### 6.2  @RequestMapping의 변화 ( basic() + basicGet(), basicGet2() ) 
```
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/*]}" onto public void org.zerock.controller.SampleController.basic()
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/basicOnlyGet],methods=[GET]}" onto public void org.zerock.controller.SampleController.basicGet2()
INFO : org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/sample/basic],methods=[GET || POST]}" onto public void org.zerock.controller.SampleController.basicGet()
```
### 6.3 Controller의 파라미터 수집
- http://localhost:8080/sample/ex01?name=AAA&age=10
```
메시지 파일 [/WEB-INF/views/ex01.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - SampleDTO(name=AAA, age=10)
```
### 6.3.1 파라미터의 수집과 변환
- http://localhost:8080/sample/ex02?name=AAA&age=10
```
메시지  파일 [/WEB-INF/views/ex02.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - SampleDTO(name=AAA, age=10)
INFO : org.zerock.controller.SampleController - name: AAA
INFO : org.zerock.controller.SampleController - age: 10
```
### 6.3.2 리스트, 배열 처리
- http://localhost:8080/sample/ex02List?ids=111&ids=222&ids=333
```
메시지 파일 [/WEB-INF/views/ex02List.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - ids: [111, 222, 333]	
```
### 6.3.3 객체 리스트
- Bad request http://localhost:8088/sample/ex02Bean?list[0].name=aaa&list[2].name=bbb
- http://localhost:8080/sample/ex02Bean?list%5B0%5D.name=aaa&list%5B2%5D.name=bbb
```
INFO : org.zerock.controller.SampleController - list dtos: SampleDTOList(list=[SampleDTO(name=aaa, age=0), SampleDTO(name=null, age=0), SampleDTO(name=bbb, age=0)])
```
### 6.3.4 @InitBinder
- http://localhost:8080/sample/ex03?title=test&dueDate=2018-01-01	
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - todo: TodoDTO(title=test, dueDate=Mon Jan 01 00:00:00 KST 2018)
```
### 6.3.5 @DateTimeFormat
- http://localhost:8080/sample/ex03?title=test&dueDate=2018/01/01
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.SampleController - todo: TodoDTO(title=test, dueDate=Mon Jan 01 00:00:00 KST 2018)
```
## 6.4 Model이라는 데이터 전달자
### 6.4.1 @ModelAttribute 어노테이션
- http://localhost:8080/sample/ex04?name=aaa&age=11&page=9
```
SAMPLEDTO SampleDTO(name=aaa, age=11)
PAGE 9
```
```
INFO : org.zerock.controller.SampleController - dto: SampleDTO(name=aaa, age=11)
INFO : org.zerock.controller.SampleController - page: 9
```
6.5.1 void 타입
- http://localhost:8080/sample/ex05
```
메시지 파일 [/WEB-INF/views/sample/ex05.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.SampleController - /ex05..........
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
6.5.3 객체 타입
- http://localhost:8080/sample/ex06
```
{"name":"홍길동","age":10}
```
```
INFO : org.zerock.controller.SampleController - /ex06..........
```
6.5.4 ResponseEntity 타입
- http://localhost:8080/sample/ex07
```
{"name": "홍길동"}
개발자 도구
요청 URL: http://localhost:8088/sample/ex07
요청 메서드: GET
상태 코드: 200 
원격 주소: [::1]:8080
리퍼러 정책: strict-origin-when-cross-origin
```
```
INFO : org.zerock.controller.SampleController - /ex07..........
```
6.5.5 파일 업로드 처리
- http://localhost:8080/sample/exUpload
```
선택된 파일 없음
선택된 파일 없음
선택된 파일 없음
선택된 파일 없음
선택된 파일 없음
```
```
INFO : org.zerock.controller.SampleController - basic...................
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
- 파일 선택 > 제출 후 
- http://localhost:8080/sample/exUploadPost
```
메시지 파일 [/WEB-INF/views/sample/exUploadPost.jsp]을(를) 찾을 수 없습니다.
```
```
INFO : org.zerock.controller.SampleController - /exUpload..........
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
INFO : org.zerock.controller.SampleController - ----------------------------------
INFO : org.zerock.controller.SampleController - name:ex07.PNG
INFO : org.zerock.controller.SampleController - size:19508
````
- page 153 Java 설정을 이용하는 경우(jex01 > Run As > Run on Server 클릭시 자동으로 http://localhost:8080/controller/ load 됨)
```
Hello world!
The time on the server is 2023? 2? 28? ?? 1? 7? 42? KST.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
- C:\upload\tmp 가 생성됨
	
6.6 Controller의 Exception 
- Java 설정을 이용하는 경우(jex01, page 158) 에도 동일 결과임.
- http://localhost:8088/sample/ex04?name=aaa&age=bbb&page=9
```
org.springframework.validation.BeanPropertyBindingResult: 1 errors Field error in object 'sampleDTO' on field 'age': rejected value [bbb]; codes [typeMismatch.sampleDTO.age,typeMismatch.age,typeMismatch.int,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [sampleDTO.age,age]; arguments []; default message [age]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'age'; nested exception is java.lang.NumberFormatException: For input string: "bbb"]
org.springframework.web.method.annotation.ModelAttributeMethodProcessor.resolveArgument(ModelAttributeMethodProcessor.java:157)
org.springframework.web.method.support.HandlerMethodArgumentResolverComposite.resolveArgument(HandlerMethodArgumentResolverComposite.java:124)
org.springframework.web.method.support.InvocableHandlerMethod.getMethodArgumentValues(InvocableHandlerMethod.java:161)
org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:131)
```
```
[org.springframework.context.support.DefaultMessageSourceResolvable: codes [sampleDTO.age,age]; arguments []; default message [age]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'age'; nested exception is java.lang.NumberFormatException: For input string: "bbb"]
ERROR: org.zerock.exception.CommonExceptionAdvice - {exception=org.springframework.validation.BindException: org.springframework.validation.BeanPropertyBindingResult: 1 errors
Field error in object 'sampleDTO' on field 'age': rejected value [bbb]; codes [typeMismatch.sampleDTO.age,typeMismatch.age,typeMismatch.int,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [sampleDTO.age,age]; arguments []; default message [age]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'int' for property 'age'; nested exception is java.lang.NumberFormatException: For input string: "bbb"]}
```
- page 158 Java 설정을 이용하는 경우(jex01)
- @Log4j에 빨간 줄 https://stage-diary.tistory.com/532
```
					<groupId>com.sun.jmx</groupId>
					<artifactId>jmxri</artifactId>
				</exclusion>
			</exclusions>
			<!-- <scope>runtime</scope> --> 이 부분 
		</dependency>
```
6.6.2 404 에러 
- http://localhost:8088/nopage
```
HTTP 상태 404 – 찾을 수 없음
타입 상태 보고

설명 Origin 서버가 대상 리소스를 위한 현재의 representation을 찾지 못했거나, 그것이 존재하는지를 밝히려 하지 않습니다.
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
WARN : org.springframework.web.servlet.PageNotFound - No mapping found for HTTP request with URI [/nopage] in DispatcherServlet with name 'appServlet'
```
- Java 설정을 이용하는 경우(jex01, page 161)
```
No handler found for GET /nopage
org.springframework.web.servlet.DispatcherServlet.noHandlerFound(DispatcherServlet.java:1210)
org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:966)
org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:925)
org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:974)
org.springframework.web.servlet.FrameworkServlet.doGet(FrameworkServlet.java:866)
```
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
WARN : org.springframework.web.servlet.PageNotFound - No mapping found for HTTP request with URI [/nopage] in DispatcherServlet with name 'dispatcher'
ERROR: org.zerock.exception.CommonExceptionAdvice - Exception .......No handler found for GET /nopage
ERROR: org.zerock.exception.CommonExceptionAdvice - {exception=org.springframework.web.servlet.NoHandlerFoundException: No handler found for GET /nopage}
```