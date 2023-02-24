package com.ssamz.web.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
//import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ssamz.biz.user.UserDAO;
import com.ssamz.biz.user.UserVO;

@WebServlet("/login.do")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String encoding;

	public LoginServlet() {
		System.out.println("===> LoginServlet 생성");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		// 인코딩 처리
		super.init(config);
		encoding = config.getInitParameter("boardEncoding");
		System.out.println("---> Encoding: " + encoding);
	}

	public void init() throws ServletException {
		System.out.println("===> init() 호출");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		 * 6.1.1 HttpServletRequest 객체 활용 - page 165
		 */

		System.out.println("------Start Line------------");
		String method = request.getMethod();
		String uri = request.getRequestURI();
		String protocol = request.getProtocol();
		System.out.println(method + " " + uri + " " + protocol);
		System.out.println("-------Message Header---------");
		System.out.println("Host: " + request.getHeader("host"));
		System.out.println("Connection:" + request.getHeader("connection"));
		System.out.println("User-Agent: " + request.getHeader("user-agent"));
		System.out.println("Accept : " + request.getHeader("accept"));
		System.out.println("Accept-Encoding: " + request.getHeader("accept-encoding"));
		System.out.println("Accept-Language : " + request.getHeader("accept-language"));

		/*
		 * 6.1.3 로그인 인증 처리 - page 171
		 */

		// 1. 사용자 입력 정보 추출
		String id = request.getParameter("id");
		String password = request.getParameter("password");

		// 2. DB 연동 처리
		UserVO vo = new UserVO();
		vo.setId(id);

		UserDAO dao = new UserDAO();
		UserVO user = dao.getUser(vo);

		// 3. 응답 화면 구성(콘솔 로그 출력)
		if (user != null) {
			if (user.getPassword().equals(password)) {
				System.out.println(user.getName() + "님 로그인 환영<br>");
				System.out.println("<a href='/getBoardList.do'>글 목록 이동</a>");
			} else {
				System.out.println("비밀번호 오류입니다.<br>");
				System.out.println("<a href='/'>다시 로그인</a>");
			}
		} else {
			System.out.println("아이디 오류입니다.<br>");
			System.out.println("<a href='/'>다시 로그인</a>");
		}

		/*
		 * 6.1.3 브라우저에 메시지 출력 - page 174
		 */

		// 응답 메시지에 대한 인코딩 설정
		response.setContentType("text/html; charset=UTF-8");

		// HTTP 응답 프로토콜 message-body와 연결된 출력 스트림 획득
		PrintWriter out = response.getWriter();

		// 메시지 출력(브라우저에 출력)
		if (user != null) {
			if (user.getPassword().equals(password)) {

				/********** 세션 **************/
				// 상태 정보를 세션에 저장하여 전송한다. ( page 239 )
				// 사용자 이름 출력 ( page 243 )
				// 세션 유효 시간 변경 ( page 246 )
				HttpSession session = request.getSession();
				//session.setMaxInactiveInterval(10);
				//session.setAttribute("userId", user.getId());
				//session.setAttribute("userName", user.getName());
				//session.setAttribute("userRole", user.getRole());
				session.setAttribute("user",user);
				
				/*** 10.2.2 리스너 응용 ( page 302 )****/
				RequestDispatcher dispatcher = request.getRequestDispatcher("getBoardList.do");
				dispatcher.forward(request, response);

				/*** 9.1.2 HttpServletRequest와 포워딩 ( page 261 )****/
				// 글 목록 화면으로 포워딩한다.
				// 글 목록 화면에서 사용할 데이터를 HttpSErvletRequest에 등록한다. ( page 261 )
				//request.setAttribute("welcomeMessage", "님 환영합니다.");
				//RequestDispatcher dispatcher = request.getRequestDispatcher("getBoardList.do");
				//dispatcher.forward(request, response);

				/*** 9.1.2 HttpServletRequest와 리디렉트 ( page 262 )****/
				// 글 목록 화면으로 리디렉트한다.
				// 포워드가 아닌 리디렉트된 서블릿에서는 HttpServletRequest로부터 데이터를 추출할 수 없다. ( page 264 ) 
				// response.sendRedirect("getBoardList.do");
				
				/*** 9.1.3 HttpSession과 정보공유(리디렉트) ( page 266 )****/
				// 글 목록 화면으로 리디렉트한다.
				// 리디렉트 관계에 있는 서블릿 사이에서 정보를 공유하려면 HttpSession에 공유 데이터를 저장해야 한다.
				//session.setAttribute("welcomeMessage", "님 환영합니다.");
				//response.sendRedirect("getBoardList.do");
				
				/*** 9.1.4  ServletContext와 정보공유 ( page 268 )****/
				// 글 목록 화면에서 사용할 데이터를 ServletContext에 등록한다.
				//ServletContext context = getServletContext();
				//context.setAttribute("welcomeMessage", "님 환영합니다.");
				//response.sendRedirect("getBoardList.do");
				
			} else {
				out.println("비밀번호 오류입니다.<br>");
				out.println("<a href='/'>다시 로그인</a>");
			}
		} else {
			out.println("아이디 오류입니다.<br>");
			out.println("<a href='/'>다시 로그인</a>");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("===> doGet 호출");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * request.setCharacterEncoding("UTF-8");
		 * response.setContentType("text/html; charset=UTF-8");
		 */
		
		// 인코딩 처리
//		ServletConfig config = getServletConfig();
//		encoding = config.getInitParameter("boardEncoding");
//		System.out.println("---> Encoding: " + encoding);
//		request.setCharacterEncoding(encoding);
		
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
		response.sendRedirect("login.html");
	}

	public void destroy() {
		System.out.println("===> destroy() 호출");
	}
}
