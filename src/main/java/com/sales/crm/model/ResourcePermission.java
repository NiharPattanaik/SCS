package com.sales.crm.model;

import java.util.Date;

public class ResourcePermission {
	
	private int id;
	
	private Resource resource;
	
	private Permission permission;
	
	private int ResellerID;
	
	private int roleID;
	
	private Date dateCreated;
	
	private Date dateModified;
	
	private int companyID;
	
	private boolean isPresent;
	
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public int getResellerID() {
		return ResellerID;
	}

	public void setResellerID(int resellerID) {
		ResellerID = resellerID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public int getCompanyID() {
		return companyID;
	}

	public void setCompanyID(int companyID) {
		this.companyID = companyID;
	}

	public boolean isPresent() {
		return isPresent;
	}

	public void setPresent(boolean isPresent) {
		this.isPresent = isPresent;
	}

	@Override
	public String toString() {
		return "ResourcePermission [id=" + id + ", resource=" + resource + ", permission=" + permission
				+ ", ResellerID=" + ResellerID + ", roleID=" + roleID + ", dateCreated=" + dateCreated
				+ ", dateModified=" + dateModified + ", companyID=" + companyID + ", isPresent=" + isPresent + "]";
	}

		
}
