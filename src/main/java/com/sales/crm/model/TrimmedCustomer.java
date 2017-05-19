package com.sales.crm.model;

public class TrimmedCustomer extends BusinessEntity{

	private int customerID;
	private String customerName;
	private int orderBookingID;
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public int getOrderBookingID() {
		return orderBookingID;
	}
	public void setOrderBookingID(int orderBookingID) {
		this.orderBookingID = orderBookingID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerID;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrimmedCustomer other = (TrimmedCustomer) obj;
		if (customerID != other.customerID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "TrimmedCustomer [customerID=" + customerID + ", customerName=" + customerName + "]";
	}
	
	
}
