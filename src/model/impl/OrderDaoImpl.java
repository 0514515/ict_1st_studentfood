package model.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ConnProperties;
import model.OrderDao;
import model.vo.OrderInfoVO;
import model.vo.OrderMenuVO;

public class OrderDaoImpl implements OrderDao {

	@Override
	public Integer insertOrder(OrderInfoVO orderInfo, List<OrderMenuVO> orderMenus) throws SQLException {
		Integer result = 0;
		// 연결객체 선언 및 초기화
		Connection conn = ConnProperties.getConnection();
		// 주문 등록과 주문한 메뉴를 트랜잭션화
		conn.setAutoCommit(false);

		// 전송객체 선언
		PreparedStatement ps = null;
		PreparedStatement menuPs = null;

		// Insert SQL문
		String orderInfoSql = "INSERT INTO orderInfo(order_id, student_id, created_at) "
				+ " VALUES(seq_orderinfo_id.nextval,?, sysdate) ";

		try {
			// 메뉴가 1개 이상일 때
			// 주문 속 메뉴들 등록
			if(orderMenus.size()>0) {
				// 주문 등록
				ps = conn.prepareStatement(orderInfoSql);
				ps.setLong(1, orderInfo.getStudentId());
				ps.executeUpdate();
				
				 // 생성된 order_id 가져오기
	            String selectSql = "SELECT seq_orderinfo_id.currval FROM dual";
	            ps = conn.prepareStatement(selectSql);
	            ResultSet rs = ps.executeQuery();
	            rs.next();

				for(int i=0;i<orderMenus.size();i++) {
					OrderMenuVO orderMenu = orderMenus.get(i);
					String sql = "INSERT INTO orderdMenu(order_menu_id, order_id, food_id, count) "
							+ " VALUES (seq_orderdmenu_id.nextval, ?, ?, ?) ";
					menuPs = conn.prepareStatement(sql);
					menuPs.setLong(1,rs.getLong(1));
					menuPs.setLong(2,orderMenu.getFoodId());
					menuPs.setInt(3, orderMenu.getCount());
					int temp = menuPs.executeUpdate();
					result += temp;
				}
				
				// 모두 완료하면 커밋
				conn.commit();
			}else {
				//메뉴가 0개면 그냥 취소하고 롤백
				conn.rollback();
				
			}
		} finally {
			conn.close();
			ps.close();
		}

		return result;
	}

}
