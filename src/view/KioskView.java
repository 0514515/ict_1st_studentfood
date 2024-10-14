package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.dao.CategoryDao;
import model.dao.FoodDao;
import model.dao.OrderDao;
import model.impl.CategoryDaoImpl;
import model.impl.FoodDaoImpl;
import model.impl.OrderDaoImpl;
import model.vo.FoodVO;
import model.vo.OrderInfoVO;
import model.vo.OrderMenuVO;
import view.custom.FoodButton;
import view.tablemodel.CartTableModel;

public class KioskView extends JPanel {

	// 북쪽
	JScrollPane northScrollPane;
	JPanel scrollPanel;

	// 센터
	JScrollPane centerScrollPane;
	JPanel centerPanel;

	// 동쪽
	JPanel eastPanel;
	JTable cartTable;				//장바구니 테이블
	CartTableModel cartTableModel;	//장바구니 테이블 모델
	JTextField studentIdTf;			//학번 TextField
	JButton orderButton;			//주문 버튼
	JButton deleteButton;			//선택 삭제 버튼

	// DAO
	CategoryDao categoryDao = new CategoryDaoImpl();
	FoodDao foodDao = new FoodDaoImpl();
	OrderDao orderDao = new OrderDaoImpl();
	
	public KioskView() {
		addLayout();
	}

