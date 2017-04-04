package com.sales.crm.model;

import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "SALES_EXEC")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class SalesExec extends BusinessEntity{
	
	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	int salesExecID;
	
	@Column(name = "NAME")
	String name;
	
	@Column(name = "DESCRIPTION")
	String description;
	
	@Column(name = "RESELLER_ID")
	int resellerID;
	
	//@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	//@OneToMany(fetch=FetchType.LAZY)
	//@JoinTable(name="CUSTOMER_SALES_EXEC", 
	//    joinColumns={@JoinColumn(name="SALES_EXEC_ID")},
	//    inverseJoinColumns={@JoinColumn(name="CUSTOMER_ID")}
	//)
	//List<Customer> customers;
	
	public int getSalesExecID() {
		return salesExecID;
	}
	public void setSalesExecID(int salesExecID) {
		this.salesExecID = salesExecID;
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
	public int getResellerID() {
		return resellerID;
	}
	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}
	
}
