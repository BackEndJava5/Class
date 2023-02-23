package hello;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hello.do")
public class HelloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HelloServlet() {

	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1. 사용자 입력 정보 추출
		String id = request.getParameter("id");

		// 2. 응답 화면 구성
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();

		out.println("<html>");

		out.println("<head>");
		out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		out.println("<title>Hello Servlet</title>");
		out.println("</head>");

		out.println("<body>");
		out.println("<center>");
		out.println("<h1>" + id + "님 환영합니다.</h1>");
		//out.println("<h1><font color=\"red\">" + id + "</font>님 환영합니다.</h1>"); // 역슬래시와 큰따옴표 사용
		//out.println("<h1><font color='red'>" + id + "</font>님 환영합니다.</h1>"); // 작은 따옴표 사용
		out.println("</center>");
		out.println("</body>");

		out.println("</html>");
	}
}
