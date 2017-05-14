package com.sales.crm.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class BusinessEntity implements Serializable{
	
	private static final long serialVersionUID = 0l;
	
	public static final int STATUS_INACTIVE = 0;
	public static final int STATUS_SELF_REGISTERED = 1;
	public static final int STATUS_ACTIVE = 2;
	public static final int STATUS_CANCELLED = 3;
	
	protected int companyID;
	
	protected Date dateCreated;
	
	protected Date dateModified;
	
	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	@Override
	public String toString() {
		return "BusinessEntity [companyID=" + companyID + ", dateCreated=" + dateCreated + ", dateModified="
				+ dateModified + "]";
	}

	
		
}
