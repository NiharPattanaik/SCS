package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;

public interface SalesExecDAO {
	
	SalesExecutive get(int salesExecID);
	
	List<SalesExecutive> getSalesExecutives(int resellerID);
	
	void assignBeats(int resellerID, int supplierID, int salesExecID, List<Integer> beatIDs) throws Exception;
	
	void updateAssignedBeats(int resellerID, int supplierID, int salesExecID, List<Integer> beatIDs) throws Exception;
	
	List<SalesExecutive> getSalesExecMapsBeatsCustomers(int resellerID);
	
	List<Beat> getAssignedBeats(int salesExecID);
	
	List<Beat> getScheduledVisitSalesExecBeats(int salesExecID, Date visitDate);
	
	List<SalesExecutive> getScheduledVisitSalesExecs(Date visitDate, int resellerID);
	
	List<TrimmedCustomer> getScheduledVisitBeatCustomers(int salesExecID, Date visitDate, int beatID);
	
	void deleteBeatAssignment(int supplierID, int salesExecID) throws Exception;
	
	List<SalesExecutive> getSalesExecutivesHavingBeatsAssigned(int resellerID);
	
	int getSalesExecutiveCount(int resellerID);
	
	List<SalesExecutive> getSalesExecsNotMappedToSupplier(int resellerID, int supplierID);
}
