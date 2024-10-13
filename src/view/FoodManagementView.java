package view;

import java.awt.BorderLayout;
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

public class FoodManagementView extends JPanel{
	
	//1번째 구역 레이아웃
    JPanel panel1;
    JTextField categoryTf;                      //카테고리 이름 TextField
    JButton categoryResistBtn, categoryDeleteBtn;    //등록, 삭제 버튼

    //2번째 구역 레이아웃
    JPanel panel2;
    JTable categoryTable;
    CategoryTableModel categoryTableModel;

    //3번째 구역 레이아웃
    JPanel panel3;
    JTable foodTable;
    FoodTableModel foodTableModel;

    //4번째 구역 레이아웃
    JPanel panel4;
    JTextField foodNameTf, foodPriceTf, foodStatusTf;
    JButton foodResistBtn, foodDeleteBtn, foodModifyBtn;
    
    //dao
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();
    FoodDaoImpl foodDao = new FoodDaoImpl();

    //생성자
    public FoodManagementView(){
        setLayout(new GridLayout(1,4));
        addLayout();
        setCategoryEvent();
        setFoodEvent();
        searchAllCategory();
    }

    //레이아웃
    private void addLayout(){
        addPanel1();
        addPanel2();
        addPanel3();
        addPanel4();
    }

