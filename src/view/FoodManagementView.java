package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.impl.CategoryDaoImpl;
import model.impl.FoodDaoImpl;
import model.vo.CategoryVO;
import model.vo.FoodVO;
import view.tableModel.CategoryTableModel;
import view.tableModel.FoodTableModel;

public class FoodManagementView extends JPanel {

	// 1번째 구역 레이아웃
	JPanel panel1;
	JTextField categoryTf; // 카테고리 이름 TextField
	JButton categoryResistBtn, categoryDeleteBtn; // 등록, 삭제 버튼

	// 2번째 구역 레이아웃
	JPanel panel2;
	JTable categoryTable; // 카테고리 목록용 Table
	CategoryTableModel categoryTableModel; // 카테고리 Table Model

	// 3번째 구역 레이아웃
	JPanel panel3;
	JTable foodTable; // 음식 목록용 Table
	FoodTableModel foodTableModel; // 음식 TableModel

	// 4번째 구역 레이아웃
	JPanel panel4;
	JTextField foodNameTf, foodPriceTf, foodStatusTf; // 음식 이름, 가격, 판매상태 TextField
	JButton foodResistBtn, foodDeleteBtn, foodModifyBtn; // 음식 등록, 삭제, 수정 TextField

	// dao
	CategoryDaoImpl categoryDao = new CategoryDaoImpl(); // 카테고리 DB용 DAO
	FoodDaoImpl foodDao = new FoodDaoImpl(); // 음식 DB용 DAO

	// 카테고리나 음식을 수정했을 때 refresh를 위해 인스턴스 저장용 변수
	KioskView kioskView;

	// TextField하단의 알림 용 JLabel
	JLabel categoryAlertLabel = new JLabel("");
	JLabel foodAlertLabel = new JLabel("");

	// 생성자
	public FoodManagementView(KioskView kioskView) {
		this.kioskView = kioskView;
		setLayout(new GridLayout(1, 4));
		addLayout();
		setCategoryEvent();
		setFoodEvent();
		searchAllCategory();
	}

	// 레이아웃
	private void addLayout() {
		categoryAlertLabel.setForeground(Color.red);
		foodAlertLabel.setForeground(Color.red);
		addPanel1();
		addPanel2();
		addPanel3();
		addPanel4();
	}

	// 1번 컴포넌트
	private void addPanel1() {
		// 컴포넌트 초기화
		panel1 = new JPanel();
		panel1.setBorder(new TitledBorder("카테고리 관리"));
		categoryTf = new JTextField(10);
		categoryResistBtn = new JButton("등록");
		categoryDeleteBtn = new JButton("삭제");

		// 사이즈 조절
		panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoryTf.setMaximumSize(new Dimension(200,30));

		// 레이아웃 세팅
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

		// 정렬 세팅
		categoryTf.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoryResistBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoryDeleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		categoryAlertLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 컴포넌트 배치
		panel1.add(Box.createVerticalStrut(350));
		panel1.add(categoryTf);
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		panel1.add(categoryResistBtn);
		panel1.add(Box.createRigidArea(new Dimension(0, 5)));
		panel1.add(categoryDeleteBtn);
		panel1.add(Box.createRigidArea(new Dimension(0, 10)));
		panel1.add(categoryAlertLabel);
		

		add(panel1);
	}

	// 2번 컴포넌트
	private void addPanel2() {
		// 컴포넌트 초기화
		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel2.setBorder(new TitledBorder("카테고리 목록"));
		categoryTableModel = new CategoryTableModel();
		categoryTable = new JTable(categoryTableModel);

		panel2.add(new JScrollPane(categoryTable));

		add(panel2);
	}

	// 3번 컴포넌트
	private void addPanel3() {
		// 컴포넌트 초기화
		panel3 = new JPanel();
		panel3.setLayout(new BorderLayout());
		panel3.setBorder(new TitledBorder("음식 목록"));
		foodTableModel = new FoodTableModel();
		foodTable = new JTable(foodTableModel);

		panel3.add(new JScrollPane(foodTable));

		add(panel3);
	}

