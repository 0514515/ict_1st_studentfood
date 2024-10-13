package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.ConnProperties;
import model.FoodDao;
import model.vo.FoodVO;

public class FoodDaoImpl implements FoodDao {

	@Override
	public Integer insertFood(FoodVO food) throws SQLException {
		Integer result = 0;
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();

		// 전송객체 선언
		PreparedStatement ps = null;

		// Insert SQL문
		String sql = "INSERT INTO food(food_id, food_name, price, status, category_name) "
					+ " VALUES(seq_food_id.nextval,?, ?, ?, ?) ";

		try {
			// 전송객체 생성
			ps = conn.prepareStatement(sql);

			ps.setString(1, food.getFoodName());
			ps.setInt(2, food.getPrice());
			ps.setString(3, food.getStatus());
			ps.setString(4, food.getCategoryName());

			// 실행
			result = ps.executeUpdate();
		} finally {
			// 객체 close
			ConnProperties.conClose(conn, ps);
		}

		return result;
	}

	@Override
	public Integer modifyFood(FoodVO before, FoodVO after) throws SQLException {
		Integer result = 0;
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();

		// 전송객체 선언
		PreparedStatement ps = null;

		// Insert SQL문
		String sql = "UPDATE food "
				+ " SET food_name = ?, price = ?, status = ?"
				+ " WHERE food_name = ? AND category_name = ? ";


		try {
			// 전송객체 생성
			ps = conn.prepareStatement(sql);

			ps.setString(1,after.getFoodName());
			ps.setInt(2,after.getPrice());
			ps.setString(3,after.getStatus());
			ps.setString(4,before.getFoodName());
			ps.setString(5,before.getCategoryName());

			// 실행
			result = ps.executeUpdate();
		} finally {
			// 객체 close
			ConnProperties.conClose(conn, ps);
		}

		return result;
	}

	@Override
	public Integer deleteFood(String foodName, String categoryName) throws SQLException {
		Integer result = 0;
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();

		// 전송객체 선언
		PreparedStatement ps = null;

		// Insert SQL문
		String sql = "DELETE FROM food "
				+ " WHERE category_name = ? AND food_name = ? ";

		try {
			// 전송객체 생성
			ps = conn.prepareStatement(sql);

			ps.setString(1, categoryName);
			ps.setString(2, foodName);

			// 실행
			result = ps.executeUpdate();
		} finally {
			// 객체 close
			ConnProperties.conClose(conn, ps);
		}

		return result;
	}

	@Override
	public ArrayList selectFoodByCategory(String categoryName) throws SQLException {
		ArrayList result = new ArrayList();
		
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();
		
		// 전송객체 선언
		PreparedStatement ps = null;
		
		//Insert SQL문
		String sql = "SELECT * "
				+ " FROM food "
				+ " WHERE category_name = ? ";
				
			try {
				// 전송객체 생성
				ps = conn.prepareStatement(sql);
				ps.setString(1, categoryName);
				
				// 실행
				ResultSet rs = ps.executeQuery();
				
				
				while(rs.next()) {
					ArrayList temp = new ArrayList();
					temp.add(rs.getString("food_name"));
					temp.add(rs.getString("price"));
					temp.add(rs.getString("status"));
					result.add(temp);
				}
			}
			finally {
				//객체 close
				ConnProperties.conClose(conn, ps);
			}
				
		return result;
	}

	@Override
	public ArrayList<FoodVO> selectFoodVOByCategory(String categoryName) throws SQLException {
		ArrayList<FoodVO> result = new ArrayList<>();
		
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();
		
		// 전송객체 선언
		PreparedStatement ps = null;
		
		//Insert SQL문
		String sql = "SELECT * "
				+ " FROM food "
				+ " WHERE category_name = ? ";
				
			try {
				// 전송객체 생성
				ps = conn.prepareStatement(sql);
				ps.setString(1, categoryName);
				
				// 실행
				ResultSet rs = ps.executeQuery();
				
				
				while(rs.next()) {
					FoodVO food = new FoodVO();
					food.setFoodId((long)rs.getInt("food_id"));
					food.setFoodName(rs.getString("food_name"));
					food.setPrice(rs.getInt("price"));
					food.setStatus(rs.getString("status"));
					food.setCategoryName(rs.getString("category_name"));
					result.add(food);
				}
			}
			finally {
				//객체 close
				ConnProperties.conClose(conn, ps);
			}
				
		return result;
	}

}
