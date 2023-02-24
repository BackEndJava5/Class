package com.ssamz.biz.user;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import lombok.Data;

// VO 클래스
@Data
public class UserVO implements HttpSessionBindingListener {

	// private 멤버 변수 선언
	private String id;
	private String password;
	private String name;
	private String role;

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("---> UserVO 객체가 세션에 등록됨");
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("---> UserVO 객체가 세션에서 제거됨");
	}
}