    //1번 컴포넌트
    private void addPanel1(){
        //컴포넌트 초기화
        panel1 = new JPanel();
        panel1.setBorder(new TitledBorder("카테고리 관리"));
        categoryTf = new JTextField(10);
        categoryResistBtn = new JButton("등록");
        categoryDeleteBtn = new JButton("삭제");
        
 

        //사이즈 조절
        categoryTf.setMaximumSize(new Dimension(200, 30));

        //레이아웃 세팅
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));

        //정렬 세팅
        categoryTf.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryResistBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        categoryDeleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //컴포넌트 배치
        panel1.add(Box.createVerticalStrut(350));
        panel1.add(categoryTf);
        panel1.add(Box.createRigidArea(new Dimension(0,5)));
        panel1.add(categoryResistBtn);
        panel1.add(Box.createRigidArea(new Dimension(0,5)));
        panel1.add(categoryDeleteBtn);

        add(panel1);
    }

    //2번 컴포넌트
    private void addPanel2(){
        //컴포넌트 초기화
       panel2 = new JPanel();
       panel2.setLayout(new BorderLayout());
       panel2.setBorder(new TitledBorder("카테고리 목록"));
       categoryTableModel = new CategoryTableModel();
       categoryTable = new JTable(categoryTableModel);
       
       panel2.add(new JScrollPane(categoryTable));
       
       
       add(panel2);
    }

    //3번 컴포넌트
    private void addPanel3(){
        panel3 = new JPanel();
        panel3.setLayout(new BorderLayout());
        panel3.setBorder(new TitledBorder("음식 목록"));
        foodTableModel = new FoodTableModel();
        foodTable = new JTable(foodTableModel);
        
        panel3.add(new JScrollPane(foodTable));
        
        add(panel3);
    }
    
    //4번 컴포넌트
    private void addPanel4(){
        //컴포넌트 초기화
        panel4 = new JPanel();
        panel4.setBorder(new TitledBorder("음식 관리"));
        foodNameTf = new JTextField(10);
        foodPriceTf = new JTextField(10);
        foodStatusTf = new JTextField(10);
        foodResistBtn = new JButton("등록");
        foodDeleteBtn = new JButton("삭제");
        foodModifyBtn = new JButton("수정");

        //사이즈 조절
        foodNameTf.setMaximumSize(new Dimension(200, 30));
        foodPriceTf.setMaximumSize(new Dimension(200, 30));
        foodStatusTf.setMaximumSize(new Dimension(200, 30));

        //레이아웃 세팅
        panel4.setLayout(new BoxLayout(panel4, BoxLayout.Y_AXIS));

        //정렬 세팅
        foodNameTf.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodStatusTf.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodPriceTf.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodResistBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodDeleteBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        foodModifyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //컴포넌트 배치
        panel4.add(Box.createVerticalStrut(350));
        panel4.add(foodNameTf);
        panel4.add(Box.createRigidArea(new Dimension(0,5)));
        panel4.add(foodPriceTf);
        panel4.add(Box.createRigidArea(new Dimension(0,5)));
        panel4.add(foodStatusTf);
        panel4.add(Box.createRigidArea(new Dimension(0,5)));
        panel4.add(foodResistBtn);
        panel4.add(Box.createRigidArea(new Dimension(0,5)));
        panel4.add(foodDeleteBtn);
        panel4.add(Box.createRigidArea(new Dimension(0,5)));
        panel4.add(foodModifyBtn);

        add(panel4);
    }

    //CATEGORY CRUD 메소드------------------
    //등록
    void resistCategory() {
    	CategoryVO category = new CategoryVO();
    	category.setCategoryName(categoryTf.getText());
    	
    	try{
    		int result = categoryDao.insertCategory(category);
    		
    		if(result == 0) {
    			JOptionPane.showMessageDialog(null, "카테고리 등록 실패");
    		}else {
    			JOptionPane.showMessageDialog(null, "카테고리 등록 성공");
    		}
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(null, "내부 에러로 인한 카테고리 등록 실패");
    		e.printStackTrace();
    	}
    	
    }
    //삭제
    void deleteCategory() {
    	String categoryName = categoryTf.getText();
    	try {
    		int result = categoryDao.deleteCategory(categoryName);
    		
    		if(result != 0) {
    			JOptionPane.showMessageDialog(null, "카테고리 삭제 성공");
    		}
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(null, "내부 에러로 인한 카테고리 삭제 실패");
    		e.printStackTrace();
    	}
    }
    
    //전체조회
    void searchAllCategory() {
    	try {
			categoryTableModel.data = categoryDao.selectAllCategory();
			categoryTableModel.fireTableDataChanged();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    
    //카테고리이벤트
    private void setCategoryEvent() {
    	categoryResistBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resistCategory();
				searchAllCategory();
			}
		});
    	
    	categoryDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteCategory();
				searchAllCategory();
			}
		});
    	
    	// 검색한 열을 클릭했을 때
		categoryTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ev){
				
				try{
					categoryTf.setText(getValueAtSelectRow(categoryTable));
					 
				}catch(Exception ex){
					System.out.println("실패 : "+ ex.getMessage());
				}
				
				refreshFoodTableFromCategory();
			}
		});
    }

    //FOOD CRUD 메소드------------------
    //등록
    void resistFood() {
    	FoodVO food = new FoodVO();
    	food.setFoodName(foodNameTf.getText());
    	food.setPrice(Integer.parseInt(foodPriceTf.getText()));
    	food.setStatus(foodStatusTf.getText());
    	food.setCategoryName(getValueAtSelectRow(categoryTable));
    	
    	try{
    		int result = foodDao.insertFood(food);
    		
    		if(result != 0) {
    			JOptionPane.showMessageDialog(null, "음식 등록 성공");
    		}
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(null, "내부 에러로 인한 음식 등록 실패");
    		e.printStackTrace();
    	}finally {
    		refreshFoodTableFromCategory();
    	}
    }
    //삭제
    void deleteFood() {
    	String foodName = foodNameTf.getText();
    	String categoryName = getValueAtSelectRow(categoryTable);
    	
    	try {
    		int result = foodDao.deleteFood(foodName, categoryName);
    		
    		if(result !=0) {
    			JOptionPane.showMessageDialog(null, "음식 삭제 성공");
    		}
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(null, "내부 에러로 인한 음식 삭제 실패");
    		e.printStackTrace();
    	}finally {
    		refreshFoodTableFromCategory();
    	}
    }
    
    //수정
    void modifyFood() {
    	FoodVO before = new FoodVO();
    	FoodVO after = new FoodVO();
    	
    	before.setCategoryName(getValueAtSelectRow(categoryTable));
    	before.setFoodName(getValueAtSelectRow(foodTable));
    	after.setFoodName(foodNameTf.getText());
    	after.setPrice(Integer.parseInt(foodPriceTf.getText()));
    	after.setStatus(foodStatusTf.getText());
    	
    	try {
    		int result = foodDao.modifyFood(before, after);
    		
    		if(result !=0) {
    			JOptionPane.showMessageDialog(null, "음식 수정 성공");
    		}
    	}catch(SQLException e) {
    		JOptionPane.showMessageDialog(null, "내부 에러로 인한 음식 수정 실패");
    		e.printStackTrace();
    	}finally {
    		refreshFoodTableFromCategory();
    	}
    }
    
    //음식이벤트
    private void setFoodEvent() {
    	foodResistBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resistFood();
			}
		});
    	
    	foodDeleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteFood();
			}
		});
    	
    	foodModifyBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				modifyFood();
			}
		});
    	
    	foodTable.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent ev){
				try{
					foodNameTf.setText((String)foodTable.getValueAt(foodTable.getSelectedRow(), 0));
					foodPriceTf.setText((String)foodTable.getValueAt(foodTable.getSelectedRow(), 1));
					foodStatusTf.setText((String)foodTable.getValueAt(foodTable.getSelectedRow(), 2));
					 
				}catch(Exception ex){
					System.out.println("실패 : "+ ex.getMessage());
				}
			}
		});
    }
    
    
    //테이블로부터 선택한 열의 0번째 Text를 반환
    private String getValueAtSelectRow(JTable table) {
    	int row = table.getSelectedRow();
		int col = 0;
		String value = (String)table.getValueAt(row, col);
		
		return value;
    }
    
    //카테고리 행을 클릭하면 해당 카테고리의 음식들을 Food Table에 출력
    private void refreshFoodTableFromCategory() {
    	try {
			foodTableModel.data = foodDao.selectFoodByCategory(getValueAtSelectRow(categoryTable));
			foodTableModel.fireTableDataChanged();
		}catch(SQLException e) {
			e.printStackTrace();
		}
    }
}
