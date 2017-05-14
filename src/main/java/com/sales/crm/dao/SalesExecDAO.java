package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;

public interface SalesExecDAO {
	
	SalesExecutive get(int salesExecID);
	
	List<SalesExecutive> getSalesExecutives(int resellerID);
	
	void assignBeats(int salesExecID, List<Integer> beatIDs) throws Exception;
	
	void updateAssignedBeats(final int salesExecID, final List<Integer> beatIDs) throws Exception;
	
	List<SalesExecutive> getSalesExecMapsBeatsCustomers(int resellerID);
	
	List<Beat> getAssignedBeats(int salesExecID);
	
	List<Beat> getScheduledVisitSalesExecBeats(int salesExecID, Date visitDate);
	
	List<SalesExecutive> getScheduledVisitSalesExecs(Date visitDate);
	
	List<TrimmedCustomer> getScheduledVisitBeatCustomers(int salesExecID, Date visitDate, int beatID);
	
	void deleteBeatAssignment(int salesExecID) throws Exception;
	
	List<SalesExecutive> getSalesExecutivesHavingBeatsAssigned(int resellerID);
	
}
