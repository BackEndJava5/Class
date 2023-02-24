<%@ page contentType="text/html; charset=UTF-8"
	errorPage="errors/boardError.jsp" session="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.ssamz.biz.board.BoardDAO"%>
<%@page import="com.ssamz.biz.board.BoardVO"%>
<%@page import="java.util.List"%>
<%@include file="../layout/header.jsp"%>


<%
/* //1. 사용자 입력 정보 추출
String searchCondition=request.getParameter("searchCondition");
String searchKeyword = request.getParameter("searchKeyword");

//Null Check
if(searchCondition == null) searchCondition = "TITLE";
if(searchKeyword == null) searchKeyword= "";


// 세션에 검색 관련 정보를 저장한다.
session.setAttribute("condition", searchCondition);
session.setAttribute("keyword", searchKeyword);

//2. DB 연동처리
BoardVO vo= new BoardVO();
vo.setSearchCondition(searchCondition);
vo.setSearchKeyword(searchKeyword);
		
BoardDAO dao= new BoardDAO();
List<BoardVO> boardList=dao.getBoardList(vo); */

//1. 컨트롤러(Servlet)가 모델(DAO)을 이용하여 request에 등록한 글 목록을 꺼낸다.
List<BoardVO> boardList = (List) request.getAttribute("boardList");
//2. 화면 이동

%>

<center>
	<!-- 검색 시작 -->
	<form action="/getBoardList.do" method="post">
		<table border="1" cellpadding="0" cellspacing="0" width="700">
			<tr>
				<td align="right"><select name="searchCondition">

						<%
						String condition = (String) session.getAttribute("condition");
						%>
						<%
						if (condition.equals("TITLE")) {
						%>
						<option value="TITLE" selected>제목
						<%
						} else {
						%>						
						<option value="TITLE">제목
						<% } %>

						<%
						if (condition.equals("CONTENT")) {
						%>						
						<option value="CONTENT" selected>내용
						<% } else { %>						
						<option value="CONTENT">내용
						<% } %>
						
				</select> 
				<% String keyword = (String) session.getAttribute("keyword"); %> 
				<input name="searchKeyword" type="text" value="<%= keyword %>"/> 
				<input type="submit" value="검색" />
				</td>
			</tr>
		</table>
	</form>
	<!-- 검색 종료 -->

	<table border="1" cellpadding="0" cellspacing="0" width="700">
		<tr>
			<th bgcolor="orange" width="100">번호</th>
			<th bgcolor="orange" width="200">제목</th>
			<th bgcolor="orange" width="150">작성자</th>
			<th bgcolor="orange" width="150">등록일</th>
			<th bgcolor="orange" width="100">조회수</th>
		</tr>

		<% for (BoardVO board : boardList) { %>

		<tr>
			<td><%=board.getSeq()%></td>
<td align="left"><a href="getBoard.jsp?seq=<%=board.getSeq()%>">
<%=board.getTitle()%></a></td>
			<td><%=board.getWriter()%></td>
			<td><%=board.getRegDate()%></td>
			<td><%=board.getCnt()%></td>
		</tr>
		<% } %>

	</table>
</center>

<%@ include file="../layout/footer.jsp"%>

