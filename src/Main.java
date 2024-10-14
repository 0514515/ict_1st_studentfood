import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import view.ChartView;
import view.FoodManagementView;
import view.KioskView;
import view.StudentView;

public class Main extends JFrame{
	JTabbedPane pane;
	public Main() {
		pane = new JTabbedPane();	//상단 탭을 위한 TabbedPane
		
		//FoodManagement에서 카테고리, 음식 변화 발생 시 refresh를 위한 래퍼런스
		KioskView kioskView = new KioskView();
		
		//탭 추가
		pane.addTab("학생 관리",new StudentView());
		pane.addTab("음식 kiosk",kioskView);
		pane.addTab("음식/카테고리 관리",new FoodManagementView(kioskView));
		pane.addTab("주문 통계", new ChartView());
		
		
		//탭 사이즈와 타이틀 세팅
		setTabLabel(0, "학생 관리");
		setTabLabel(1, "음식 kiosk");
		setTabLabel(2, "음식/카테고리 관리");
		setTabLabel(3, "주문 통계");
		
		add(pane);
		
		setVisible(true);
		setSize(1440,960);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	// 탭의 사이즈와 타이틀을 설정하는 메소드
	private void setTabLabel(int idx, String title) {
		JLabel lab = new JLabel(title);
		lab.setHorizontalAlignment(JLabel.CENTER);
		lab.setPreferredSize(new Dimension(200,30));
		pane.setTabComponentAt(idx, lab);
	}
	
	public static void main(String[] args) {
		new Main();
	}
}