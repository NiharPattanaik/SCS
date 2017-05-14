package com.sales.crm.model;

public class TrimmedCustomer extends BusinessEntity{

	int customerID;
	String customerName;
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
	@Override
	public String toString() {
		return "TrimmedCustomer [customerID=" + customerID + ", customerName=" + customerName + "]";
	}
	
	
}
