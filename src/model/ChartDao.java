package model;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ChartDao {
	
	ArrayList<ArrayList> getSumCountData(String theme, String keyword) throws SQLException;
}
