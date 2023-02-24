<%@ page import="com.ssamz.biz.user.UserDAO"%>
<%@ page import="com.ssamz.biz.user.UserVO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<%
// 1. 사용자 입력 정보 추출
String id = request.getParameter("id");
String password = request.getParameter("password");
String name = request.getParameter("name");
String role = request.getParameter("role");

// 2. DB 연동 처리
UserVO vo = new UserVO();
vo.setId(id);
vo.setPassword(password);
vo.setName(name);
vo.setRole(role);

UserDAO dao = new UserDAO();
dao.insertUser(vo);

// 3. 화면 이동
RequestDispatcher dispatcher = request.getRequestDispatcher("/");
dispatcher.forward(request, response);
%>

<%@ include file="../layout/footer.jsp"%>

