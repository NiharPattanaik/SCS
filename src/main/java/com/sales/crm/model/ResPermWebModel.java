package com.sales.crm.model;

import java.util.List;

public class ResPermWebModel {
	
	private List<Integer> resourcePermIDList;
	
	private int resellerID;
	
	private int roleID;

	public List<Integer> getResourcePermIDList() {
		return resourcePermIDList;
	}

	public void setResourcePermIDList(List<Integer> resourcePermIDList) {
		this.resourcePermIDList = resourcePermIDList;
	}

	public int getResellerID() {
		return resellerID;
	}

	public void setResellerID(int resellerID) {
		this.resellerID = resellerID;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}
	
	
}
