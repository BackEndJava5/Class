package com.ssamz.biz.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ssamz.biz.common.JDBCUtil;

public class UpdateUserTest {

	public static void main(String[] args) {
		// JDBC관련변수
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			conn = JDBCUtil.getConnection();

			// JDBC 3단계: Statement 생성
			String sql = "update users set name=?, role=? where id=?";

			stmt = conn.prepareStatement(sql);
			if (stmt != null) {
				System.out.println("Statement 객체:" + stmt.toString());
			}

			// JDBC 4단계: SQL 전송
			// ? 값 설정
			stmt.setString(1, "수정");
			stmt.setString(2, "USER");
			stmt.setString(3, "ssamz3");

			// SQL 전송
			int count = stmt.executeUpdate();
			System.out.println(count + "건 데이터 처리 성공 !");

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

			// JDBC 5단계: 연결 해제
			JDBCUtil.close(null, stmt, conn);
		}
	}
}
