# Part3 기본적인 웹 게시물 관리
## 07 스프링 MVC 프로젝트의 기본 
### 7.3.1 pom.xml 의 수정
- JDBCTests 와 DataSourceTests 동작 확인
```
INFO : org.zerock.persistence.JDBCTests - oracle.jdbc.driver.T4CConnection@6e15fe2
```
```
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@1323676377 wrapping net.sf.log4jdbc.sql.jdbcapi.ConnectionSpy@72f8ae0c
INFO : org.zerock.persistence.DataSourceTests - org.apache.ibatis.session.defaults.DefaultSqlSession@7f353d99
INFO : org.zerock.persistence.DataSourceTests - HikariProxyConnection@1179244298 
```
### 7.3.2 테이블 생성과 Dummy(더미) 데이터 생성
```
create sequence seq_board;

create table tbl_board (
    bno number(10, 0),
    title varchar2(200) not null,
    content varchar2(2000) not null,
    writer varchar2(50) not null,
    regdate date default sysdate,
    updatedate date default sysdate
);

alter table tbl_board add constraint pk_board
primary key (bno);

insert into tbl_board (bno, title, content, writer)
values (seq_board.nextval, '테스트 제목', '테스트 내용', 'user00');
```
7.5 Java 설정을 이용하는 경우의 프로젝트 구성(jex02)
- Run on Server 동작 확인

## 08 영속/비즈니스 계층의 CRUD 구현
### 8.1 영속 계층의 구현 준비
```
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Start completed.
INFO : jdbc.sqlonly - select * from tbl_board where bno > 0 

INFO : jdbc.sqltiming - select * from tbl_board where bno > 0 
 {executed in 147 msec}
INFO : jdbc.resultsettable - 
|----|-------|--------|-------|----------------------|----------------------|
|bno |title  |content |writer |regdate               |updatedate            |
|----|-------|--------|-------|----------------------|----------------------|
|1   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:40:19.0 |2023-02-28 17:40:19.0 |
|2   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:12.0 |2023-02-28 17:42:12.0 |
|3   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:14.0 |2023-02-28 17:42:14.0 |
|4   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:15.0 |2023-02-28 17:42:15.0 |
|5   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:42:16.0 |2023-02-28 17:42:16.0 |
|----|-------|--------|-------|----------------------|----------------------|
```
## 8.2 영속 영역의 CRUD 구현

- src\test\resources\log4j.xml 에서 log level 을 info로 변경
```
<logger name="jdbc.audit">
	<level value="info" />
</logger>

<logger name="jdbc.resultset">
	<level value="info" />
</logger>

<logger name="jdbc.connection">
	<level value="info" />
</logger>
```

### 8.2.1 create(insert) 처리
```
INFO : jdbc.audit - 1. Connection.prepareStatement(insert into tbl_board (bno,title,content,writer)
		values (seq_board.nextval, ?, ?, ?)) returned net.sf.log4jdbc.sql.jdbcapi.PreparedStatementSpy@1d901f20
INFO : jdbc.audit - 1. PreparedStatement.setString(1, "새로 작성하는 글") returned 
INFO : jdbc.audit - 1. PreparedStatement.setString(2, "새로 작성하는 내용") returned 
INFO : jdbc.audit - 1. PreparedStatement.setString(3, "newbie") returned 
INFO : jdbc.sqlonly - insert into tbl_board (bno,title,content,writer) values (seq_board.nextval, '새로 작성하는 글', '새로 
작성하는 내용', 'newbie') 

INFO : jdbc.sqltiming - insert into tbl_board (bno,title,content,writer) values (seq_board.nextval, '새로 작성하는 글', '새로 
작성하는 내용', 'newbie') 
```
### 8.2.2 read(select) 처리
```
INFO : jdbc.audit - 1. PreparedStatement.close() returned 
INFO : jdbc.audit - 1. Connection.clearWarnings() returned 
INFO : org.zerock.mapper.BoardMapperTests - BoardVO(bno=null, title=새로 작성하는 글, content=새로 작성하는 내용, writer=newbie, regdate=null, updateDate=null)
```
### 8.2.3 delete 처리
```
INFO : jdbc.audit - 1. Connection.prepareStatement(delete tbl_board where bno = ?) returned net.sf.log4jdbc.sql.jdbcapi.PreparedStatementSpy@1015a4b9
INFO : jdbc.audit - 1. PreparedStatement.setLong(1, 3) returned 
INFO : jdbc.sqlonly - delete tbl_board where bno = 3 

INFO : jdbc.sqltiming - delete tbl_board where bno = 3 
 {executed in 7 msec}
INFO : jdbc.audit - 1. PreparedStatement.execute() returned false
INFO : jdbc.audit - 1. PreparedStatement.getUpdateCount() ret
```
### 8.2.4 update 처리
```
INFO : jdbc.sqlonly - update tbl_board set title= '수정된 제목', content='수정된 내용', writer = 'user00', updateDate = sysdate 
where bno = 5 

INFO : jdbc.connection - 10. Connection opened
INFO : jdbc.audit - 10. Connection.new Connection returned 
INFO : jdbc.audit - 10. Connection.setReadOnly(false) returned 
INFO : jdbc.audit - 10. Connection.setAutoCommit(true) returned 
INFO : jdbc.sqltiming - update tbl_board set title= '수정된 제목', content='수정된 내용', writer = 'user00', updateDate = sysdate 
where bno = 5 
 {executed in 5 msec}
INFO : jdbc.audit - 1. PreparedStatement.execute() returned false
INFO : jdbc.audit - 1. PreparedStatement.getUpdateCount() returned 1
INFO : jdbc.audit - 1. PreparedStatement.close() returned 
```
## 09 비즈니스 계층
### 9.2 비즈니스 계층의 구현과 테스트
```
INFO : org.zerock.service.BoardServiceTests - org.zerock.service.BoardServiceImpl@44032fde
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
```

