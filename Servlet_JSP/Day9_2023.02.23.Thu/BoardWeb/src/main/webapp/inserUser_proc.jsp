<%@ page import="com.ssamz.biz.user.UserDAO"%>
<%@ page import="com.ssamz.biz.user.UserVO"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TITLE</title>
</head>
<body>
	<center>

		<%
		// 1. 사용자 입력 정보 추출
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String role = request.getParameter("role");

		// 1. DB 연동처리
		UserVO vo = new UserVO();
		vo.setId(id);
		vo.setPassword(password);
		vo.setName(name);
		vo.setRole(role);

		UserDAO dao = new UserDAO();
		dao.updateUser(vo);

		// 3. 화면 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
		dispatcher.forward(request, response);
		%>

	</center>
</body>
</html>