package model.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.CategoryVO;

public interface CategoryDao {
	Integer insertCategory(CategoryVO category) throws SQLException;	//카테고리 추가
	Integer deleteCategory(String categoryName) throws SQLException;	//카테고리 삭제
	ArrayList selectAllCategory() throws SQLException;					//전체 카테고리 조회(테이블용)
}
