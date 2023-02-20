package com.ssamz.web.user;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public class StartTomcat {

	public static void main(String[] args) throws LifecycleException {
		// Ctrl + Shift + O : Organize imports

		// 클래스로 객체를 생성하여 server 인스턴스변수, 참조변수
		Tomcat server = new Tomcat();
		server.start();
		String a = null;

		// 메모리 영역
		// 메소드 영역(데이터 영역) : 상수풀, 메소드, 클래스, static
		// 프로그램 시작과 동시에 저장하고 프로그램이 끝나면 해제

		// 스택 영역 : 지역변수, 참조변수

		// 힙 영역 : 객체

	}

}
