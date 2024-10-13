package view.custom;

import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CategoryItemLabelGenerator;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.TextAnchor;

import model.ChartDao;
import model.impl.ChartDaoImpl;


public class OrderChart {

	public JFreeChart getChart(String theme, String keyword) throws SQLException{
		// 데이터 생성
        DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 
 
        //------------------------------------------------------------------
        // 데이터 입력 ( 값, 범례, 카테고리 )
        ChartDao chartDao = new ChartDaoImpl();
        ArrayList<ArrayList> data = chartDao.getSumCountData(theme, keyword);
        for(ArrayList temp : data) {
        	int value = (Integer) temp.get(1);
        	String cate = (String) temp.get(0);
        	dataset.addValue(value, theme+"별",  cate);
        }
        //------------------------------------------------------------------
        
        // 렌더링 생성 및 세팅
        // 렌더링 생성
        final BarRenderer renderer = new BarRenderer();
     
        // 공통 옵션 정의
        final CategoryItemLabelGenerator generator = new StandardCategoryItemLabelGenerator();
        final ItemLabelPosition p_center = new ItemLabelPosition(
                ItemLabelAnchor.CENTER, TextAnchor.CENTER
            );
        final ItemLabelPosition p_below = new ItemLabelPosition(
                     ItemLabelAnchor.OUTSIDE6, TextAnchor.TOP_LEFT
                     );
        Font f = new Font("Gulim", Font.BOLD, 14);
        Font axisF = new Font("Gulim", Font.PLAIN, 14);
       
        // 렌더링 세팅
        // 그래프 1
        renderer.setBaseItemLabelGenerator(generator);
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(p_center);
        renderer.setBaseItemLabelFont(f);
        renderer.setSeriesPaint(0, new Color(0,162,255));
 
        // plot 생성
        final CategoryPlot plot = new CategoryPlot();
       
        // plot 에 데이터 적재
        plot.setDataset(dataset);
        plot.setRenderer(renderer);
 
        // plot 기본 설정
        plot.setOrientation(PlotOrientation.VERTICAL);       // 그래프 표시 방향
        plot.setRangeGridlinesVisible(true);                         // X축 가이드 라인 표시여부
        plot.setDomainGridlinesVisible(true);                      // Y축 가이드 라인 표시여부
 
        // 렌더링 순서 정의 : dataset 등록 순서대로 렌더링 ( 즉, 먼저 등록한게 아래로 깔림 )
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
       
        // X축 세팅
        plot.setDomainAxis(new CategoryAxis());           // X축 종류 설정
        plot.getDomainAxis().setTickLabelFont(axisF); // X축 눈금라벨 폰트 조정
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.STANDARD);       // 카테고리 라벨 위치 조정
 
        // Y축 세팅
        plot.setRangeAxis(new NumberAxis());              // Y축 종류 설정
        plot.getRangeAxis().setTickLabelFont(axisF);  // Y축 눈금라벨 폰트 조정
       
        // 세팅된 plot을 바탕으로 chart 생성
        final JFreeChart chart = new JFreeChart(plot);
        chart.setTitle(" 주문 통계 "); 
        TextTitle copyright = new TextTitle(theme+"별 "+ keyword +"합계 현황 ", new Font("SansSerif", Font.PLAIN, 20));
        copyright.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(copyright);  
        return chart;
	}
}
