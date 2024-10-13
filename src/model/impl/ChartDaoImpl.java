package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.ChartDao;
import model.ConnProperties;

public class ChartDaoImpl implements ChartDao{

	HashMap<String,String> map = new HashMap<>();
	
	public ChartDaoImpl() {
		map.put("카테고리", "c.category_name");
		map.put("음식이름", "f.food_name");
		map.put("판매수량", "sum(count) sum_count");
		map.put("합계금액", "sum(f.price) sum_price");
	}
	
	@Override
	public ArrayList<ArrayList> getSumCountData(String theme, String keyword) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<ArrayList> list = new ArrayList();
		
		Connection conn = null;
		PreparedStatement ps = null;
		theme = map.get(theme);
		keyword = map.get(keyword);
		
		String sql = " SELECT " + theme + ", " + keyword + " "
				+ " FROM orderdmenu om INNER JOIN food f ON om.FOOD_ID = f.FOOD_ID "
				+ "						INNER JOIN category c ON f.CATEGORY_NAME = c.CATEGORY_NAME "
				+ "GROUP BY "+theme+" ";
		
		try {
			conn = ConnProperties.getConnection();
			ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getString(1));
				temp.add(rs.getInt(2));
				list.add(temp);
			}
			
		}finally {
			ConnProperties.conClose(conn, ps);
		}
		
		return list;
	}


}
