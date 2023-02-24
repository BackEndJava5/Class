package com.ssamz.web.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 사용자 요청 path를 추출한다.
		String uri = request.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));
		
		// 2. 추출된 path 정보에 따라 요청을 분기 처리한다.
		if(path.equals("/login.do")) {
			System.out.println("로그인 처리");			
		} else if(path.equals("/insertUser.do")) {
			System.out.println("회원가입 처리");			
		} else if(path.equals("/logout.do")) {
			System.out.println("로그아웃 처리");			
		} else if(path.equals("/insertBoard.do")) {
			System.out.println("글 등록 처리");			
		} else if(path.equals("/updateBoard.do")) {
			System.out.println("글 수정 처리");			
		} else if(path.equals("/deleteBoard.do")) {
			System.out.println("글 삭제 처리");			
		} else if(path.equals("/getBoard.do")) {
			System.out.println("글 상세 조회 처리");			
		} else if(path.equals("/getBoardList.do")) {
			System.out.println("글 목록 검색 처리");			
		} 
	}
}
