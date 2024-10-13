import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import view.ChartView;
import view.FoodManagementView;
import view.KioskView;

public class Main extends JFrame{
	JTabbedPane pane;
	public Main() {
		pane = new JTabbedPane();	//상단 탭을 위한 TabbedPane
		
		//탭 추가
		pane.addTab("음식 kiosk",new KioskView());
		pane.addTab("음식 관리",new FoodManagementView());
		pane.addTab("주문 통계", new ChartView());
		
		
		//탭에 컴포넌트 주입
		setTabLabel(0, "음식 kiosk");
		setTabLabel(1, "음식/카테고리 관리");
		setTabLabel(2, "주문 통계");
		
		add(pane);
		
		setVisible(true);
		setSize(1440,960);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
	}
	
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