package com.sales.crm.model;

import java.util.Date;
import java.util.List;

public class OrderBookingSchedule extends BusinessEntity{
	
	private int bookingScheduleID;
	
	private int salesExecutiveID;
	
	private int beatID;
	
	private List<Integer> customerIDs;
	
	private Date visitDate;
	
	private int resellerID;
	
	private String salesExecName;
	
	private String beatName;
	
	private String customerName;
	
	//looks odd but used for table display
	private int customerID;

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

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public int getResellerID() {
		return resellerID;
	}

	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}

	public String getSalesExecName() {
		return salesExecName;
	}

	public void setSalesExecName(String salesExecName) {
		this.salesExecName = salesExecName;
	}

	public String getBeatName() {
		return beatName;
	}

	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getBookingScheduleID() {
		return bookingScheduleID;
	}

	public void setBookingScheduleID(int bookingScheduleID) {
		this.bookingScheduleID = bookingScheduleID;
	}

	
	
}
