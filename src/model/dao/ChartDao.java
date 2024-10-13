package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ChartDao {
	
	ArrayList<ArrayList> getSumCountData(String theme, String keyword) throws SQLException;	//카트 데이터 조회
}
