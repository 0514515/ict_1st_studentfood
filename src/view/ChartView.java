package view;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import view.custom.OrderChart;

public class ChartView extends JPanel{
	
	String[] theme = {"음식이름","카테고리"};
	JComboBox<String> themeCb = new JComboBox<>(theme);
	
	String[] keyword = {"판매수량","합계금액"};
	JComboBox<String> keywordCb = new JComboBox<>(keyword);
	
	JPanel panel = new JPanel();
	ChartPanel chartPanel;
	
	public ChartView(){
		setBorder(new TitledBorder("주문 통계"));
		
		
		themeCb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String theme = (String)themeCb.getSelectedItem();
				String keyword = (String)keywordCb.getSelectedItem();
				generateChart(theme, keyword);
			}
		});
		
		keywordCb.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String theme = (String) themeCb.getSelectedItem();
				String keyword = (String) keywordCb.getSelectedItem();
				generateChart(theme, keyword);
			}
		});
		
		generateChart(theme[0], keyword[1]);
		add(panel);
		add(themeCb);
		add(keywordCb);
	}
	
	private void generateChart(String theme, String keyword) {
		OrderChart orderChart = new OrderChart();
		
		try {
			JFreeChart chart = orderChart.getChart(theme, keyword);
			chartPanel = new ChartPanel(chart);
			chartPanel.setPreferredSize(new Dimension(1400,750));
			panel.removeAll();
			panel.add(chartPanel);
			panel.repaint();
			panel.revalidate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
