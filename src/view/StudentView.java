package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import model.dao.StudentDao;
import model.impl.StudentDaoImpl;
import model.vo.StudentVO;
import view.tablemodel.StudentTableModel;

public class StudentView extends JPanel {

	//등록및 수정 창
	JTextField tfId,     //학번 
	           tfName,   //이름
	           tfMajor,  //학과
	           tfBirth,  //생년월일
	           tfAddr,   //주소
	           tfGender; //성별
	 
	JButton    bInsert,  //학생정보 등록 버튼
	           bModify,  // 수정 버튼
	           bDelete;  //삭제 버튼
	
	
	JTextArea  ta;       //특이사항 출력
	//검색창
	JTextField Search;   //검색 창
 	JButton    bSearch;  //검색 버튼
	JComboBox  com;      //검색 콤보박스
	
	//목록
	JTable     tableStudent;//학생목록
    StudentTableModel tsList; 
    
    //모델단 변수선언
    StudentDao dao;
    
	
	public StudentView() {
		addLayout();
		connectDB();
		eventProc();
	}
	
	//디비 연결
	public void connectDB() {
		
		try {
			dao = new StudentDaoImpl();
		}catch(Exception e) {
			System.out.println("학생관리 로딩 실패");
			e.printStackTrace();
		}
	}
	