	// 4번 컴포넌트
	private void addPanel4() {
		// 컴포넌트 초기화
		panel4 = new JPanel();
		panel4.setBorder(new TitledBorder("음식 관리"));
		foodNameTf = new JTextField(10);
		foodPriceTf = new JTextField(10);
		foodStatusTf = new JTextField(10);
		foodResistBtn = new JButton("등록");
		foodDeleteBtn = new JButton("삭제");
		foodModifyBtn = new JButton("수정");

		// 사이즈 조절
		
		foodNameTf.setMaximumSize(new Dimension(200, 30));
		foodPriceTf.setMaximumSize(new Dimension(200, 30));
		foodStatusTf.setMaximumSize(new Dimension(200, 30));
		panel4.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 레이아웃 세팅
		panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

		// 정렬 세팅
		foodNameTf.setAlignmentX(Component.CENTER_ALIGNMENT);
		foodStatusTf.setAlignmentX(Component.CENTER_ALIGNMENT);
		foodPriceTf.setAlignmentX(Component.CENTER_ALIGNMENT);
		foodResistBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		foodDeleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		foodModifyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
		foodAlertLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

		// 컴포넌트 배치
		panel4.add(Box.createVerticalStrut(350));
		panel4.add(foodNameTf);
		panel4.add(Box.createRigidArea(new Dimension(0, 5))); // 각 Tf사이의 패딩용으로 비어있는 블럭 추가
		panel4.add(foodPriceTf);
		panel4.add(Box.createRigidArea(new Dimension(0, 5)));
		panel4.add(foodStatusTf);
		panel4.add(Box.createRigidArea(new Dimension(0, 5)));
		panel4.add(foodResistBtn);
		panel4.add(Box.createRigidArea(new Dimension(0, 5)));
		panel4.add(foodDeleteBtn);
		panel4.add(Box.createRigidArea(new Dimension(0, 5)));
		panel4.add(foodModifyBtn);
		panel4.add(Box.createRigidArea(new Dimension(0, 10)));
		panel4.add(foodAlertLabel);

		add(panel4);
	}

	// ---------------- CATEGORY CRUD 메소드 ------------------

	// 카테고리 이름 검사
	boolean validateCategoryName() {
		if (categoryTf.getText().isBlank() || categoryTf.getText() == null) {
			return false;
		}

		return true;
	}

	// 등록
	void resistCategory() {
		// 카테고리 textfield가 비어서 category 객체의 값이 empty이거나 null이라면 실행 x
		if (!validateCategoryName()) {
			categoryAlertLabel.setText("등록 : 카테고리 이름을 입력하세요");
			return;
		}

		// 카테고리 생성
		CategoryVO category = new CategoryVO();
		category.setCategoryName(categoryTf.getText());

		try {

			// 카테고리 추가 실행
			int result = categoryDao.insertCategory(category);

			// 카테고리 추가 시 KioskView의 카테고리, 음식 업데이트
			kioskView.generateCategoryAndFood();

			categoryAlertLabel.setText("등록 : 카테고리 생성 성공");
		} catch (Exception e) {
			categoryAlertLabel.setText(categoryErrorHandle(e));
		}

	}

	// 삭제
	void deleteCategory() {
		// 카테고리 textfield가 비어서 category 객체의 값이 empty이거나 null이라면 실행 x
		if (!validateCategoryName()) {
			categoryAlertLabel.setText("삭제 : 카테고리 이름을 입력하세요");
			return;
		}

		// 카테고리 이름을 TextField로부터 Get
		String categoryName = categoryTf.getText();
		try {
			// 카테고리 속 음식이 있으면 삭제 불가능 메시지
			// 삭제 실행
			int result = categoryDao.deleteCategory(categoryName);

			// 카테고리 삭제 시 KioskView의 카테고리, 음식 업데이트
			kioskView.generateCategoryAndFood();

			categoryAlertLabel.setText("삭제 : 카테고리 삭제 성공");

		} catch (SQLException e) {
			categoryAlertLabel.setText(categoryErrorHandle(e));
		}
	}

