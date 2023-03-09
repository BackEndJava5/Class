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
## 17 Ajax 댓글 처리
### 17.1 프로젝트의 구성
- Spring boot에서 log4jdbc.sql.jdbcapi.DriverSpy 드라이버를 이용한 DB 설정시 아래와 같은 에러가 발생함 https://momobob.tistory.com/23
- pom.xml
- ojdbc6.jar 사용자
```
<dependency>
   <groupId>com.oracle.database.jdbc</groupId>
   <artifactId>ojdbc6</artifactId>
   <version>11.2.0.4</version>
</dependency>
- ojdbc9.jar 사용자
- https://mvnrepository.com/artifact/com.oracle.ojdbc/ojdbc8/19.3.0.0
```
        <!-- https://mvnrepository.com/artifact/com.oracle.ojdbc/ojdbc8 -->
        <dependency>
            <groupId>com.oracle.ojdbc</groupId>
            <artifactId>ojdbc8</artifactId>
            <version>19.3.0.0</version>
        </dependency>

- tomcat 서버 실행시 웰컴 메세지로 정상동작 확인
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
```
### 17.2 댓글 처리를 위한 영속 영역
```
create table tbl_reply (
    rno number(10, 0),
    bno number(10, 0) not null,
    reply varchar2(1000) not null,
    replyer varchar2(50) not null,
    replyDate date default sysdate,
    updateDate date default sysdate
);

create sequence seq_reply;

alter table tbl_reply add constraint pk_reply primary key(rno);

alter table tbl_reply add constraint fk_reply_board
foreign key (bno) references tbl_board(bno);
```
#### 17.2.1 ReplyVO 클래스의 추가 ( page 381 )
```
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : org.zerock.mapper.ReplyMapperTests - org.apache.ibatis.binding.MapperProxy@7ff8a9dc
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown completed.
```
#### 17.2.3 CRUD 작업
#### 등록 create
- ReplyMapperTests.java JUNIT test 실행후 10개의 레코드가 생성되는지 확인
```
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : jdbc.sqlonly - insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 11, '댓글 테스트 1', 
'replyer1') 

INFO : jdbc.sqltiming - insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 11, '댓글 테스트 1', 
'replyer1') 

...

INFO : jdbc.sqltiming - insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 14, '댓글 테스트 9', 
'replyer9') 
 {executed in 2 msec}
INFO : jdbc.sqlonly - insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 10, '댓글 테스트 10', 
'replyer10') 

INFO : jdbc.sqltiming - insert into tbl_reply (rno, bno, reply, replyer) values (seq_reply.nextval, 10, '댓글 테스트 10', 
'replyer10') 
 {executed in 8 msec}
INFO : org.zerock.mapper.ReplyMapperTests - org.apache.ibatis.binding.MapperProxy@29ebbdf4
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
```
- sqldeveloper에서 아래의 명령어 실행시 10개의 레코드가 생성되었는지 확인
```
select * from tbl_reply order by rno desc; 
```
#### 조회(read)
- 5번 댓글이 정상적으로 조회되는지 확인
```
INFO : jdbc.sqltiming - select * from tbl_reply where rno = 5 
 {executed in 223 msec}
INFO : jdbc.resultsettable - 
|----|----|---------|---------|----------------------|----------------------|
|rno |bno |reply    |replyer  |replydate             |updatedate            |
|----|----|---------|---------|----------------------|----------------------|
|5   |11  |댓글 테스트 1 |replyer1 |2023-03-07 17:21:15.0 |2023-03-07 17:21:15.0 |
|----|----|---------|---------|----------------------|----------------------|

INFO : org.zerock.mapper.ReplyMapperTests - ReplyVO(rno=5, bno=11, reply=댓글 테스트 1, replyer=replyer1, replyDate=Tue Mar 07 17:21:15 KST 2023, updateDate=Tue Mar 07 17:21:15 KST 2023)
```
#### 삭제(delete)
```
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : jdbc.sqlonly - delete from tbl_reply where rno=5 

INFO : jdbc.sqltiming - delete from tbl_reply where rno=5 
 {executed in 24 msec}
 ```
### 수정(update)
```
INFO : jdbc.sqlonly - select * from tbl_reply where rno = 2 

INFO : jdbc.sqltiming - select * from tbl_reply where rno = 2 
 {executed in 204 msec}
INFO : jdbc.resultsettable - 
|----|----|--------------|---------|----------------------|----------------------|
|rno |bno |reply         |replyer  |replydate             |updatedate            |
|----|----|--------------|---------|----------------------|----------------------|
|2   |12  |Update Reply  |replyer2 |2023-03-07 17:21:15.0 |2023-03-08 10:09:11.0 |
|----|----|--------------|---------|----------------------|----------------------|

INFO : jdbc.sqlonly - update tbl_reply set reply = 'Update Reply ',updatedate = sysdate where rno = 2 

INFO : jdbc.sqltiming - update tbl_reply set reply = 'Update Reply ',updatedate = sysdate where rno = 2 
 {executed in 2 msec}
INFO : org.zerock.mapper.ReplyMapperTests - UPDATE COUNT: 1
```
#### 17.2.4 @Param 어노테이션과 댓글 목록
### 17.3 서비스 영역과 Controller 처리
#### 17.3.1 ReplyController의 설계
#### 17.3.2 등록 작업과 테스트

- 크롬 확장 프로그램으로 REST API 테스트 하기
- 무료 REST(HTTP) API TESTER - Talend API Tester (크롬 확장 프로그램) 설치  https://heeeju4lov.tistory.com/9
- Talend API Tester 실행후 아래와 같이 입력
```
 Method : POST
 URL : http://localhost:8080/replies/new
 Headers : Content-Type  :  application/json
 Body : {"bno":3145745, "reply":"Hello Reply","replyer":"user00"}
 ```
- sqldeveloper에서 확인

#### 17.3.3 특정 게시물의 댓글 목록 확인
```
http://localhost:8080/replies/pages/{bno}/{page}
http://localhost:8080/replies/pages/15/1
```
#### 17.3.4 댓글 삭제/조회
#### 17.3.5 댓글 수정
```
URL :
http://localhost:8080/replies/{rno}
http://localhost:8080/replies/2
Body : {"bno":12, "reply":"댓글을 수정합니다.","replyer":"user00"}
```
```
INFO : org.zerock.controller.ReplyController - rno: 2
INFO : org.zerock.controller.ReplyController - modify: ReplyVO(rno=2, bno=12, reply=댓글을 수정합니다., replyer=user00, replyDate=null, updateDate=null)
```
### 17.4 JavaSCript 준비
#### 17.4.1 JavaScript의 모듈화
```
http://localhost:8080/board/get?bno=12
```
- 개발자 도구에서 reply.js 로딩되고 console 에서 Reply Module........ 출력되는지 확인

#### 모듈 구성하기
```
http://localhost:8080/board/get?pageNum=1&amount=10&type=&keyword=&bno=168
```


