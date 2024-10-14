package view.tablemodel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.vo.FoodVO;

public class FoodTableModel extends AbstractTableModel {
public ArrayList data = new ArrayList();
	
	String [] columnNames = {"음식 이름", "가격"," 판매 상태"};

	    public int getColumnCount() { 
	        return columnNames.length; 
	    } 
	     
	    public int getRowCount() { 
	        return data.size(); 
	    } 

	    public Object getValueAt(int row, int col) { 
			ArrayList temp = (ArrayList)data.get( row );
	        return temp.get( col ); 
	    }
	    
	    public String getColumnName(int col){
	    	return columnNames[col];
	    }
	   
}
