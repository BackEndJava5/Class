package com.ssamz.biz.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssamz.biz.common.JDBCUtil;

public class UserDAO {
	// JDBC관련변수
	Connection conn = null;
	PreparedStatement stmt = null;
	ResultSet rs = null;

	// USERS 테이블 관련 SQL 명령어
	private String USER_LIST = "select * from users";
	private String USER_INSERT = "insert into users values(?, ?, ?, ?)";
	private String USER_UPDATE = "update users set name = ?, role = ? where id = ?";

	// USERS 테이블 관련 CRUD 메소드

	// 회원 목록 조회(DAO)
	public void getUserList() {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_LIST);
			rs = stmt.executeQuery();
			System.out.println("[ USER 목록 ]");
			while (rs.next()) {
				System.out.print(rs.getString("ID") + " : ");
				System.out.print(rs.getString("PASSWORD") + " : ");
				System.out.print(rs.getString("NAME") + " : ");
				System.out.println(rs.getString("ROLE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}

	// 회원 목록 조회(VO)
	public List<UserVO> getUserListVO() {
		List<UserVO> userList = new ArrayList<UserVO>();

		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_LIST);
			rs = stmt.executeQuery();
			System.out.println("[ USER 목록 ]");
			while (rs.next()) {
				UserVO user = new UserVO();
				user.setId(rs.getString("ID"));
				user.setPassword(rs.getString("PASSWORD"));
				user.setName(rs.getString("NAME"));
				user.setRole(rs.getString("ROLE"));
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
		return userList;
	}

	// 회원 목록 조회(DAO)
	public void getUserList2() {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_LIST);
			rs = stmt.executeQuery();
			System.out.println("[ USER 목록 ]");
			while (rs.next()) {
				System.out.print(rs.getString("NAME") + "의 권한 : " + rs.getString("ROLE"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}

	// 회원 수 조회
	public void getUserCount() {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_LIST);
			rs = stmt.executeQuery();
			int userCount = 0;

			while (rs.next()) {
				userCount++;
			}
			System.out.println("회원 수 : " + userCount);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}

	// 회원 등록(DAO)
	public void insertUser(String id, String password, String name, String role) {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_INSERT);
			stmt.setString(1, id);
			stmt.setString(2, password);
			stmt.setString(3, name);
			stmt.setString(4, role);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}

	// 회원 등록(VO)
	public void insertUserVO(UserVO vo) {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_INSERT);
			stmt.setString(1, vo.getId());
			stmt.setString(2, vo.getPassword());
			stmt.setString(3, vo.getName());
			stmt.setString(4, vo.getRole());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}

	// 회원 수정(DAO)
	public void updateUser(String name, String role, String id) {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_UPDATE);
			stmt.setString(1, name);
			stmt.setString(2, role);
			stmt.setString(3, id);
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}

	// 회원 수정(VO)
	public void updateUserVO(UserVO vo) {
		try {
			conn = JDBCUtil.getConnection();
			stmt = conn.prepareStatement(USER_UPDATE);
			stmt.setString(1, vo.getName());
			stmt.setString(2, vo.getRole());
			stmt.setString(3, vo.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(null, stmt, conn);
		}
	}
}
