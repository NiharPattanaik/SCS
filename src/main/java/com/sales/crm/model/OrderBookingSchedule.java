package com.sales.crm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "ORDER_BOOKING_SCHEDULE")
@AttributeOverrides({
	@AttributeOverride(name = "tenantID", column = @Column(name = "TENANT_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class OrderBookingSchedule extends BusinessEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int bookingScheduleID;
	
	@Column(name = "SALES_EXEC_ID")
	private int salesExecutiveID;
	
	@Column(name = "BEAT_ID")
	private int beatID;
	
	@Column(name = "VISIT_DATE")
	private Date visitDate;
	
	@Transient
	private String salesExecName;
	
	@Transient
	private String beatName;
	
	@Transient
	private String customerName;
	
	@Transient
	private List<Integer> customerIDs;
	
	//looks odd but used for table display
	@Transient
	private int customerID;
	
	@Transient
	private int orderID;
	
	@Transient
	private String statusAsString;
	
	@Transient
	private String visitDateAsString;
	
	@Transient
	private int status;
	
	@Transient
	private int statusID;

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

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getStatusAsString() {
		return statusAsString;
	}

	public void setStatusAsString(String statusAsString) {
		this.statusAsString = statusAsString;
	}

	public String getVisitDateAsString() {
		return visitDateAsString;
	}

	public void setVisitDateAsString(String visitDateAsString) {
		this.visitDateAsString = visitDateAsString;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + beatID;
		result = prime * result + ((beatName == null) ? 0 : beatName.hashCode());
		result = prime * result + bookingScheduleID;
		result = prime * result + customerID;
		result = prime * result + ((customerIDs == null) ? 0 : customerIDs.hashCode());
		result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
		result = prime * result + orderID;
		result = prime * result + ((salesExecName == null) ? 0 : salesExecName.hashCode());
		result = prime * result + salesExecutiveID;
		result = prime * result + status;
		result = prime * result + ((statusAsString == null) ? 0 : statusAsString.hashCode());
		result = prime * result + ((visitDate == null) ? 0 : visitDate.hashCode());
		result = prime * result + ((visitDateAsString == null) ? 0 : visitDateAsString.hashCode());
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
		OrderBookingSchedule other = (OrderBookingSchedule) obj;
		if (beatID != other.beatID)
			return false;
		if (beatName == null) {
			if (other.beatName != null)
				return false;
		} else if (!beatName.equals(other.beatName))
			return false;
		if (bookingScheduleID != other.bookingScheduleID)
			return false;
		if (customerID != other.customerID)
			return false;
		if (customerIDs == null) {
			if (other.customerIDs != null)
				return false;
		} else if (!customerIDs.equals(other.customerIDs))
			return false;
		if (customerName == null) {
			if (other.customerName != null)
				return false;
		} else if (!customerName.equals(other.customerName))
			return false;
		if (orderID != other.orderID)
			return false;
		if (salesExecName == null) {
			if (other.salesExecName != null)
				return false;
		} else if (!salesExecName.equals(other.salesExecName))
			return false;
		if (salesExecutiveID != other.salesExecutiveID)
			return false;
		if (status != other.status)
			return false;
		if (statusAsString == null) {
			if (other.statusAsString != null)
				return false;
		} else if (!statusAsString.equals(other.statusAsString))
			return false;
		if (visitDate == null) {
			if (other.visitDate != null)
				return false;
		} else if (!visitDate.equals(other.visitDate))
			return false;
		if (visitDateAsString == null) {
			if (other.visitDateAsString != null)
				return false;
		} else if (!visitDateAsString.equals(other.visitDateAsString))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OrderBookingSchedule [bookingScheduleID=" + bookingScheduleID + ", salesExecutiveID=" + salesExecutiveID
				+ ", beatID=" + beatID + ", visitDate=" + visitDate + ", salesExecName=" + salesExecName + ", beatName="
				+ beatName + ", customerName=" + customerName + ", customerIDs=" + customerIDs + ", customerID="
				+ customerID + ", orderID=" + orderID + ", statusAsString=" + statusAsString + ", visitDateAsString="
				+ visitDateAsString + ", status=" + status + ", tenantID=" + tenantID + ", dateCreated=" + dateCreated
				+ ", dateModified=" + dateModified + "]";
	}
	
	
		
}