### 9.2.1 등록 작업의 구현과 테스트
```
INFO : jdbc.sqltiming - insert into tbl_board (bno,title,content, writer) values (66, '새로 작성하는 글', '새로 작성하는 내용', 'newbie') 
 {executed in 15 msec}
INFO : jdbc.audit - 1. PreparedStatement.execute() returned false
INFO : jdbc.audit - 1. PreparedStatement.getUpdateCount() returned 1
INFO : jdbc.audit - 1. PreparedStatement.close() returned 
INFO : jdbc.audit - 1. Connection.clearWarnings() returned 
INFO : org.zerock.service.BoardServiceTests - 생성된 게시물의 번호: 66
```

### 9.2.2 목록(리스트) 작업의 구현과 테스트
```
|----|---------------------|----------------------|-------|----------------------|----------------------|
|bno |title                |content               |writer |regdate               |updatedate            |
|----|---------------------|----------------------|-------|----------------------|----------------------|
|1   |테스트 제목               |테스트 내용                |user00 |2023-02-28 17:40:19.0 |2023-02-28 17:40:19.0 |
|2   |테스트 제목               |테스트 내용                |user00 |2023-02-28 17:42:12.0 |2023-02-28 17:42:12.0 |
|4   |테스트 제목               |테스트 내용                |user00 |2023-02-28 17:42:15.0 |2023-02-28 17:42:15.0 |
|5   |수정된 제목               |수정된 내용                |user00 |2023-02-28 17:42:16.0 |2023-03-03 09:56:05.0 |
|62  |새로 작성하는 글 select key |새로 작성하는 내용 select key |newbie |2023-03-03 09:47:12.0 |2023-03-03 09:47:12.0 |
|63  |새로 작성하는 글            |새로 작성하는 내용            |newbie |2023-03-03 09:47:12.0 |2023-03-03 09:47:12.0 |
|64  |새로 작성하는 글 select key |새로 작성하는 내용 select key |newbie |2023-03-03 09:56:05.0 |2023-03-03 09:56:05.0 |
|65  |새로 작성하는 글            |새로 작성하는 내용            |newbie |2023-03-03 09:56:05.0 |2023-03-03 09:56:05.0 |
|66  |새로 작성하는 글            |새로 작성하는 내용            |newbie |2023-03-03 10:19:46.0 |2023-03-03 10:19:46.0 |
|----|---------------------|----------------------|-------|----------------------|----------------------|
```
### 9.2.3 조회 작업의 구현과 테스트
```
INFO : jdbc.resultsettable - 
|----|-------|--------|-------|----------------------|----------------------|
|bno |title  |content |writer |regdate               |updatedate            |
|----|-------|--------|-------|----------------------|----------------------|
|1   |테스트 제목 |테스트 내용  |user00 |2023-02-28 17:40:19.0 |2023-02-28 17:40:19.0 |
|----|-------|--------|-------|----------------------|----------------------|

INFO : jdbc.resultset - 1. ResultSet.next() returned false
INFO : jdbc.resultset - 1. ResultSet.close() returned void
```
### 9.2.4 삭제/수정 구현과 테스트
```
INFO : jdbc.sqlonly - delete tbl_board where bno = 2 

INFO : jdbc.sqltiming - delete tbl_board where bno = 2 
 {executed in 2 msec}
INFO : org.zerock.service.BoardServiceTests - REMOVE RESULT: true
```
```
INFO : jdbc.sqlonly - update tbl_board set title= '제목 수정합니다.', content='테스트 내용', writer = 'user00', updateDate = 
sysdate where bno = 1 

INFO : jdbc.sqltiming - update tbl_board set title= '제목 수정합니다.', content='테스트 내용', writer = 'user00', updateDate = 
sysdate where bno = 1 
 {executed in 6 msec}
INFO : org.zerock.service.BoardServiceTests - MODIFY RESULT: true
```
## 10 프레젠테이션(웹)계층의 CRUD 구현
### 10.0.1 목록에 대한 처리와 테스트
```
INFO : org.zerock.controller.BoardControllerTests - {list=[BoardVO(bno=1, title=제목 수정합니다., content=테스트 내용, writer=user00, regdate=Tue Feb 28 17:40:19 KST 2023, updateDate=Fri Mar 03 10:39:58 KST 2023), BoardVO(bno=4, title=테스트 제목, content=테스트 내용, writer=user00, regdate=Tue Feb 28 17:42:15 KST 2023, updateDate=Tue Feb 28 17:42:15 KST 2023), BoardVO(bno=5, title=수정된 제목, content=수정된 내용, writer=user00, regdate=Tue Feb 28 17:42:16 KST 2023, updateDate=Fri Mar 03 09:56:05 KST 2023), BoardVO(bno=65, title=새로 작성하는 글, content=새로 작성하는 내용, writer=newbie, regdate=Fri Mar 03 09:56:05 KST 2023, updateDate=Fri Mar 03 09:56:05 KST 2023)]}
INFO : com.zaxxer.hikari.HikariDataSource - HikariPool-1 - Shutdown initiated...
```

