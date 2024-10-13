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

import model.CategoryDao;
import model.FoodDao;
import model.OrderDao;
import model.impl.CategoryDaoImpl;
import model.impl.FoodDaoImpl;
import model.impl.OrderDaoImpl;
import model.vo.FoodVO;
import model.vo.OrderInfoVO;
import model.vo.OrderMenuVO;
import view.custom.FoodButton;
import view.tableModel.CartTableModel;

public class KioskView extends JPanel {

	// 북쪽
	JScrollPane northScrollPane;
	JPanel scrollPanel;

	// 센터
	JScrollPane centerScrollPane;
	JPanel centerPanel;

	// 동쪽
	JPanel eastPanel;
	JTable cartTable;
	CartTableModel cartTableModel;
	JTextField studentIdTf;
	JButton orderButton;
	JButton deleteButton;

	// DAO
	CategoryDao categoryDao = new CategoryDaoImpl();
	FoodDao foodDao = new FoodDaoImpl();
	OrderDao orderDao = new OrderDaoImpl();
	
	public KioskView() {
		addLayout();
	}



	void addLayout() {
		setLayout(new BorderLayout());

		// 북쪽
		northScrollPane = new JScrollPane();
		scrollPanel = new JPanel();
		scrollPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		northScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		northScrollPane.setPreferredSize(new Dimension(1440, 130));
		northScrollPane.setBorder(new TitledBorder("카테고리"));

		northScrollPane.setViewportView(scrollPanel);

		add(northScrollPane, BorderLayout.NORTH);

		// 센터
		centerScrollPane = new JScrollPane();
		centerPanel = new JPanel();
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT,30,30));
		centerPanel.setBorder(new TitledBorder("메뉴"));
		centerScrollPane.setViewportView(centerPanel);
		add(centerPanel);

		
		//카테고리와 음식을 DB로부터 가져와서 버튼 생성
				generateCategoryAndFood();
		// 동쪽
		orderButton = new JButton("0원 결제");
		orderButton.setPreferredSize(new Dimension(300,125));
		orderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				order();
			}
		});
		deleteButton = new JButton("선택 삭제");
		deleteButton.setPreferredSize(new Dimension(200,40));
		deleteButton.addActionListener(new ActionListener() {
			
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
		studentIdTf = new JTextField(20);
		studentIdTf.setPreferredSize(new Dimension(40,40));
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

	void generateCategoryAndFood() {
		ArrayList result = new ArrayList();
		try {
			result = categoryDao.selectAllCategory();
			for (int i = 0; i < result.size(); i++) {
				String categoryName = (String) ((ArrayList) result.get(i)).get(0);

				scrollPanel.add(Box.createRigidArea(new Dimension(30, 0)));
				JButton categoryButton = new JButton(categoryName);
				categoryButton.setPreferredSize(new Dimension(150, 70));
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
	}

	void generateFood(String categoryName) {
		centerPanel.removeAll();
		
		try {
			ArrayList<FoodVO> foodList = foodDao.selectFoodVOByCategory(categoryName);
			for(FoodVO food:foodList) {
				FoodButton foodButton = new FoodButton(food);
				foodButton.setPreferredSize(new Dimension((centerPanel.getWidth()/3)-45,(centerPanel.getWidth()/3)-45));
				foodButton.addActionListener(new ActionListener() {
					
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
		centerPanel.revalidate();
        centerPanel.repaint();
	}
	
	void refreshOrderButton() {
		orderButton.setText(cartTableModel.getTotalPrice()+"원 결제");
	}
	
	void order() {
		if(cartTableModel.getFoodList().size()>0) {
			OrderInfoVO orderInfo = new OrderInfoVO();
			if(studentIdTf.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "학번이 공백입니다.");
			}else {
				orderInfo.setStudentId(Long.parseLong(studentIdTf.getText()));
				List<OrderMenuVO> list = new ArrayList<>();
				
				for(FoodVO food:cartTableModel.getFoodList()) {
					OrderMenuVO orderMenu = new OrderMenuVO();
					orderMenu.setFoodId(food.getFoodId());
					orderMenu.setCount(food.getCount());
					list.add(orderMenu);
				}
				
				try {

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
