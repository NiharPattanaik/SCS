package com.sales.crm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

public class DeliveryExecutive extends User {
	
	@Transient
	private List<Beat> beats;
	
	@Transient
	private List<Integer> beatIDLists;
	
	@Transient
	private Date visitDate;
	
	@Transient
	private List<Integer> customerIDs;

	public List<Beat> getBeats() {
		return beats;
	}

	public void setBeats(List<Beat> beats) {
		this.beats = beats;
	}

	public List<Integer> getBeatIDLists() {
		return beatIDLists;
	}

	public void setBeatIDLists(List<Integer> beatIDLists) {
		this.beatIDLists = beatIDLists;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	
	public List<Integer> getCustomerIDs() {
		return customerIDs;
	}

	public void setCustomerIDs(List<Integer> customerIDs) {
		this.customerIDs = customerIDs;
	}

	@Override
	public String toString() {
		return "DeliveryExecutive [beats=" + beats + ", beatIDLists=" + beatIDLists + ", visitDate=" + visitDate
				+ ", customerIDs=" + customerIDs + "]";
	}

	
}
