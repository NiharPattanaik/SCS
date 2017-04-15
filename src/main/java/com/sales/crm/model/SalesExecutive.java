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
	
	
}
