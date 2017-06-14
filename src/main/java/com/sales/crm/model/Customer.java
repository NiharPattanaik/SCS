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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private int customerID;
	
	@Column(name = "RESELLER_ID")
	private int resellerID;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "VISIT_DATE")
	private Date visitDate;
	
	@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Address> address;
	
	@Transient
	private int salesExecID;
	
	@Transient
	private String salesExecName;
	
	@Transient
	private int beatID = -1;
	
	@Transient
	private String beatName;
	
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
	public int getSalesExecID() {
		return salesExecID;
	}
	public void setSalesExecID(int salesExecID) {
		this.salesExecID = salesExecID;
	}
	public String getSalesExecName() {
		return salesExecName;
	}
	public void setSalesExecName(String salesExecName) {
		this.salesExecName = salesExecName;
	}
	public int getBeatID() {
		return beatID;
	}
	public void setBeatID(int beatID) {
		this.beatID = beatID;
	}
	
	public String getBeatName() {
		return beatName;
	}
	public void setBeatName(String beatName) {
		this.beatName = beatName;
	}
	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", resellerID=" + resellerID + ", name=" + name + ", description="
				+ description + ", visitDate=" + visitDate + ", address=" + address + ", salesExecID=" + salesExecID
				+ ", salesExecName=" + salesExecName + ", beatID=" + beatID + "]";
	}
	
	
	
}
