package model;

import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.FoodVO;

public interface FoodDao {
	Integer insertFood(FoodVO food) throws SQLException;
	Integer modifyFood(FoodVO before, FoodVO after) throws SQLException;
	Integer deleteFood(String foodName, String categoryName) throws SQLException;
	ArrayList selectFoodByCategory(String categoryName) throws SQLException;
	ArrayList<FoodVO> selectFoodVOByCategory(String categoryName) throws SQLException;
}