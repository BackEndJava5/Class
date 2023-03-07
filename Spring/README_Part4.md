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
- 개발자 도구 > 네트워크
```
응답헤더
    Connection: keep-alive
    Content-Length: 15
    Content-Type: text/plain;charset=UTF-8
    Date: Tue, 07 Mar 2023 05:35:00 GMT
    Keep-Alive: timeout=20
```
#### 16.2.2 객체의 반환
- http://localhost:8080/sample/getSample
```
This XML file does not appear to have any style information associated with it. The document tree is shown below.
<SampleVO>
<mno>112</mno>
<firstName>스타</firstName>
<lastName>로드</lastName>
</SampleVO>
```
```
응답헤더
    Connection: keep-alive
    Content-Type: application/xml;charset=UTF-8
    Date: Tue, 07 Mar 2023 05:40:58 GMT
    Keep-Alive: timeout=20
    Transfer-Encoding: chunked
```
- http://localhost:8080/sample/getSample.json
```
{"mno":112,"firstName":"스타","lastName":"로드"}
```
- http://localhost:8080/sample/getSample2.json
```
{"mno":113,"firstName":"로켓","lastName":"라쿤"}
```
#### 16.2.3 컬렉션 타입의 객체 반환
- http://localhost:8080/sample/getMap
```
<Map>
<First>
<mno>111</mno>
<firstName>그루트</firstName>
<lastName>주니어</lastName>
</First>
</Map>
```
- http://localhost:8080/sample/getMap.json
```
{"First":{"mno":111,"firstName":"그루트","lastName":"주니어"}}
```
#### 16.2.4 ResponseEntity 타입
- http://localhost:8080/sample/check.json?height=140&weight=60
```
{"mno":0,"firstName":"140.0","lastName":"60.0"}
```
### 16.3 @RestController에서 파라미터
#### 16.3.1 @PathVAriable
- http://localhost:8080/sample/product/bags/1234
```
<Strings>
<item>category: bags</item>
<item>productid: 1234</item>
</Strings>
```
### 16.4 REST 방식의 테스트 
#### 16.4.1 JUnit 기반 테스트
```
INFO : org.springframework.test.web.servlet.TestDispatcherServlet - FrameworkServlet '': initialization completed in 58 ms
INFO : org.zerock.controller.SampleControllerTests - {"tno":123,"owner":"Admin","grade":"AAA"}
INFO : org.zerock.controller.SampleController - convert.......ticketTicket(tno=123, owner=Admin, grade=AAA)
```


