package com.ssamz.web.common;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class BoardWebSessionListener implements HttpSessionAttributeListener {

	public BoardWebSessionListener() {
		System.out.println("BoardHebSessionListener 생성");
	}

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		System.out.println("---> 세션에 정보를 등록함");
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		System.out.println("---> 세션에 등록된 정보를 덮어씀");
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		System.out.println("---> 세션에 등록된 정보가 삭제됨");
	}
}