	// 전체조회 : 조회한 ArrayList를 Model에 즉시 반영
	void searchAllCategory() {
		try {
			categoryTableModel.data = categoryDao.selectAllCategory();
			categoryTableModel.fireTableDataChanged();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			categoryAlertLabel.setText("조회 : " + categoryErrorHandle(e));
		}
	}

	// 카테고리이벤트
	private void setCategoryEvent() {
		// 카테고리 등록
		categoryResistBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resistCategory();
				searchAllCategory();
			}
		});

		// 카테고리 삭제
		categoryDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCategory();
				searchAllCategory();
			}
		});

		// 카테고리 테이블에서 한 행을 클릭했을 때
		categoryTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {

				try {
					categoryTf.setText(getValueAtSelectRow(categoryTable));

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "카테고리 조회 : " + categoryErrorHandle(ex));
				}

				refreshFoodTableFromCategory();
			}
		});
	}

	// -------------- FOOD CRUD 메소드------------------
	// 카테고리 이름 검사
	boolean validateFoodName() {
		if (
				foodNameTf.getText().isBlank() || foodNameTf.getText() == null ||
				foodPriceTf.getText().isBlank() || foodPriceTf.getText() == null ||
				foodStatusTf.getText().isBlank() || foodStatusTf.getText() == null) {
			return false;
		}

		return true;
	}
	
	// 음식 등록
	void resistFood() {
		if(!validateFoodName()) {
			foodAlertLabel.setText("등록 : 입력 값 중 공백이 있습니다. 값을 채워주세요");
			return;
		}
		
		if(categoryTable.getSelectedRow()==-1) {
			foodAlertLabel.setText("등록 : 카테고리를 선택해주세요");
			return;
		}
		
		if(foodTable.getSelectedRow()==-1) {
			foodAlertLabel.setText("수정 : 음식을 선택해주세요");
			return;
		}
		
		// 음식 생성 : TextField로부터 음식의 정보를 가져온다.
		FoodVO food = new FoodVO();
		food.setFoodName(foodNameTf.getText()); // 음식 이름
		food.setPrice(Integer.parseInt(foodPriceTf.getText())); // 음식 가격
		food.setStatus(foodStatusTf.getText()); // 음식 판매 상태
		food.setCategoryName(getValueAtSelectRow(categoryTable)); // 음식 카테고리 이름

		try {
			int result = foodDao.insertFood(food);

			foodAlertLabel.setText("등록 : 등록 성공");
		} catch (SQLException e) {
			foodAlertLabel.setText("등록 " + foodErrorHandle(e));
		} finally {
			refreshFoodTableFromCategory();
		}
	}

	// 음식 삭제
	void deleteFood() {
		if(!validateFoodName()) {
			foodAlertLabel.setText("삭제 : 입력 값 중 공백이 있습니다. 값을 채워주세요");
			return;
		}
		
		if(categoryTable.getSelectedRow()==-1) {
			foodAlertLabel.setText("삭제 : 카테고리를 선택해주세요");
			return;
		}
		
		if(foodTable.getSelectedRow()==-1) {
			foodAlertLabel.setText("수정 : 음식을 선택해주세요");
			return;
		}
		
		// 카테고리 속 음식 삭제을 위하여, 카테고리 이름, 음식 이름을 Get
		String foodName = foodNameTf.getText();
		String categoryName = getValueAtSelectRow(categoryTable);

		try {
			// 해당 카테고리 속 음식 삭제
			int result = foodDao.deleteFood(foodName, categoryName);

			foodAlertLabel.setText("삭제 : 음식 삭제 성공");
			
			foodNameTf.setText("");
			foodPriceTf.setText("");
			foodStatusTf.setText("");
		} catch (Exception e) {
			foodAlertLabel.setText("삭제 : " + foodErrorHandle(e));
		} finally {
			refreshFoodTableFromCategory();
		}
	}

	// 음식 수정
	void modifyFood() {
		if(!validateFoodName()) {
			foodAlertLabel.setText("수정 : 입력 값 중 공백이 있습니다. 값을 채워주세요");
			return;
		}
		
		if(categoryTable.getSelectedRow()==-1) {
			foodAlertLabel.setText("수정 : 카테고리를 선택해주세요");
			return;
		}
		
		if(foodTable.getSelectedRow()==-1) {
			foodAlertLabel.setText("수정 : 음식을 선택해주세요");
			return;
		}
		
		// 수정 전 음식 정보와 수정 후 음식 생성
		FoodVO before = new FoodVO();
		FoodVO after = new FoodVO();

		// 수정 전 음식 : JTable에 있는 것 Get
		before.setCategoryName(getValueAtSelectRow(categoryTable));
		before.setFoodName(getValueAtSelectRow(foodTable));

		// 수정 후 음식 : JTextField의 사용자 입력 Get
		after.setFoodName(foodNameTf.getText());
		after.setPrice(Integer.parseInt(foodPriceTf.getText()));
		after.setStatus(foodStatusTf.getText());

		try {
			// 수정
			int result = foodDao.modifyFood(before, after);

			foodAlertLabel.setText("수정 : 음식 수정 성공");
		} catch (SQLException e) {
			foodAlertLabel.setText("수정 : " + foodErrorHandle(e));
		} finally {
			refreshFoodTableFromCategory();
		}
	}

	// 음식이벤트
	private void setFoodEvent() {
		// 음식 등록 버튼
		foodResistBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resistFood();
			}
		});

		// 음식 삭제 버튼
		foodDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFood();
			}
		});

		// 음식 수정 버튼
		foodModifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifyFood();
			}
		});

		// 음식 JTable 속 행 클릭 시 음식용 JTextField에 반영
		foodTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent ev) {
				try {
					foodNameTf.setText((String) foodTable.getValueAt(foodTable.getSelectedRow(), 0));
					foodPriceTf.setText((String) foodTable.getValueAt(foodTable.getSelectedRow(), 1));
					foodStatusTf.setText((String) foodTable.getValueAt(foodTable.getSelectedRow(), 2));

				} catch (Exception ex) {
					System.out.println("실패 : " + ex.getMessage());
				}
			}
		});
	}

	// 테이블로부터 선택한 열의 0번째 Text를 반환
	private String getValueAtSelectRow(JTable table) {
		int row = table.getSelectedRow();
		int col = 0;
		String value = (String) table.getValueAt(row, col);

		return value;
	}

	// 카테고리 행을 클릭하면 해당 카테고리의 음식들을 Food Table에 출력
	private void refreshFoodTableFromCategory() {
		try {
			foodTableModel.data = foodDao.selectFoodByCategory(getValueAtSelectRow(categoryTable));
			foodTableModel.fireTableDataChanged();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 카테고리 예외 발생 시 송출할 에러 메시지 반환
	public String categoryErrorHandle(Exception e) {
		e.printStackTrace();

		// DB에러 발생시
		if (e instanceof SQLException) {
			SQLException se = (SQLException) e;
			switch (se.getErrorCode()) {
			case 1:
				return "이미 존재하는 카테고리입니다.";
			case 2292:
				return "카테고리에 음식이 있으므로 삭제할 수 없습니다.";
			}
		}
		return "에러가 발생하였습니다";
	}

	// 음식 예외 발생 시 송출할 에러 메시지 반환
	public String foodErrorHandle(Exception e) {
		e.printStackTrace();

		// DB에러 발생시
		if (e instanceof SQLException) {
			SQLException se = (SQLException) e;
			switch (se.getErrorCode()) {
			case 1:
				return "이미 존재하는 음식입니다.";
			}
		}
		return "에러가 발생하였습니다";

	}
}
