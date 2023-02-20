package com.ssamz.web.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssamz.biz.board.BoardDAO;
import com.ssamz.biz.board.BoardVO;

@WebServlet("/deleteBoard.do")
public class DeleteBoardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private String encoding;

	//protected void doGet(HttpServletRequest request, HttpServletResponse response)
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 0. 상태 정보 체크( page 232 )
		Cookie[] cookieList = request.getCookies();
		if (cookieList == null) {
			response.sendRedirect("/login.html");
		} else {
			String userId = null;

			for (Cookie cookie : cookieList) {
				if (cookie.getName().equals("userId")) {
					userId = cookie.getValue();
				}
			}
			if (userId == null) {
				response.sendRedirect("/login.html");
			}
		}
		// 1. 사용자 입력 정보 추출
		String seq = request.getParameter("seq");

		// 2. DB 연동 처리
		BoardVO vo = new BoardVO();
		vo.setSeq(Integer.parseInt(seq));

		BoardDAO dao = new BoardDAO();
		dao.deleteBoard(vo);

		// 3. 화면 이동
		// response.sendRedirect("getBoardList.do");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/getBoardList.do");
		dispatcher.forward(request, response);
	}
}