	void addLayout() {
		setLayout(new BorderLayout());

		// 북쪽 : 스크롤 팬 <- JPanel 주입
		northScrollPane = new JScrollPane();
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		northScrollPane.setPreferredSize(new Dimension(1440, 130));
		northScrollPane.setBorder(new TitledBorder("카테고리"));

		northScrollPane.setViewportView(scrollPanel);

		add(northScrollPane, BorderLayout.NORTH);

		// 센터 : 스크롤 팬 <- JPanel 주입
		centerScrollPane = new JScrollPane();
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,30,30));
		centerPanel.setBorder(new TitledBorder("메뉴"));
		centerScrollPane.setViewportView(centerPanel);
		add(centerPanel);

		
		//카테고리 초기 생성
		generateCategory();
		
		// 동쪽
		//주문 버튼
		orderButton = new JButton("0원 결제");
		orderButton.setPreferredSize(new Dimension(300,125));
		orderButton.addActionListener(new ActionListener() {
			
			//주문 버튼 이벤트 주입
			@Override
			public void actionPerformed(ActionEvent e) {
				order();
			}
		});
		//선택 삭제 버튼
		deleteButton = new JButton("선택 삭제");
		deleteButton.setPreferredSize(new Dimension(200,40));
		deleteButton.addActionListener(new ActionListener() {
			
			//선택 삭제 버튼 이벤트 : 행을 클릭하고 삭제하면 해당 메뉴를 장바구니에서 삭제
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int[] selectedRows = cartTable.getSelectedRows();
				if(selectedRows.length>0) {
					cartTableModel.removeFoods(selectedRows);
				}
				refreshOrderButton();
			}
		});
		
		//학번 TextField
		studentIdTf = new JTextField(20);
		studentIdTf.setPreferredSize(new Dimension(40,40));
		
		//동쪽 패널
		eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		eastPanel.setBorder(new TitledBorder("장바구니"));
		
		// 장바구니 테이블
		cartTableModel = new CartTableModel();
		cartTable = new JTable(cartTableModel);
		cartTable.setPreferredSize(new Dimension(500,700));
		JScrollPane pane = new JScrollPane(cartTable);
		pane.setPreferredSize(new Dimension(600,550));
		eastPanel.add(pane,BorderLayout.NORTH);
		
		//나머지 배치
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createRigidArea(new Dimension(10,40)));
		panel.add(new JLabel("학번"));
		panel.add(Box.createRigidArea(new Dimension(10,40)));
		panel.add(studentIdTf);
		panel.add(Box.createRigidArea(new Dimension(75,40)));
		panel.add(deleteButton);
		eastPanel.add(panel,BorderLayout.CENTER);
		eastPanel.add(orderButton,BorderLayout.SOUTH);
		

		add(eastPanel, BorderLayout.EAST);
	}

	//카테고리 버튼 생성 메소드
	void generateCategory() {
		//상단 패널 카테고리 버튼 전부 삭제
		scrollPanel.removeAll();
		
		ArrayList result = new ArrayList();
		try {
			//DB로부터 카테고리 전부 조회
			result = categoryDao.selectAllCategory();
			
			//카테고리 개수만큼 카테고리 버튼 생성 로직 실행
			for (int i = 0; i < result.size(); i++) {
				String categoryName = (String) ((ArrayList) result.get(i)).get(0);

				scrollPanel.add(Box.createRigidArea(new Dimension(30, 0)));
				JButton categoryButton = new JButton(categoryName);
				categoryButton.setPreferredSize(new Dimension(150, 70));
				
				//카테고리 버튼 클릭 시 중심 패널에 해당 카테고리 속 메뉴 버튼을 주입하는 이벤트
				categoryButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						generateFood(categoryName);
					}
				});
				scrollPanel.add(categoryButton);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//중심 패널 새로고침
		scrollPanel.revalidate();
		scrollPanel.repaint();
	}

	//카테고리 버튼을 클릭 했을 때, 카테고리명으로 받아온 음식의 정보를 음식 패널의 버튼으로 생성하는 메소드
	void generateFood(String categoryName) {
		//메뉴가 들어있던 중심 패널의 모든 버튼은 삭제
		centerPanel.removeAll();
		
		try {
			//DB로 부터 해당 카테고리의 메뉴들을 Get
			ArrayList<FoodVO> foodList = foodDao.selectFoodVOByCategory(categoryName);
			
			//메뉴 개수만큼 버튼 생성 로직 실행
			for(FoodVO food:foodList) {
				FoodButton foodButton = new FoodButton(food);
				foodButton.setPreferredSize(new Dimension((centerPanel.getWidth()/3)-45,(centerPanel.getWidth()/3)-45));
				foodButton.addActionListener(new ActionListener() {
					
					//버튼 클릭시 장바구니에 추가하고, 결제 버튼에 가격 갱신하는 이벤트
					@Override
					public void actionPerformed(ActionEvent e) {
						cartTableModel.addFood(food);
						refreshOrderButton();
					}
				});
				centerPanel.add(foodButton);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		//중심 패널 새로고침
		centerPanel.revalidate();
        centerPanel.repaint();
	}
	
	void refreshOrderButton() {
		orderButton.setText(cartTableModel.getTotalPrice()+"원 결제");
	}
	
	//결제 버튼 눌렀을 때 메소드
	void order() {
		//장바구니에 1개이상의 메뉴가 들어있을 때 작동
		if(cartTableModel.getFoodList().size()>0) {
			//주문 VO
			OrderInfoVO orderInfo = new OrderInfoVO();
			
			//학번이 공백이 아닐 때에만 작동
			if(studentIdTf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "학번이 공백입니다.");
			}else {
				//장바구니 테이블에서 OrderMenuVO 객체들을 받아와 실행
				orderInfo.setStudentId(Long.parseLong(studentIdTf.getText()));
				List<OrderMenuVO> list = new ArrayList<>();
				
				//메뉴 수 만큼 arrayList에 주입
				for(FoodVO food:cartTableModel.getFoodList()) {
					OrderMenuVO orderMenu = new OrderMenuVO();
					orderMenu.setFoodId(food.getFoodId());
					orderMenu.setCount(food.getCount());
					list.add(orderMenu);
				}
				
				try {
					// 주문VO와 메뉴VO를 담은 List로 주문 실행
					int result = orderDao.insertOrder(orderInfo, list);
					JOptionPane.showMessageDialog(null, "학번 " + studentIdTf.getText() +" 주문완료 " + result + "건");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					
					e.printStackTrace();
				}
			}
		}
		
	}
}