### 10.2.2 등록 처리와 테스트
```
INFO : jdbc.resultset - 1. ResultSet.next() returned false
INFO : jdbc.resultset - 1. ResultSet.close() returned void
INFO : jdbc.sqlonly - insert into tbl_board (bno,title,content, writer) values (72, '테스트 새글 제목', '테스트 새글 내용', 'user00') 

INFO : jdbc.sqltiming - insert into tbl_board (bno,title,content, writer) values (72, '테스트 새글 제목', '테스트 새글 내용', 'user00') 
 {executed in 25 msec}
INFO : org.zerock.controller.BoardControllerTests - redirect:/board/list
```
### 10.2.3 조회 처리와 테스트
```
|----|----------|-----------|-------|----------------------|----------------------|
|bno |title     |content    |writer |regdate               |updatedate            |
|----|----------|-----------|-------|----------------------|----------------------|
|2   |새로 작성하는 글 |새로 작성하는 내용 |newbie |2023-03-03 09:56:05.0 |2023-03-03 09:56:05.0 |
|----|----------|-----------|-------|----------------------|----------------------|

INFO : jdbc.resultset - 1. ResultSet.next() returned false
INFO : jdbc.resultset - 1. ResultSet.close() returned void
INFO : org.zerock.controller.BoardControllerTests - {board=BoardVO(bno=2, title=새로 작성하는 글, content=새로 작성하는 내용, writer=newbie, regdate=Fri Mar 03 09:56:05 KST 2023, updateDate=Fri Mar 03 09:56:05 KST 2023), org.springframework.validation.BindingResult.board=org.springframework.validation.BeanPropertyBindingResult: 0 errors}
```
### 10.2.4 수정 처리와 테스트
```
INFO : jdbc.sqlonly - update tbl_board set title= '수정된 테스트 새글 제목', content='수정된 테스트 새글 내용', writer = 'user00', updateDate 
= sysdate where bno = 1 

INFO : jdbc.sqltiming - update tbl_board set title= '수정된 테스트 새글 제목', content='수정된 테스트 새글 내용', writer = 'user00', updateDate 
= sysdate where bno = 1 
```
### 10.2.5 삭제 처리와 테스트
- .param("bno", "5"))
```
INFO : org.zerock.controller.BoardControllerTests - redirect:/board/list
INFO : org.zerock.controller.BoardController - remove...5
INFO : org.zerock.service.BoardServiceImpl - remove....5
INFO : jdbc.sqlonly - delete tbl_board where bno = 5 

INFO : jdbc.sqltiming - delete tbl_board where bno = 5 
```
## 11 화면처리
### 11.1 목록 페이지 작업과 includes

