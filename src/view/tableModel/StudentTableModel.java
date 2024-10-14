package view.tablemodel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class StudentTableModel extends AbstractTableModel{
	
	public ArrayList data = new ArrayList();
	String [] columnNames = {"학번", "이름", "학과", "생년월일", "주소", "성별"};

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
	    
	    public void removeAll(){
	    	data = new ArrayList();
	    	fireTableDataChanged();
	    }

}
