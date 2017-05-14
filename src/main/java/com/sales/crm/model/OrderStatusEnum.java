package com.sales.crm.model;

public enum OrderStatusEnum {
	
	ORDER_BOOKING_SCHEDULED (1),
	ORDER_CONFIRMED (2),
	DELIVERY_BOOKING_SCHEDULED (3),
	DELIVERY_CONFORMED (4),
	PAYMENT_BOOKING_SCHEDULED (5),
	PAYMENT_CONFIRMED(6);
	
	private int orderStatus;
	
	OrderStatusEnum(int orderStatus){
		this.orderStatus = orderStatus;
	}

	public int getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	

}
