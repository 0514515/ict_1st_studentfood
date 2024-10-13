package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.FoodVO;

public interface FoodDao {
	Integer insertFood(FoodVO food) throws SQLException;								//음식 추가
	Integer modifyFood(FoodVO before, FoodVO after) throws SQLException;				//음식 수정
	Integer deleteFood(String foodName, String categoryName) throws SQLException;		//음식 삭제
	ArrayList selectFoodByCategory(String categoryName) throws SQLException;			//음식 관리에서 카테고리의 모든 음식 조회(음식관리 속 테이블용)
	ArrayList<FoodVO> selectFoodVOByCategory(String categoryName) throws SQLException;	//Kiosk에서 카테고리의 음식을 객체로 조회(Kiosk 속 음식 button용)
}