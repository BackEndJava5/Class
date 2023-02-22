package com.ssamz.web.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@WebFilter(urlPatterns = "*.do")
//@WebFilter(urlPatterns = "/getBoardList.do")
public class TimeCheckFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;

	public TimeCheckFilter() {
		System.out.println("===> TimeCheckFilter 생성");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("---> init 호출");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/****** 10.1.2 필터 응용 (page 238) **********/
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		String path = uri.substring(uri.lastIndexOf("/"));

		/****** 10.1.2 필터 응용 (page 282) **********/
		long startTime = System.currentTimeMillis();
		// System.out.println("---> doFilter 호출");
		// System.out.println("---[ 사전 처리 ]---");
		chain.doFilter(request, response);
		// System.out.println("---[ 사후 처리 ]---");
		long endTime = System.currentTimeMillis();
		// System.out.println("서블릿 수행에 소요된 시간 : " + (endTime - startTime) + "(ms)초");
		System.out.println(path + " 요청 처리에 소요된 시간 : " + (endTime - startTime) + "(ms)초");
	}

	public void destroy() {
		System.out.println("---> destroy 호출");
	}
}
