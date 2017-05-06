package com.sales.crm.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "RESELLER")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})

public class Reseller extends BusinessEntity{
	
	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int resellerID;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER )
	private List<Address> address;
	
	@Column(name="STATUS")
	private int status;
	
	@Transient
	private List<Customer> customers;
	
	@Transient
	private List<Beat> beats;
	
	@Transient
	private List<Area> areas;
	
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
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> addresses) {
		this.address = addresses;
	}
	public List<Customer> getCustomers() {
		return customers;
	}
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public List<Beat> getBeats() {
		return beats;
	}
	public void setBeats(List<Beat> beats) {
		this.beats = beats;
	}
	public List<Area> getAreas() {
		return areas;
	}
	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
