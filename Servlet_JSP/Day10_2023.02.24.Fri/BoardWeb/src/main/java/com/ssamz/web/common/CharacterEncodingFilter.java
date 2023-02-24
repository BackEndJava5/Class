package com.ssamz.web.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;

@WebFilter(urlPatterns = { "*.do" },

		initParams = @WebInitParam(name = "boardEncoding", value = "UTF-8"))
public class CharacterEncodingFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;
	private String encoding;

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("boardEncoding");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 서블릿이 수행되기 전에 인코딩을 처리한다.
		// ServletContext context = request.getServletContext();
		// String encoding = context.getInitParameter("boardEncoding");
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}
}
