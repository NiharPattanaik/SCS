package com.sales.crm.model;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class Customer extends BusinessEntity{
	
	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	long customerID;
	
	@Column(name = "RESELLER_ID")
	long resellerID;
	
	@Column(name = "NAME")
	String name;
	
	@Column(name = "DESCRIPTION")
	String description;
	
	@Column(name = "VISIT_DATE")
	Date visitDate;
	
	@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	List<Address> address;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name="SALES_EXEC_CUSTOMER", 
	    joinColumns={@JoinColumn(name="CUSTOMER_ID")},
	    inverseJoinColumns={@JoinColumn(name="SALES_EXEC_ID")})
	SalesExec salesExec;
	//@Transient
	//Long salesExecID;
	
	//@Transient
	//String salesExecName;
	
	public long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
	public long getResellerID() {
		return resellerID;
	}
	public void setResellerID(long resellerID) {
		this.resellerID = resellerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public SalesExec getSalesExec() {
		return salesExec;
	}
	public void setSalesExec(SalesExec salesExec) {
		this.salesExec = salesExec;
	}
	
	
	
	/**
	public Long getSalesExecID() {
		return salesExecID;
	}
	public void setSalesExecID(Long salesExecID) {
		this.salesExecID = salesExecID;
	}
	public String getSalesExecName() {
		return salesExecName;
	}
	public void setSalesExecName(String salesExecName) {
		this.salesExecName = salesExecName;
	}
	
	**/
	

}
