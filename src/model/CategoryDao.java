package model;

import java.sql.SQLException;
import java.util.ArrayList;

import model.vo.CategoryVO;

public interface CategoryDao {
	Integer insertCategory(CategoryVO category) throws SQLException;
	Integer deleteCategory(String categoryName) throws SQLException;
	ArrayList selectAllCategory() throws SQLException;
}
