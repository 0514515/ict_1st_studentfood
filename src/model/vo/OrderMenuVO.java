package model.vo;

public class OrderMenuVO {
	private Long orderMenuId;
	private Long orderId;
	private Long foodId;
	private Integer count;
	public Long getOrderMenuId() {
		return orderMenuId;
	}
	public void setOrderMenuId(Long orderMenuId) {
		this.orderMenuId = orderMenuId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getFoodId() {
		return foodId;
	}
	public void setFoodId(Long foodId) {
		this.foodId = foodId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
}
