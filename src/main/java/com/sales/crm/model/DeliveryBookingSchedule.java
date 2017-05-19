package com.sales.crm.model;

import java.util.Date;
import java.util.List;

public class DeliveryBookingSchedule {
	
	private int delivExecutiveID;
	
	private int beatID;
	
	private List<Integer> customerIDs;
	
	private Date visitDate;
	
	private List<Integer> orderBookingIDs;

	public int getDelivExecutiveID() {
		return delivExecutiveID;
	}

	public void setDelivExecutiveID(int delivExecutiveID) {
		this.delivExecutiveID = delivExecutiveID;
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

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public List<Integer> getOrderBookingIDs() {
		return orderBookingIDs;
	}

	public void setOrderBookingIDs(List<Integer> orderBookingIDs) {
		this.orderBookingIDs = orderBookingIDs;
	}

	
}
