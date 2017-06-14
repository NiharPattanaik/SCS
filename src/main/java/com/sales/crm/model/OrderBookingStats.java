package com.sales.crm.model;

import java.util.List;

public class OrderBookingStats extends BusinessEntity{
	
	private int totalNoOfVisits;
	
	private int noOfVisitsCompleted;
	
	private int noOfVisitsPending;
	
	private List<String> allCustomersForVisit;
	
	private List<String> completedCustomers;
	
	private List<String> pendingCustomers;

	public int getTotalNoOfVisits() {
		return totalNoOfVisits;
	}

	public void setTotalNoOfVisits(int totalNoOfVisits) {
		this.totalNoOfVisits = totalNoOfVisits;
	}

	public int getNoOfVisitsCompleted() {
		return noOfVisitsCompleted;
	}

	public void setNoOfVisitsCompleted(int noOfVisitsCompleted) {
		this.noOfVisitsCompleted = noOfVisitsCompleted;
	}

	public int getNoOfVisitsPending() {
		return noOfVisitsPending;
	}

	public void setNoOfVisitsPending(int noOfVisitsPending) {
		this.noOfVisitsPending = noOfVisitsPending;
	}

	public List<String> getAllCustomersForVisit() {
		return allCustomersForVisit;
	}

	public void setAllCustomersForVisit(List<String> allCustomersForVisit) {
		this.allCustomersForVisit = allCustomersForVisit;
	}

	public List<String> getCompletedCustomers() {
		return completedCustomers;
	}

	public void setCompletedCustomers(List<String> completedCustomers) {
		this.completedCustomers = completedCustomers;
	}

	public List<String> getPendingCustomers() {
		return pendingCustomers;
	}

	public void setPendingCustomers(List<String> pendingCustomers) {
		this.pendingCustomers = pendingCustomers;
	}

	
	

}
