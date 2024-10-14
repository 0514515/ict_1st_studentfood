package view.tablemodel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

public class CategoryTableModel extends AbstractTableModel {
	public ArrayList data = new ArrayList();
	
	String [] columnNames = {"카테고리명"};

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
