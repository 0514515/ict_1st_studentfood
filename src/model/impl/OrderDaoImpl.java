package model.impl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.ConnProperties;
import model.dao.OrderDao;
import model.vo.OrderInfoVO;
import model.vo.OrderMenuVO;

public class OrderDaoImpl implements OrderDao {

	//주문 전표 1개와 메뉴들 INSERT
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
				// 주문 등록(주문번호는 시퀀스)
				ps = conn.prepareStatement(orderInfoSql);
				ps.setLong(1, orderInfo.getStudentId());
				ps.executeUpdate();
				
				// 주문을 선등록 후 해당 주문의 id를 시퀀스의 currval로 조회
	            String selectSql = "SELECT seq_orderinfo_id.currval FROM dual";
	            
	            // 방금 등록한 주문 번호를 바로 가져오기 위한 select문
	            ps = conn.prepareStatement(selectSql);
	            ResultSet rs = ps.executeQuery();
	            rs.next();

	            // 주문 속 메뉴들 등록(카드에 있던 메뉴 종류의 수만큼 반복)
				for(int i=0;i<orderMenus.size();i++) {
					OrderMenuVO orderMenu = orderMenus.get(i);
					
					String sql = "INSERT INTO orderdMenu(order_menu_id, order_id, food_id, count) "
							+ " VALUES (seq_orderdmenu_id.nextval, ?, ?, ?) ";
					
					menuPs = conn.prepareStatement(sql);
					menuPs.setLong(1,rs.getLong(1));			//방금 주문한 주문번호
					menuPs.setLong(2,orderMenu.getFoodId());	//메뉴id
					menuPs.setInt(3, orderMenu.getCount());		//해당 메뉴(음식)의 개수 입력
					int temp = menuPs.executeUpdate();
					result += temp;								//n개 결과 완료 출력을 위한 쿼리 완료 개수 카운팅
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
