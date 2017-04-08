package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecBeatCustomer;
import com.sales.crm.model.SalesExecutive;

public interface SalesExecDAO {
	
	SalesExecutive get(int salesExecID);
	
	List<SalesExecutive> getSalesExecutives(int resellerID);
	
	void assignBeats(int salesExecID, List<Integer> beatIDs);
	
	void updateAssignedBeats(final int salesExecID, final List<Integer> beatIDs);
	
	List<SalesExecutive> getSalesExecMapsBeatsCustomers(int resellerID);
	
	List<Beat> getAssignedBeats(int salesExecID);
	
	void scheduleVistit(SalesExecBeatCustomer salesExecBeatCustomer);
}
