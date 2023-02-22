package com.ssamz.web.common;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

public class TimeCheckFilter extends HttpServlet implements Filter {

	public TimeCheckFilter() {
		System.out.println("===> TimeCheckFilter 생성");
	}

	public void init(FilterConfig fConfig) throws ServletException {
		System.out.println("---> init 호출");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("---> doFilter 호출");

		System.out.println("---[ 사전 처리 ]---");
		chain.doFilter(request, response);
		System.out.println("---[ 사후 처리 ]---");
	}

	public void destroy() {
		System.out.println("---> destroy 호출");
	}
}