- https://cafe.naver.com/gugucoding
- SB Admin2 - https://cafe.naver.com/gugucoding?iframe_url_utf8=%2FArticleRead.nhn%253Fclubid%3D28363273%2526page%3D1%2526menuid%3D38%2526boardtype%3DL%2526articleid%3D6570%2526referrerAllArticles%3Dfalse

- server.xml 
- Context docBase="ex02" path="/controller" http://localhost:8080/controller/board/list
- Context docBase="ex02" path="/" http://localhost:8080/board/list

```
List Page
```
### 11.1.1 SB Admin2 페이지 적용하기(page 228)
### 11.1.1 SB Admin2 페이지 적용하기(page 230)
### 11.1.2 includes 적용(page 232)
### 11.1.3 jQuery 라이브러리 변경( page 235)
- 크롬 > 도구 더보기 > 개발자도구 > toggle device toolbar > 모바일 사이즈 선택

## 11.2 목록 화면 처리(page 237)
- 모바일 사이즈로 출력되는지 확인

## 11.2 목록 화면 처리(page 238)
- 데이터베이스 전체 목록 출력 확인

## 11.3 등록 입력 페이지와 등록 처리
- http://localhost:8080/board/register
- 크롬 > 도구 더보기 > 개발자도구 > network -> register > Payload 에서 입력한 한글 확인
- BoardController에 전달될 때 한글이 깨진 상태인지 확인
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.BoardController - list
INFO : org.zerock.controller.BoardController - register: BoardVO(bno=null, title=íì¤í¸, content=íì¤í¸, writer=user00, regdate=null, updateDate=null)
```
### 11.3.1 한글 문제와 UTF-8 필터 처리
- 브라우저와 콘솔로그에서 한글이 정상적으로 처리되는지 확인
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.BoardController - list
INFO : org.zerock.controller.BoardController - register: BoardVO(bno=null, title=테스트, content=테스트, writer=user00, regdate=null, updateDate=null)
```
### 11.3.2 재전송(redirect)처리
### 11.3.3 모달(Modal)창 보여주기
- 게시물 작성후 모달창 확인

### 11.3.4 목록에서 버튼으로 이동하기
- Register New Board 버튼을 눌러서 Board Register 페이지로 이동하는지 확인

### 11.4.1 조회 페이지 작성
- 상세페이지와 Modify/List 버튼 확인
http://localhost:8080/board/get?bno=1 


### 11.4.2 목록 페이지와 뒤로 가기 문제

### 11.5 게시물의 수정/삭제 처리
### 11.5.1 수정/삭제 페이지로 이동
http://localhost:8080/board/modify?bno=1
- 상세페이지에서 수정 가능한지 확인

### 11.5.2 게시물 수정/삭제 확인
http://localhost:8080/board/modify?bno=85
- 수정
```
INFO : org.zerock.controller.BoardController - modify:BoardVO(bno=85, title=수정테스트, content=수정테스트
수정테스트, writer=user00, regdate=null, updateDate=Fri Mar 03 00:00:00 KST 2023)
INFO : org.zerock.controller.BoardController - list
```
- 삭제
```
INFO : org.zerock.controller.BoardController - remove...81
INFO : org.zerock.controller.BoardController - list
```

### 11.5.3 조회 페이지에서 <form> 처리
	
# 12 오라클 데이터베이스 페이징 처리
## 12.1 order by의 문제
### 12.1.1 실행계획과 order by

