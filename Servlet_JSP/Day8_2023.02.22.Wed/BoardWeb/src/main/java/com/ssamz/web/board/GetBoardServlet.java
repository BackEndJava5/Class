package com.ssamz.web.board;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssamz.biz.board.BoardDAO;
import com.ssamz.biz.board.BoardVO;

@WebServlet("/getBoard.do")
public class GetBoardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. 사용자 입력 정보 추출
		String seq = request.getParameter("seq");

		// 1. DB 연동 처리
		BoardVO vo = new BoardVO();
		vo.setSeq(Integer.parseInt(seq));

		BoardDAO boardDAO = new BoardDAO();
		BoardVO board = boardDAO.getBoard(vo);

		// 2. 응답 화면 구성
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>글 상세</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>글 상세</h1>");
		out.println("<a href='logout.do'>Log-out</a></h3>");
		out.println("<tr>");

		out.println("<form action='updateBoard.do' method='post'>");
		out.println("<input name='seq' type='hidden' value='" + board.getSeq() + "'/>");
		out.println("<table border='1' cellpadding='0' cellspacing='0'>");

		out.println("<tr>");
		out.println("<td bgcolor='orange' width='70'>제목</td>");
		out.println("<td align='left'><input name='title' type='text' value='" + board.getTitle() + "'/></td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td bgcolor='orange'>작성자</td>");
		out.println("<td align='left'>" + board.getWriter() + "</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td bgcolor='orange'>내용</td>");
		out.println("<td align='left'><textarea name='content' cols='40' rows='10'>" + board.getContent()
				+ "</textarea></td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td bgcolor='orange'>등록일</td>");
		out.println("<td align='left'>" + board.getRegDate() + "</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td bgcolor='orange'>조회수</td>");
		out.println("<td align='left'>" + board.getCnt() + "</td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td colspan='2' align='center'>");
		out.println("<input type='submit' value='글 수정'/>");
		out.println("</td>");
		out.println("</tr>");

		out.println("</table>");
		out.println("</form>");
		out.println("<hr>");
		
		out.println("<a href='insertBoard.html'>글등록</a>&nbsp;&nbsp;&nbsp;");
		
		//out.println("<a href='deleteBoard.do?seq=" + board.getSeq() + "'>글삭제</a>&nbsp;&nbsp;&nbsp;");
		/********** 권한 이용 ( page 244 ) **************/
		HttpSession session = request.getSession();
		String userRole = (String) session.getAttribute("userRole");
		if(userRole.equals("ADMIN")) {
			out.println("<a href='deleteBoard.do?seq=" + board.getSeq() + "'글삭제</a>&nbsp;&nbsp;&nbsp;");
		}		
		
		out.println("<a href='getBoardList.do'>글목록</a>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");

		out.close();
	}
}
