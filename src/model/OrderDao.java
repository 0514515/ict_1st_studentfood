package model;

import java.sql.SQLException;
import java.util.List;

import model.vo.OrderInfoVO;
import model.vo.OrderMenuVO;

public interface OrderDao {
	Integer insertOrder(OrderInfoVO orderInfo, List<OrderMenuVO> orderMenus) throws SQLException;
}
