<%@ page import="com.ssamz.biz.user.UserDAO"%>
<%@ page import="com.ssamz.biz.user.UserVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
// 1. 사용자 입력 정보 추출
String id = request.getParameter("id");
String password = request.getParameter("password");

// 2. DB 연동처리
UserVO vo = new UserVO();
vo.setId(id);
UserDAO dao = new UserDAO();
UserVO user = dao.getUser(vo);

// 2. 화면 이동
if (user != null) {
	if (user.getPassword().equals(password)) {

		// 상태 정보를 세션에 저장한다.
		session.setAttribute("user", user);

		// 글 목록 화면으로 이동한다. 
		response.sendRedirect("/index.jsp");
	} else {
		out.println("비밀번호 오류입니다.<br>");
		out.println("<a href='/login.jsp'>다시 로그인</a>");
	}
} else {
	out.println("아이디 오류입니다.<br>");
	out.println("<a href='/login.jsp'>다시 로그인</a>");
}
%>
