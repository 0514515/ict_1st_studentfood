package ui;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import view.StudentView;

public class Main extends JFrame {

	StudentView student;
	
	
	Main(){
		
		student = new StudentView();
		
		
		//붙이기
		JTabbedPane tab = new JTabbedPane();
		tab.addTab("학생관리", student);
        
		
		add(tab);
		
		setTitle("학식");
		setBounds(100,100,1440,960);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	public static void main(String [] args) {
	
		new Main();
		
	}
}
