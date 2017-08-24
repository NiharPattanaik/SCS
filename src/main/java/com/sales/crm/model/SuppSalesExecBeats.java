package com.sales.crm.model;

import java.util.List;

public class SuppSalesExecBeats {
	
	private Supplier supplier;
	
	private SalesExecutive salesExecutive;
	
	private List<Beat> beats;
	
	private List<Integer> beatIDLists;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public SalesExecutive getSalesExecutive() {
		return salesExecutive;
	}

	public void setSalesExecutive(SalesExecutive salesExecutive) {
		this.salesExecutive = salesExecutive;
	}

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

	@Override
	public String toString() {
		return "SuppSalesExecBeats [supplier=" + supplier + ", salesExecutive=" + salesExecutive + ", beats=" + beats
				+ ", beatIDLists=" + beatIDLists + "]";
	}
	
	
	
}
