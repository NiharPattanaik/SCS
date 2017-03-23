package com.sales.crm.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Employee extends BusinessEntity {

	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int empID;
	
	@Column(name = "EMP_TYPE")
	private int type;
	
	@Column(name = "CUSTOMER_ID")
	private int customerID;
	
	@Column(name = "RESELLER_ID")
	private int resellerID;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "employeeID", cascade = CascadeType.PERSIST)
	private List<Address> addresses;
	
	
	public int getEmpID() {
		return empID;
	}
	public void setEmpID(int empID) {
		this.empID = empID;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getResellerID() {
		return resellerID;
	}
	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	
}
