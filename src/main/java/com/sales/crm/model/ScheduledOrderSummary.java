package com.sales.crm.model;

import java.util.Date;

public class ScheduledOrderSummary {

	private int scheduleID;
	
	private Date visitDate;
	
	private String visitDateStr;
	
	private int salesExecID;
	
	private String salesExecName;
	
	private int numberOfOrders;
	
	private int numberOfLines;
	
	private double totalBookValue;
	
	private int numberOfSchedules;
	
	private int numberOfOrdersPending;

	public int getScheduleID() {
		return scheduleID;
	}

	public void setScheduleID(int scheduleID) {
		this.scheduleID = scheduleID;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getVisitDateStr() {
		return visitDateStr;
	}

	public void setVisitDateStr(String visitDateStr) {
		this.visitDateStr = visitDateStr;
	}

	public int getSalesExecID() {
		return salesExecID;
	}

	public void setSalesExecID(int salesExecID) {
		this.salesExecID = salesExecID;
	}

	public String getSalesExecName() {
		return salesExecName;
	}

	public void setSalesExecName(String salesExecName) {
		this.salesExecName = salesExecName;
	}

	public int getNumberOfOrders() {
		return numberOfOrders;
	}

	public void setNumberOfOrders(int numberOfOrders) {
		this.numberOfOrders = numberOfOrders;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public void setNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
	}

	public double getTotalBookValue() {
		return totalBookValue;
	}

	public void setTotalBookValue(double totalBookValue) {
		this.totalBookValue = totalBookValue;
	}

	public int getNumberOfSchedules() {
		return numberOfSchedules;
	}

	public void setNumberOfSchedules(int numberOfSchedules) {
		this.numberOfSchedules = numberOfSchedules;
	}

	public int getNumberOfOrdersPending() {
		return numberOfOrdersPending;
	}

	public void setNumberOfOrdersPending(int numberOfOrdersPending) {
		this.numberOfOrdersPending = numberOfOrdersPending;
	}

	@Override
	public String toString() {
		return "ScheduledOrderSummary [scheduleID=" + scheduleID + ", visitDate=" + visitDate + ", visitDateStr="
				+ visitDateStr + ", salesExecID=" + salesExecID + ", salesExecName=" + salesExecName
				+ ", numberOfOrders=" + numberOfOrders + ", numberOfLines=" + numberOfLines + ", totalBookValue="
				+ totalBookValue + ", numberOfSchedules=" + numberOfSchedules + ", numberOfOrdersPending="
				+ numberOfOrdersPending + "]";
	}
	
	
	
}
