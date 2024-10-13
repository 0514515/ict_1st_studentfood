package view.custom;

import java.awt.Dimension;

import javax.swing.JButton;

import model.vo.FoodVO;

public class FoodButton extends JButton{

	FoodVO food;
	
	public FoodButton(FoodVO food) {
		super(food.getFoodName() + " " + food.getPrice() + "원");
		this.food = food;
	}	
	
}
