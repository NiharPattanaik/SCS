package com.sales.crm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

public class SalesExecutive extends User {
	
	@Transient
	private List<Beat> beats;
	
	@Transient
	private List<Integer> beatIDLists;
	
	@Transient
	private Date visitDate;
	
	@Transient
	private List<Integer> customerIDs;
	
	//HACK, used from spring tag (assign_beat.jsp)
	@Transient
	private int supplierID;

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
	
	

	public int getSupplierID() {
		return supplierID;
	}

	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
	}

	@Override
	public String toString() {
		return "SalesExecutive [beats=" + beats + ", beatIDLists=" + beatIDLists + ", visitDate=" + visitDate
				+ ", customerIDs=" + customerIDs + ", supplierID=" + supplierID + ", companyID=" + companyID
				+ ", dateCreated=" + dateCreated + ", dateModified=" + dateModified + "]";
	}

	
	
	
}
