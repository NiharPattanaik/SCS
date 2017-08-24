package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.TrimmedCustomer;

public interface BeatDAO {
	
	void create(Beat beat) throws Exception;
	
	Beat get(int beatID);
	
	void update(Beat beat) throws Exception;
	
	void delete(int beatID) throws Exception;
	
	List<Beat> getResellerBeats(int resellerID);
	
	void assignBeatsToCustomer(int customerID, List<Integer> beatIDs) throws Exception;
	
	void updateAssignedBeatToCustomers(int customerID, List<Integer> beatIDs) throws Exception;
	
	List<TrimmedCustomer> getBeatCustomers( int beatID);
	
	void deleteAssignedBeatCustomerLink(int customerID) throws Exception;
	
	int getBeatsCount(int resellerID);
	
	List<Beat> getBeatsNotMappedToCustomer(int customerID);
	
	List<Beat> getBeatsNotMappedToSalesExec(int resellerID, int supplierID, int salesExecID);
	
}
