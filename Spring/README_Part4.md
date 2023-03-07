# Part4 Rest 방식과 Ajax를 이용하는 댓글 처리
## 16 Rest방식으로 전환
### 16.1 @RestController
#### 16.1.1 예제 프로젝트 준비

- tomcat 서버 > add and remove > ex03 추가
- server.xml 에서 ```<Context docBase="ex03" path="/"```  로 수정
- tomcat 서버 실행하여 웰컴 메시지 확인
 ``` INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.``` 
- http://localhost:8080/sample/getText
```
안녕하세요
```

