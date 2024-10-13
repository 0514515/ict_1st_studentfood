package model.dao;

import java.sql.SQLException;
import java.util.List;

import model.vo.OrderInfoVO;
import model.vo.OrderMenuVO;

public interface OrderDao {
	Integer insertOrder(OrderInfoVO orderInfo, List<OrderMenuVO> orderMenus) throws SQLException;	//1개의 주문전표와 여러개의 메뉴 추가
}
