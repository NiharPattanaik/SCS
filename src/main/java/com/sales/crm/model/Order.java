package com.sales.crm.model;

import java.text.SimpleDateFormat;

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
@Table(name = "ORDER_DETAILS")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class Order extends BusinessEntity{
	
	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int orderID;
	
	@Column(name = "ORDER_BOOKING_ID")
	private int orderBookingID;
	
	@Column(name = "NO_OF_LINE_ITEMS")
	private int noOfLineItems;
	
	@Column(name = "BOOK_VALUE")
	private double bookValue;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "STATUS")
	private int status;
	
	@Column(name = "RESELLER_ID")
	private int resellerID;
	
	@Column(name = "CUSTOMER_ID")
	private int customerID;
	
	@Transient
	private String dateCreatedString;
	
	@Transient
	private String customerName;
	
	@Transient
	private String statusAsString;

	public String getDateCreatedString() {
		return new SimpleDateFormat("dd-MM-yyyy").format(dateCreated);
	}

	public void setDateCreatedString(String dateCreatedString) {
		this.dateCreatedString = dateCreatedString;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public int getOrderBookingID() {
		return orderBookingID;
	}

	public void setOrderBookingID(int orderBookingID) {
		this.orderBookingID = orderBookingID;
	}

	public int getNoOfLineItems() {
		return noOfLineItems;
	}

	public void setNoOfLineItems(int noOfLineItems) {
		this.noOfLineItems = noOfLineItems;
	}

	public double getBookValue() {
		return bookValue;
	}

	public void setBookValue(double bookValue) {
		this.bookValue = bookValue;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getResellerID() {
		return resellerID;
	}

	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public String getStatusAsString() {
		return statusAsString;
	}

	public void setStatusAsString(String statusAsString) {
		this.statusAsString = statusAsString;
	}

	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", orderBookingID=" + orderBookingID + ", noOfLineItems=" + noOfLineItems
				+ ", bookValue=" + bookValue + ", remark=" + remark + ", status=" + status + ", resellerID="
				+ resellerID + ", dateCreatedString=" + dateCreatedString + ", customerID=" + customerID + "]";
	}

	
}
