package com.ssamz.web.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssamz.biz.board.BoardDAO;
import com.ssamz.biz.board.BoardVO;

@WebServlet("/updateBoard.do")
public class UpdateBoardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 사용자 입력 정보 추출
		String title = request.getParameter("title");
		String seq = request.getParameter("seq");
		String content = request.getParameter("content");

		// 2. DB 연동 처리
		BoardVO vo = new BoardVO();
		vo.setTitle(title);
		vo.setSeq(Integer.parseInt(seq));
		vo.setContent(content);

		BoardDAO dao = new BoardDAO();
		dao.updateBoard(vo);

		// 3. 화면 이동
		RequestDispatcher dispatcher = request.getRequestDispatcher("/getBoardList.do");
		dispatcher.forward(request, response);
	}
}