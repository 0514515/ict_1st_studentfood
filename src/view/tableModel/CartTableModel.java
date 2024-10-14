package view.tablemodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.vo.FoodVO;

public class CartTableModel extends AbstractTableModel{


    private List<FoodVO> foodList = new ArrayList<>();
    private String[] columnNames = {"음식이름", "합계 가격", "개수"};

    public CartTableModel() {
    }
    
    public Integer getTotalPrice() {
    	int result = 0;
    	
    	for(FoodVO food:foodList) {
    		result += food.getPrice() * food.getCount();
    	}
    	
    	return result;
    }

    public List<FoodVO> getFoodList(){
    	return this.foodList;
    }
    
    public void setFoodList(List<FoodVO> foodList) {
        this.foodList = foodList;
    }

    @Override
    public int getRowCount() {
        return foodList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        FoodVO food = foodList.get(rowIndex);
        switch (columnIndex) {
            case 0: return food.getFoodName();
            case 1: return food.getPrice() * food.getCount();  // 합계 가격 = 1개 가격 * 개수
            case 2: return food.getCount();  // 개수
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // 음식 추가 (중복 체크 후 개수만 증가)
    public void addFood(FoodVO newFood) {
        boolean found = false;

        for (int i = 0; i < foodList.size(); i++) {
            FoodVO food = foodList.get(i);
            if (food.getFoodName().equals(newFood.getFoodName())) {
                // 같은 음식이 이미 있다면 개수만 증가시킴
                food.setCount(food.getCount() + 1);
                fireTableCellUpdated(i, 1); // 합계 가격 열 업데이트
                fireTableCellUpdated(i, 2); // 개수 열 업데이트
                found = true;
                break;
            }
        }

        if (!found) {
            foodList.add(newFood); // 새로운 음식 추가
            fireTableRowsInserted(foodList.size() - 1, foodList.size() - 1);
        }
    }

    // JTable에서 클릭한 행의 번호를 가져와 삭제하는 메소드
    public void removeFoods(int[] selectedRows) {
        Arrays.sort(selectedRows);
        for (int i = selectedRows.length - 1; i >= 0; i--) {
        	FoodVO vo = foodList.get(selectedRows[i]);
        	vo.setCount(1);
            foodList.remove(selectedRows[i]);
            fireTableRowsDeleted(selectedRows[i], selectedRows[i]);
        }
    }
}
