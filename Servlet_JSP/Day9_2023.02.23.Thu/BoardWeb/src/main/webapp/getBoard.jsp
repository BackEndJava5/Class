<%@ page import="java.util.List"%>
<%@ page import="com.ssamz.biz.board.BoardDAO"%>
<%@ page import="com.ssamz.biz.board.BoardVO"%>
<%@ page contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>글 상세</title>
</head>

</head>
<body>

	<%@ include file="../layout/header.jsp"%>

	<%
	// 1. 사용자 입력 정보 추출
	String seq = request.getParameter("seq");

	// 2. DB 연동처리
	BoardVO vo = new BoardVO();
	vo.setSeq(Integer.parseInt(seq));

	BoardDAO boardDAO = new BoardDAO();
	BoardVO board = boardDAO.getBoard(vo);

	// 2. 화면 이동
	%>

	<center>

		<form action='#' method='post'>
			<input name='seq' type='hidden' value="<%=board.getSeq()%>" />
			<table border='1' cellpadding='0' cellspacing='0'>

				<tr>
					<td bgcolor='orange' width='70'>제목</td>
					<td align='left'><input name='title' type='text'
						value="<%=board.getTitle()%>" /></td>
				</tr>

				<tr>
					<td bgcolor='orange'>작성자</td>
					<td align='left'><%=board.getWriter()%></td>
				</tr>

				<tr>
					<td bgcolor='orange'>내용</td>
					<td align='left'><textarea name='content' cols='40' rows='10'><%= board.getContent() %></textarea></td>
				</tr>

				<tr>
					<td bgcolor='orange'>등록일</td>
					<td align='left'><%= board.getRegDate() %></td>
				</tr>

				<tr>
					<td bgcolor='orange'>조회수</td>
					<td align='left'><%= board.getCnt() %></td>
				</tr>

				<tr>
					<td colspan='2' align='center'><input type='submit'
						value='글 수정' /></td>
				</tr>

			</table>
		</form>
		<br>
		<a href='#'>글등록</a>&nbsp;&nbsp;&nbsp;
		<a href='#'>글삭제</a>&nbsp;&nbsp;&nbsp;
		<a href='#'>글목록</a>&nbsp;&nbsp;&nbsp;

	</center>

	<%@ include file="../layout/footer.jsp"%>

</body>
</html>