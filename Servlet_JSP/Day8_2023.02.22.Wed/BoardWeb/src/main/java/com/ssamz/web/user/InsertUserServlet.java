package com.ssamz.web.user;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssamz.biz.user.UserDAO;
import com.ssamz.biz.user.UserVO;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

//@WebServlet(urlPatterns = "/insertUser.do",
//	initParams = @WebInitParam(name = "boardEncoding", value = "UTF-8"))

@WebServlet(urlPatterns = "/insertUser.do")

public class InsertUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String encoding;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 1. 사용자 입력 정보 추출
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String role = request.getParameter("role");
		String selfInfo = request.getParameter("selfInfo");
		String[] languages = request.getParameterValues("languages");
		String age = request.getParameter("age");

		System.out.println("아이디 : " + id);
		System.out.println("비밀번호 : " + password);
		System.out.println("이름 : " + name);
		System.out.println("권한 : " + role);
		System.out.println("자기 소개 : " + selfInfo);
		for (String language : languages) {
			System.out.println(language + ", ");
		}
		System.out.println("");
		System.out.println("나이 : " + age);

		// 2. DB 연동 처리
		UserVO vo = new UserVO();
		vo.setId(id);
		vo.setPassword(password);
		vo.setName(name);
		vo.setRole(role);

		UserDAO dao = new UserDAO();
		dao.insertUserVO(vo);

		// 3. 화면 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("/login.html");
		dispatcher.forward(request, response);
	}
}
