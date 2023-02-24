package com.ssamz.biz.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class JDBCUtil {
	
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {			
			// JDBC 1단계: 드라이버 객체 로딩
			DriverManager.registerDriver(new org.h2.Driver());
			
			// JDBC 2단계: 커넥션 연결
			String jdbcUrl  = "jdbc:h2:tcp://localhost/~/test";
			conn = DriverManager.getConnection(jdbcUrl, "sa","");
			
			if(conn!=null) {
				System.out.println("H2 연결 성공 : " + conn.toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
		
	public static void close(ResultSet rs, PreparedStatement stmt, Connection conn) {
		// JDBC 5단계 : 연결 해제			
		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}		
}
