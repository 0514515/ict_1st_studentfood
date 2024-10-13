package model.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import model.ConnProperties;
import model.dao.ChartDao;

public class ChartDaoImpl implements ChartDao{

	HashMap<String,String> map = new HashMap<>();
	
	
	//Chart에서 콤보박스 속 String에 따라 조회용 변수를 바꾸기 위한 Mapping
	public ChartDaoImpl() {
		//차트 조회 테마
		map.put("카테고리", "c.category_name");
		map.put("음식이름", "f.food_name");
		
		//차트 조회 키워드
		map.put("판매수량", "sum(count) sum_count");
		map.put("합계금액", "sum(f.price) sum_price");
	}
	
	
	//차트에서 테마, 키워드 콤보박스에 따라 통계 결과 조회
	@Override
	public ArrayList<ArrayList> getSumCountData(String theme, String keyword) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<ArrayList> list = new ArrayList();
		
		//연결객체, 전송객체 선언
		Connection conn = null;
		PreparedStatement ps = null;
		
		//테마와 키워드에 따른 db 변수 get
		theme = map.get(theme);
		keyword = map.get(keyword);
		
		//sql
		String sql = " SELECT " + theme + ", " + keyword + " "
				+ " FROM orderdmenu om INNER JOIN food f ON om.FOOD_ID = f.FOOD_ID "
				+ "						INNER JOIN category c ON f.CATEGORY_NAME = c.CATEGORY_NAME "
				+ "GROUP BY "+theme+" ";
		
		try {
			
			//연결객체, 전송객체 생성
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
			//종료
			ConnProperties.conClose(conn, ps);
		}
		
		return list;
	}


}
