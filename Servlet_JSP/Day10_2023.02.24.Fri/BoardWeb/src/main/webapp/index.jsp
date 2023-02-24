<%@ page import="java.util.List"%>
<%@ page import="com.ssamz.biz.board.BoardDAO"%>
<%@ page import="com.ssamz.biz.board.BoardVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<%
// 1.  사용자 입력 정보 추출
String searchCondition = request.getParameter("searchCondition");
String searchKeyword = request.getParameter("searchKeyword");

// Null Check
if (searchCondition == null)
	searchCondition = "TITLE";
if (searchKeyword == null)
	searchKeyword = "";

// 세션에 검색 관련 정보를 저장한다.
session.setAttribute("condition", searchCondition);
session.setAttribute("keyword", searchKeyword);

//2. DB 연동처리
BoardVO vo= new BoardVO();
vo.setSearchCondition(searchCondition);
vo.setSearchKeyword(searchKeyword);
		
BoardDAO dao= new BoardDAO();
List<BoardVO> boardList=dao.getBoardList(vo);
//3. 화면이동

%>

<center>
	<!-- 검색 시작 -->
	<form action="/index.jsp" method="post">
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
						
				</select> <% String keyword = (String) session.getAttribute("keyword"); %> 
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
			<td align="left"><a href="getBoard.jsp?seq=<%=board.getSeq()%>"> <%=board.getTitle()%></a></td>
			<td><%=board.getWriter()%></td>
			<td><%=board.getRegDate()%></td>
			<td><%=board.getCnt()%></td>
		</tr>
		<% } %>

	</table>
</center>

<%@ include file="../layout/footer.jsp"%>

