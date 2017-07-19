package com.sales.crm.model;

import java.text.SimpleDateFormat;
import java.util.Date;

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
@Table(name = "SUPPLIER_AREA_MANAGER")
@AttributeOverrides({
	@AttributeOverride(name = "companyID", column = @Column(name = "COMPANY_ID")),
	@AttributeOverride(name = "dateCreated", column = @Column(name = "DATE_CREATED")),
	@AttributeOverride(name = "dateModified", column = @Column(name = "DATE_MODIFIED"))})
public class SuppAreaManager {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private int ID;

	@Column(name = "NAME")
	private String name;
	
	@Column(name = "EFFECTIVE_FROM")
	private Date effectiveFrom;
	
	@Column(name = "CONTACT_NO")
	private String contactNo;
	
	@Column(name = "SUPPLIER_ID")
	private int supplierID;
	
	@Column(name = "RESELLER_ID")
	private int resellerID;
	
	@Transient
	private String effectiveFromStr;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getEffectiveFrom() {
		return effectiveFrom;
	}

	public void setEffectiveFrom(Date effectiveFrom) {
		this.effectiveFrom = effectiveFrom;
	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

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
	
	public String getEffectiveFromStr() {
		if(effectiveFrom != null){
			return new SimpleDateFormat("dd-MM-yyyy").format(effectiveFrom);
		}
		return effectiveFromStr;
	}

	public void setEffectiveFromStr(String effectiveFromStr) {
		this.effectiveFromStr = effectiveFromStr;
	}

	@Override
	public String toString() {
		return "SuppAreaManager [ID=" + ID + ", name=" + name + ", effectiveFrom=" + effectiveFromStr + ", contactNo="
				+ contactNo + ", supplierID=" + supplierID + ", resellerID=" + resellerID + "]";
	}
	
	
}
