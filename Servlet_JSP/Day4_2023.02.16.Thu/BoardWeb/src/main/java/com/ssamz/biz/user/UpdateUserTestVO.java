package com.ssamz.biz.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.ssamz.biz.common.JDBCUtil;

public class UpdateUserTestVO {

	public static void main(String[] args) {
		// 1. UserDAO 객체를 생성한다.
		UserDAO dao = new UserDAO();

		// 2. 회원 정보를 수정한다.
		UserVO vo = new UserVO();
		vo.setName("수정");
		vo.setRole("USER");
		vo.setId("ssamz4");
		dao.updateUserVO(vo);

		// 3. 목록을 조회한다.
		dao.getUserList();
	}
}
