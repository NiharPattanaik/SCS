package com.sales.crm.model;

import java.util.List;

public class SalesExecBeatCustomer {
	
	private int salesExecutiveID;
	
	private int beatID;
	
	private List<Integer> customerIDs;

	public int getSalesExecutiveID() {
		return salesExecutiveID;
	}

	public void setSalesExecutiveID(int salesExecutiveID) {
		this.salesExecutiveID = salesExecutiveID;
	}

	public int getBeatID() {
		return beatID;
	}

	public void setBeatID(int beatID) {
		this.beatID = beatID;
	}

	public List<Integer> getCustomerIDs() {
		return customerIDs;
	}

	public void setCustomerIDs(List<Integer> customerIDs) {
		this.customerIDs = customerIDs;
	}

	
}
