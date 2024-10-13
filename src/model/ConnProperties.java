package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


//추상클래스 만듬 : new 연산자를 사용하지 못함 
public class ConnProperties {

	// 상수 
	// 데이터베이스 연결 정보 : DataSource : 앞으로는 데이터베이스 연결정보를 데이터소스라고 부른다. 
	// 데이터베이스 연결 정보에는 무엇이 있어야 하는가 
	// 1. jdbc 드라이버 시작점 네임스페이스 
	// 2. 데이터베이스 연결 url 
	// 3. 계정명
	// 4. 계정명의 패스워드 
	private static final String ORCL_JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String ORCL_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	private static final String ORCL_USER = "jinuk";
	private static final String ORCL_PASS = "park";
	
	
	// 데이터베이스 드라이버를 찾고, 연결 까지 하는 함수 
	// 추상함수가 아닌 static 일반함수
	// ConnProperty.getConnection() 이케 사용하면 된다. 
	public static Connection getConnection() {
		
		Connection conn = null;
		
		try {
			Class.forName(ConnProperties.ORCL_JDBC_DRIVER);	
			conn = DriverManager.getConnection( ConnProperties.ORCL_URL
					                           ,ConnProperties.ORCL_USER
					                           ,ConnProperties.ORCL_PASS);
			if(conn == null) {
				throw new SQLException("failed to create a connection.");
			}
			
		}catch (Exception e) {
			System.out.println(   "ConnProperty :: 데이터베이스를 연결하는데 \n"
					            + "드라이버 또는 연결 과정에서 문제가 생김 >>> : \n" 
								+ e.getMessage() + "\n");
			e.printStackTrace();
		}
		
		return conn;
	}
	
	// 연결 해제하는 함수 
	public static void conClose(Connection conn, PreparedStatement pstmt, ResultSet rsRs) {
		try{
			if (rsRs != null) try { rsRs.close(); rsRs = null; } catch(Exception ex){}
			if (pstmt != null) try { pstmt.close(); pstmt = null; } catch(Exception ex){}
			if (conn != null) try { conn.close(); conn = null; } catch(Exception ex){}
		}catch (Exception e2){}			
	}
	
	public static void conClose(Connection conn, PreparedStatement pstmt) {
		try{
			if (pstmt != null) try { pstmt.close(); pstmt = null; } catch(Exception ex){}
			if (conn != null) try { conn.close(); conn = null; } catch(Exception ex){}
		}catch (Exception e2){}			
	}	
}