	//이벤트 처리
	public void eventProc() {
		
		//학생정보입력
		bInsert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
			
				insertSt();      
				clearTextField();   //버튼 작동시 텍스트필드 입력값 초기화 
				tsList.removeAll(); //JTable에 있는 목록 초기화
			}
		});
		
		//학생정보 수정
		bModify.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
			
				modifySt();
				clearTextField();   //버튼 작동시 텍스트필드 입력값 초기화 
				tsList.removeAll(); //JTable에 있는 목록 초기화
			}
		});
			
		//학생정보 삭제 (활동중인 사람은1 안하는 사람은 0)
		bDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
			
				deleteSt();
				clearTextField();   //버튼 작동시 텍스트필드 입력값 초기화 
				tsList.removeAll(); //JTable에 있는 목록 초기화
			}
		});
		//학생 검색 ( 상태가 1인 사람만)
		bSearch.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev){
		
				searchSt();
				clearTextField(); //버튼 작동시 텍스트필드 입력값 초기화 
			}
		});	
		
		//학생테이블에서 학생 클릭했을때 정보 뜨게
		tableStudent.addMouseListener(new MouseAdapter() {
		   public void mouseClicked(MouseEvent ev) {
			   
			   try {
				   int row = tableStudent.getSelectedRow();
				   int col = 0;
				  
				   int sNum = Integer.parseInt((String)tableStudent.getValueAt(row, col));
				   //vo의 각각의 값들을 각각화면에 출력
				   StudentVO result = dao.selectByPK(sNum); 
				   tfId.setText(result.getSTUDENT_ID());  //학번
				   tfName.setText(result.getNAME());      //이름
				   tfMajor.setText(result.getMAJOR());    //학과
				   tfBirth.setText(result.getBIRTHDAY()); //생년월일
				   tfAddr.setText(result.getADDRESS());   //주소
				   tfGender.setText(result.getGENDER());  //성별
				   ta.setText(result.getDESCRIPTION());   //특이사항
				   
			   }catch(Exception e) {
				   System.out.println("실패:"+ e.getMessage());
				   e.printStackTrace();
			   }
			   
		   }
			
		});
		
		
	}//이벤트처리 끝
	
	//학생정보 입력
	public void insertSt() {
		
		StudentVO vo = new StudentVO();
		vo.setSTUDENT_ID(tfId.getText());  //학번
		vo.setNAME(tfName.getText());      //이름
		vo.setMAJOR(tfMajor.getText());    //학과
		vo.setBIRTHDAY(tfBirth.getText()); //생년월일
		vo.setADDRESS(tfAddr.getText());   //주소
		vo.setGENDER(tfGender.getText());  //성별
		vo.setDESCRIPTION(ta.getText());   //특이사항
		
		try {
			dao.insertStudent(vo);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		
	}
	

	//학생 정보 검색 
	public void searchSt() {
		
		int idx = com.getSelectedIndex();
		String word  = Search.getText();
		try {
		ArrayList data = dao.selectStudentByKeyword(idx, word);
		tsList.data = data;
		tsList.fireTableDataChanged();		
		}catch(Exception e){
			System.out.println("검색 실패");
			e.printStackTrace();
			
		}
		
		
	}
	
	//학생정보 수정
	public void modifySt() {
		
		String NAME = tfName.getText();     //이름
		String MAJOR = tfMajor.getText();   //학과
		String BIRTHDAY = tfBirth.getText();//생년월일
		String ADDRESS  =tfAddr.getText();  //주소
		String GENDER = tfGender.getText(); //성별
		String DESCRIPTION = ta.getText();  //특이사항
		String STUDENT_ID =tfId.getText();  //학번
		
		StudentVO vo = new StudentVO();
		vo.setNAME(NAME);                   //이름
		vo.setMAJOR(MAJOR);                 //학과
		vo.setBIRTHDAY(BIRTHDAY);           //생년월일
		vo.setADDRESS(ADDRESS);             //주소
		vo.setGENDER(GENDER);               //성별
		vo.setDESCRIPTION(DESCRIPTION);     //특이사항
		vo.setSTUDENT_ID(STUDENT_ID);       //학번
		
		try {
			dao.modifyStudent(vo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//정보 삭제 (status를 1에서 0으로 바꾼다)
	public void deleteSt() {
		
		String STUDENT_ID = tfId.getText();
		
		StudentVO vo = new StudentVO();
		vo.setSTUDENT_ID(STUDENT_ID);
		try {
			dao.deleteStudent(vo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//화면구성
	void addLayout() {
		
		tfId = new JTextField(20);    //학번
		tfName = new JTextField(20);  //이름
		tfMajor = new JTextField(20); //학과
		tfBirth = new JTextField(20); //생년월일
		tfAddr = new JTextField(20);  //주소
		tfGender = new JTextField(20);//성별
		
		bInsert = new JButton("등록");
		bModify = new JButton("수정");
		bDelete = new JButton("삭제"); // Status를 1에서 0으로 업데이트하는 버튼
		bSearch = new JButton("검색"); //상태가 1인 학생만 검색하기
		
		ta = new JTextArea();  //특이사항
		
		
		
		String[]cbSearch = {"학번","이름","학과"};
		com = new JComboBox(cbSearch);
		Search = new JTextField(15);//검색창
		
		tsList = new StudentTableModel();
		tableStudent = new JTable(tsList);
		
		//화면 구성 
		
		// 북쪽
		  //북쪽에서 왼쪽
		JPanel p_north_left = new JPanel();
		p_north_left.setLayout(new GridLayout(6,2,20,20));
		p_north_left.setBorder(new TitledBorder(" 학생 정보 "));
		p_north_left.add(new JLabel("학번"));
		tfId.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
		p_north_left.add(tfId);
		p_north_left.add(new JLabel("이름"));
		tfName.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
		p_north_left.add(tfName);
		p_north_left.add(new JLabel("학과"));
		tfMajor.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
		p_north_left.add(tfMajor);
		p_north_left.add(new JLabel("생년월일"));
		tfBirth.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
		p_north_left.add(tfBirth);
		p_north_left.add(new JLabel("주소"));
		tfAddr.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
		p_north_left.add(tfAddr);
		p_north_left.add(new JLabel("성별"));
		tfGender.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
		p_north_left.add(tfGender);
		
		JPanel p_north_left1 = new JPanel();
		p_north_left1.setBorder(new TitledBorder(""));
		p_north_left1.setLayout(new GridLayout(1,3));
		p_north_left1.add(bInsert);
		p_north_left1.add(bModify);
		p_north_left1.add(bDelete);
		
		JPanel p_north_left_all = new JPanel();
		p_north_left_all.setLayout(new BorderLayout());
		p_north_left_all.add(p_north_left, BorderLayout.NORTH);
		p_north_left_all.add(p_north_left1, BorderLayout.SOUTH);
		
		//북쪽에서 오른쪽
		JScrollPane pane = new JScrollPane();
		pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    JPanel p_north_right = new JPanel();
	    ta.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
	    ta.setLineWrap(true);
	    pane.setViewportView(ta);
	    p_north_right.setLayout(new BorderLayout());
	    pane.setPreferredSize(new Dimension(100,240));
	    p_north_right.add(pane, BorderLayout.CENTER);
	    
	    
	    //북쪽에서 오른쪽에서 아래
	    JPanel p_north_right1 = new JPanel();
	    p_north_right1.setLayout(new BorderLayout());
	    p_north_right1.add(com, BorderLayout.WEST);
	    Search.setFont(new Font ("맑은 고딕",Font.PLAIN,15));
	    p_north_right1.add(Search, BorderLayout.CENTER);
	    p_north_right1.add(bSearch, BorderLayout.EAST);
	    
	    //북쪽에서 오른쪽 합치기
	    JPanel p_north_right_all = new JPanel();
	    p_north_right_all.setLayout(new BorderLayout());
	    p_north_right_all.add(p_north_right, BorderLayout.NORTH);
	    p_north_right_all.add(p_north_right1,BorderLayout.SOUTH);
	    
	    
	    //북쪽 합치기
	    JPanel p_north = new JPanel();
	    p_north.setLayout(new GridLayout(1,2));
	    p_north.add(p_north_left_all);
	    p_north.add(p_north_right_all);
	    
	    
	    //남쪽
	   
	    JPanel p_south = new JPanel();
	   
	    //전체붙이기
	    setLayout(new BorderLayout());
	    add(p_north,BorderLayout.NORTH);
	    add(new JScrollPane(tableStudent), BorderLayout.CENTER);
	
		
	}
	
	//텍스트필드에서 입력한 값들 초기화하는 함수
	void clearTextField() {
		tfId.setText("");
		tfName.setText("");
		tfAddr.setText("");
		tfBirth.setText("");
		tfGender.setText("");
		tfMajor.setText("");
		ta.setText("");
		
	}
	
}
