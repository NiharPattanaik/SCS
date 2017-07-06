package com.sales.crm.model;

public enum OrderStatusEnum {
	
	ORDER_BOOKING_SCHEDULED (1),
	ORDER_CREATED(2),
	DELIVERY_SCHEDULED(3),
	DELIVERY_COMPLETED(4),
	PARTIALLY_DELIVERED(5),
	PAYMENT_SCHEDULED(6),
	PAYMENT_COMPLETED(7),
	PARTIALLY_PAID(8),
	OTP_VERIFIED(9);
	
	
	
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
