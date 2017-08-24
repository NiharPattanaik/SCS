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
@Table(name = "SUPPLIER")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class Supplier extends BusinessEntity{
	
	private static final long serialVersionUID = 0l;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	int supplierID;
	
	@Column(name = "RESELLER_ID")
	int resellerID;
	
	@Column(name = "NAME")
	String name;
	
	@Column(name = "DESCRIPTION")
	String description;
	
	@OneToMany(orphanRemoval=true, cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	List<Address> address;
	
	@Transient
	private SuppSalesOfficer salesOfficer;
	
	@Transient
	private SuppAreaManager areaManager;
	
	@Transient
	private List<Manufacturer> manufacturers;
	
	@Transient
	private List<Integer> manufacturerIDs;
	
	@Transient
	private List<Beat> beats;
	
	@Transient
	private List<SalesExecutive> salesExecs;
	
	@Transient
	private List<Integer> salesExecsIDs;
	
	public int getSupplierID() {
		return supplierID;
	}
	public void setSupplierID(int supplierID) {
		this.supplierID = supplierID;
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
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	public SuppSalesOfficer getSalesOfficer() {
		return salesOfficer;
	}
	public void setSalesOfficer(SuppSalesOfficer salesOfficer) {
		this.salesOfficer = salesOfficer;
	}
	public SuppAreaManager getAreaManager() {
		return areaManager;
	}
	public void setAreaManager(SuppAreaManager areaManager) {
		this.areaManager = areaManager;
	}
	
	public List<Manufacturer> getManufacturers() {
		return manufacturers;
	}
	public void setManufacturers(List<Manufacturer> manufacturers) {
		this.manufacturers = manufacturers;
	}
	
	public List<Integer> getManufacturerIDs() {
		return manufacturerIDs;
	}
	public void setManufacturerIDs(List<Integer> manufacturerIDs) {
		this.manufacturerIDs = manufacturerIDs;
	}
	
	public List<Beat> getBeats() {
		return beats;
	}
	public void setBeats(List<Beat> beats) {
		this.beats = beats;
	}
	
	public List<SalesExecutive> getSalesExecs() {
		return salesExecs;
	}
	public void setSalesExecs(List<SalesExecutive> salesExecs) {
		this.salesExecs = salesExecs;
	}
	public List<Integer> getSalesExecsIDs() {
		return salesExecsIDs;
	}
	public void setSalesExecsIDs(List<Integer> salesExecsIDs) {
		this.salesExecsIDs = salesExecsIDs;
	}
	@Override
	public String toString() {
		return "Supplier [supplierID=" + supplierID + ", resellerID=" + resellerID + ", name=" + name + ", description="
				+ description + ", address=" + address + ", salesOfficer=" + salesOfficer + ", areaManager="
				+ areaManager + ", manufacturers=" + manufacturers + ", manufacturerIDs=" + manufacturerIDs + ", beats="
				+ beats + ", salesExecs=" + salesExecs + "]";
	}
	
	
}
