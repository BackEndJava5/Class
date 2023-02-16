package com.ssamz.biz.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUserTest2 {

	public static void main(String[] args) {
		// 1. UserDAO 객체를 생성한다.
		UserDAO dao = new UserDAO();
		
		// 2. 회원 정보를 등록한다.
		dao.insertUser("ssamz4", "ssamz1234", "쌤즈", "ADMIN");
		
		// 3. 목록을 조회한다.
		dao.getUserList();
	}
}