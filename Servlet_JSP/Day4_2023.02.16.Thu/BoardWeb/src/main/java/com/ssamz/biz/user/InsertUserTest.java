package com.ssamz.biz.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUserTest {

	public static void main(String[] args) {
		// JDBC관련변수
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			// JDBC 1단계: 드라이버 객체 로딩
			DriverManager.registerDriver(new org.h2.Driver());

			// JDBC 2단계: 커넥션 연결
			// private접근지정자를 사용하면 클래스안에서만 사용 다른클래스에서 사용시 get , set
			// set => 담는다, 저장한다, 대입 get => 가져온다, 출력한다.
			String jdbcUrl = "jdbc:h2:tcp://localhost/~/test";
			conn = DriverManager.getConnection(jdbcUrl, "sa", "");
			if (conn != null) {
				System.out.println("H2 연결성공 : " + conn.toString());
			}

			// JDBC 3단계: Statement 생성
			String sql = "insert into users values(?, ?, ?, ?)";
			stmt = conn.prepareStatement(sql);
			if (stmt != null) {
				System.out.println("Statement 객체:" + stmt.toString());
			}

			// JDBC 4단계: SQL 전송
			// ? 값 설정
			stmt.setString(1, "ssamz2");
			stmt.setString(2, "ssamz123");
			stmt.setString(3, "쌤즈");
			stmt.setString(4, "AdMIN");

			// SQL 전송
			int count = stmt.executeUpdate();
			System.out.println(count + "건 데이터 처리 성공 !");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// JDBC 5단계: 연결 해제
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
	}// end main
} // class end