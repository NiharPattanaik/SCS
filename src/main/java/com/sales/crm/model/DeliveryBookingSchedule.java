package com.sales.crm.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class DeliveryBookingSchedule {
	
	private int ID;
	
	private int delivExecutiveID;
	
	private int beatID;
	
	private List<Integer> customerIDs;
	
	private Date visitDate;
	
	private List<Integer> orderBookingIDs;
	
	private Map<Integer, List<Integer>> customerOrderMap;
	
	private int tenantID;
	
	private List<Integer> orderIDs;
	
	
	
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

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

	public Map<Integer, List<Integer>> getCustomerOrderMap() {
		return customerOrderMap;
	}

	public void setCustomerOrderMap(Map<Integer, List<Integer>> customerOrderMap) {
		this.customerOrderMap = customerOrderMap;
	}

	public int getTenantID() {
		return tenantID;
	}

	public void setTenantID(int tenantID) {
		this.tenantID = tenantID;
	}
	
	public List<Integer> getOrderIDs() {
		return orderIDs;
	}

	public void setOrderIDs(List<Integer> orderIDs) {
		this.orderIDs = orderIDs;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + beatID;
		result = prime * result + ((customerIDs == null) ? 0 : customerIDs.hashCode());
		result = prime * result + ((customerOrderMap == null) ? 0 : customerOrderMap.hashCode());
		result = prime * result + delivExecutiveID;
		result = prime * result + ((orderBookingIDs == null) ? 0 : orderBookingIDs.hashCode());
		result = prime * result + tenantID;
		result = prime * result + ((visitDate == null) ? 0 : visitDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeliveryBookingSchedule other = (DeliveryBookingSchedule) obj;
		if (beatID != other.beatID)
			return false;
		if (customerIDs == null) {
			if (other.customerIDs != null)
				return false;
		} else if (!customerIDs.equals(other.customerIDs))
			return false;
		if (customerOrderMap == null) {
			if (other.customerOrderMap != null)
				return false;
		} else if (!customerOrderMap.equals(other.customerOrderMap))
			return false;
		if (delivExecutiveID != other.delivExecutiveID)
			return false;
		if (orderBookingIDs == null) {
			if (other.orderBookingIDs != null)
				return false;
		} else if (!orderBookingIDs.equals(other.orderBookingIDs))
			return false;
		if (tenantID != other.tenantID)
			return false;
		if (visitDate == null) {
			if (other.visitDate != null)
				return false;
		} else if (!visitDate.equals(other.visitDate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DeliveryBookingSchedule [delivExecutiveID=" + delivExecutiveID + ", beatID=" + beatID + ", customerIDs="
				+ customerIDs + ", visitDate=" + visitDate + ", orderBookingIDs=" + orderBookingIDs
				+ ", customerOrderMap=" + customerOrderMap + ", tenantID=" + tenantID + "]";
	}

	
}
