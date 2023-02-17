package com.ssamz.biz.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ssamz.biz.common.JDBCUtil;

public class SelectUserTestDAO {

	public static void main(String[] args) {

		// 1. UserDAO 객체를 생성한다.
		UserDAO dao = new UserDAO();

		// 2. 목록을 조회한다.
		dao.getUserList();
	}
}
