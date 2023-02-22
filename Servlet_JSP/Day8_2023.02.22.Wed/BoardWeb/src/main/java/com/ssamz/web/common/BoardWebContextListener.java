package com.ssamz.web.common;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BoardWebContextListener implements ServletContextListener {

	public BoardWebContextListener() {
		System.out.println("===> BoardWebContextListener 생성");
	}
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("---> ServletContxt 생성");
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("---> ServletContxt 삭제");
	}
}
