package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnProperties;
import model.dao.CategoryDao;
import model.vo.CategoryVO;

public class CategoryDaoImpl implements CategoryDao{
	
	//카테고리 입력(추가)
	@Override
	public Integer insertCategory(CategoryVO category) throws SQLException{
		Integer result = 0;
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();
		
		// 전송객체 선언
		PreparedStatement ps = null;
		
		//Insert SQL문
		String sql = "INSERT INTO category(category_name) "
				+ " VALUES (?) ";
		
		try {
			// 전송객체 생성
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, category.getCategoryName());
			
			// 실행
			result = ps.executeUpdate();
		}
		finally {
			//객체 close
			ConnProperties.conClose(conn, ps);
		}
		
		return result;
	}
	
	//카테고리 삭제
	@Override
	public Integer deleteCategory(String categoryName) throws SQLException {
		Integer result = 0;
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();
		
		// 전송객체 선언
		PreparedStatement ps = null;
		
		//Insert SQL문
		String sql = "DELETE FROM category "
				+ " WHERE category_name = ? ";
		
		try {
			// 전송객체 생성
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, categoryName);
			
			// 실행
			result = ps.executeUpdate();
		}
		finally {
			//객체 close
			ConnProperties.conClose(conn, ps);
		}
		
		return result;
	}

	@Override
	public ArrayList selectAllCategory() throws SQLException{
		ArrayList result = new ArrayList();
		
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();
		
		// 전송객체 선언
		PreparedStatement ps = null;
		
		//Insert SQL문
		String sql = "SELECT category_name name "
				+ " FROM category ";
				
			try {
				// 전송객체 생성
				ps = conn.prepareStatement(sql);
				
				// 실행
				ResultSet rs = ps.executeQuery();
				
				while(rs.next()) {
					ArrayList temp = new ArrayList();
					temp.add(rs.getString("name"));
					result.add(temp);
				}
			}
			finally {
				//객체 close
				ConnProperties.conClose(conn, ps);
			}
				
		return result;
	}

}