- 100만개 데이터 생성(page 271)
```
BEGIN
FOR i IN 1..1000000 LOOP
insert into tbl_board (bno, title, content, writer)
values (i, '테스트 제목', '테스트 내용', 'user00');
END LOOP;
COMMIT;
END;
```
```
select * from tbl_board order by bno + 1 desc;
-> 50개의 행이 인출됨(1.623초)
```
```
select * from tbl_board order by bno  desc;
-> 50개의 행이 인출됨(0.045초)
```
## 12.2 order by 보다는 인덱스
```
select 
/*+ INDEX_DESC(tbl_board pk_board) */
* 
from tbl_board where bno > 0;
-> 50개의 행이 인출됨(0.004초)
```
### 12.3.1 인덱스와 오라클 힌트(hint)
```
select * from tbl_board order by bno desc;

select /*+ INDEX_DESC(tbl_board pk_board) */* 
from tbl_board where bno > 0;
```
### 12.3.3 FULL 힌트
```
select /*+ FULL(tbl_board) */* from tbl_board order by bno desc;
```

### 12.3.4 INDEX_ASC, INDEX_DESC 힌트
```
select /*+ INDEX_ASC(tbl_board pk_board) */* from tbl_board 
where bno > 0;
```

## 12.4 ROWNUM과 인라인뷰
### 12.4.1 인덱스를 이용한 접근 시 ROWNUM
```
select /*+ INDEX_ASC(tbl_board pk_board) */
rownum rn, bno, title, content
from tbl_board;

select /*+ INDEX_ASC(tbl_board pk_board) */
rownum rn, bno, title, content
from tbl_board 
where bno > 0;
```
### 12.4.2 페이지 번호 1, 2의 데이터 
```
select /*+ INDEX_ASC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
    tbl_board
where rownum <= 10;

select /*+ INDEX_ASC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
    tbl_board
where rownum > 10 and rownum <= 20;

select /*+ INDEX_ASC(tbl_board pk_board) */
rownum rn, bno, title, content
from 
    tbl_board
where rownum <= 20;
```
### 12.4.3 인라인뷰(In-line View) 처리
```
select
bno, title, content
from
    (
        select /*+ INDEX_ASC(tbl_board pk_board) */
        rownum rn, bno, title, content
        from 
            tbl_board
        where rownum <= 20
    )
where rn > 10;
```
# 13 MyBatis와 스프링에서 페이징 처리
## 13.1 MyBatis 처리와 테스트
### 13.1.1 페이징 테스트와 수정
- MyBatis의 #{}를 적용하기 전에 XML 테스트 실행
```
INFO : org.zerock.mapper.BoardMapperTests - BoardVO(bno=88, title=테스트 제목, content=테스트 내용, writer=user00, regdate=Mon Mar 06 18:18:12 KST 2023, updateDate=Mon Mar 06 18:18:12 KST 2023)
INFO : org.zerock.mapper.BoardMapperTests - BoardVO(bno=87, title=테스트 제목, content=테스트 내용, writer=user00, regdate=Mon Mar 06 18:18:12 KST 2023, updateDate=Mon Mar 06 18:18:12 KST 2023)
INFO : org.zerock.mapper.BoardMapperTests - BoardVO(bno=86, title=테스트 제목, content=테스트 내용, writer=user00, regdate=Mon Mar 06 18:18:12 KST 2023, updateDate=Mon Mar 06 18:18:12 KST 2023)
INFO : org.zerock.mapper.BoardMapperTests - BoardVO(bno=85, title=테스트 제목, content=테스트 내용, writer=user00, regdate=Mon Mar 06 18:18:12 KST 2023, updateDate=Mon Mar 06 18:18:12 KST 2023)
```
- page 297
```
INFO : jdbc.sqlonly - select bno, title, content from ( select /*+INDEX_DESC(tbl_board pk_board) */ rownum rn, bno, 
title, content from tbl_board where rownum<= 3 * 10 ) where rn > (3-1) * 10 

INFO : jdbc.sqltiming - select bno, title, content from ( select /*+INDEX_DESC(tbl_board pk_board) */ rownum rn, bno, 
title, content from tbl_board where rownum<= 3 * 10 ) where rn > (3-1) * 10 
 {executed in 174 msec}
INFO : jdbc.resultsettable - 
|----|-------|--------|
|bno |title  |content |
|----|-------|--------|
|86  |테스트 제목 |테스트 내용  |
|85  |테스트 제목 |테스트 내용  |
|84  |테스트 제목 |테스트 내용  |
|83  |테스트 제목 |테스트 내용  |
|82  |테스트 제목 |테스트 내용  |
|81  |테스트 제목 |테스트 내용  |
|80  |테스트 제목 |테스트 내용  |
|79  |테스트 제목 |테스트 내용  |
|78  |테스트 제목 |테스트 내용  |
|77  |테스트 제목 |테스트 내용  |
|----|-------|--------|

```
## 13.2 BoardController와 BoardService 수정
```
INFO : org.zerock.controller.HomeController - Welcome home! The client locale is ko_KR.
INFO : org.zerock.controller.BoardController - list: Criteria(pageNum=1, amount=10, type=null, keyword=null)
```
- http://localhost:8080/board/list?pageNum=1 에서 페이지당 10개씩 출력되는지 확인


