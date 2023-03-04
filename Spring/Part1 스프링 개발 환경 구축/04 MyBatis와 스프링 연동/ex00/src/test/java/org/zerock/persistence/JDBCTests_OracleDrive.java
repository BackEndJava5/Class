package org.zerock.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests_OracleDrive {

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDrive");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void testConnection() {

		String query = "select sysdate from dual";
		String result = null;
		try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "C##book_ex", "oracle");
				PreparedStatement pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {

			log.info(con);
			System.out.println(con);
			
			rs.next();
			result = rs.getString(1); //칼럼 1부터 시작함 = 첫번째 칼럼읽어옴	
			
			log.info(result);
			System.out.println(result);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
