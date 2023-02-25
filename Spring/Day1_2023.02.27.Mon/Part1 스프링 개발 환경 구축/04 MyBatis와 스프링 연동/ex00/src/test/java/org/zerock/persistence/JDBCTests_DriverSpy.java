package org.zerock.persistence;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.Test;

import lombok.extern.log4j.Log4j;

@Log4j
public class JDBCTests_DriverSpy {

	static {
		try {
			Class.forName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testConnection() {

		String query = "select sysdate from dual";
		String result = null;
		
		try (Connection con = DriverManager.getConnection("jdbc:log4jdbc:oracle:thin:@localhost:1521:XE", "C##book_ex", "oracle");
				PreparedStatement pstmt = con.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();) {
			log.warn(con);
			log.info(con);
			System.out.println(con);
			
			rs.next();
			result = rs.getString(1); //칼럼 1부터 시작함 = 첫번째 칼럼읽어옴	
			
			log.info("select sysdate from dual: " + result);
			System.out.println("select sysdate from dual: " + result);

		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

}