# 14 페이징 화면 처리
## 14.1 페이징 처리할 때 필요한 정보들
## 14.2 페이징 처리를 위한 클래스 설계
## 14.3 JSP에서 페이지 번호 출력

- http://localhost:8080/board/list?pageNum=5
- http://localhost:8080/board/list?pageNum=5&amount=20

### 14.3.1 페이지 번호 이벤트 처리
- 페이지 번호를 클릭시 정상적으로 이동하는지 확인

## 14.4 조회 페이지로 이동
- 특정 게시물 제목을 클릭시 pageNum과 amount 파라미터가 추가로 전달되는 지 확인
http://localhost:8080/board/get?pageNum=2&amount=10&bno=87

### 14.4.1 조회 페이지에서 다시 목록 페이지로 이동 - 페이지 번호 유지
- 조회한 상세 페이지에서 'list'버튼을 눌렀을때 해당하는 페이지로 돌아가는지 확인

## 14.5 수정과 삭제 처리
### 14.5.1 수정/삭제 처리 후 이동
- 수정/삭제 후 사용자가 보던 페이지로 이동하는지 확인

### 14.5.2 수정/삭제 페이지에서 목록으로 이동
- 수정/삭제 취소시에 사용자가 보던 페이지로 이동하는지 확인

## 14.6 MyBatis에서 전체 데이터 개수 처리
```
INFO : org.zerock.controller.BoardController - list: Criteria(pageNum=1, amount=10, type=null, keyword=null)
INFO : org.zerock.controller.BoardController - total: 102
```
# 15 검색 처리
## 15.4 화면에서 검색 조건 처리
### 15.4.1 목록 화면에서의 검색 처리(page 341)
- BoardMapperTest.java JUNIT TEST
```
INFO : jdbc.sqltiming - select bno, title, content, writer, regdate, updatedate from ( select /*+INDEX_DESC(tbl_board 
pk_board) */ rownum rn, bno, title, content, writer, regdate, updatedate from tbl_board where 
( title like '%'||'키워드'||'%' OR content like '%'||'키워드'||'%' OR writer like '%'||'키워드'||'%' 
) AND rownum <= 1 * 10 ) where rn > (1 -1) * 10 
 {executed in 2 msec}
INFO : jdbc.resultsettable - 
|----|------|--------|-------|----------------------|----------------------|
|bno |title |content |writer |regdate               |updatedate            |
|----|------|--------|-------|----------------------|----------------------|
|148 |키워드   |키워드     |user00 |2023-03-07 11:10:53.0 |2023-03-07 11:10:53.0 |
|----|------|--------|-------|----------------------|----------------------|
```
- http://localhost:8080/board/list 에서 검색창 추가되었는지, 한글 영문 검색 테스트
- http://localhost:8080/board/list?type=C&keyword=%ED%82%A4%EC%9B%8C%EB%93%9C&pageNum=1&amount=10 keyword(검색어)가 파라미터로 잘 전달되는지 확인

### 검색버튼의 이벤트 처리(page 345)
- 검색조건없이(--선택) 키워드 입력했을 때 : '검색종류를 선택하세요' 팝업 확인
- 검색조건 선택하고 키워드 입력하지 않았을 때 : '키워드를 입력하세요' 팝업 확인
- 검색 결과가 여러페이지인 키워드로 검색했을 때 페이지를 이동해도 검색 조건과 키워드가 유지되는지 확인

### 15.4.2 조회 페이지에서 검색 처리
### 15.4.3 수정/삭제 페이지에서 검색 처리( page 348 )

- 검색어(Test)로 검색한 상태에서 특정 페이지의 게시물을 수정하고 난 이후에도 검색 조건은 유지한 채 목록 페이지로 이동하는 지 확인(파라미터 keyword=Test가 계속 유지되는지 확인)
- http://localhost:8080/board/list?type=T&keyword=Test&pageNum=1&amount=10








