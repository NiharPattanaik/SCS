package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.TrimmedCustomer;

public interface BeatDAO {
	
	void create(Beat beat) throws Exception;
	
	Beat get(int beatID, int tenantID);
	
	void update(Beat beat) throws Exception;
	
	void delete(int beatID, int tenantID) throws Exception;
	
	List<Beat> getTenantBeats(int tenantID);
	
	void assignBeatsToCustomer(int customerID, List<Integer> beatIDs, int tenantID) throws Exception;
	
	void updateAssignedBeatToCustomers(int customerID, List<Integer> beatIDs, int tenantID) throws Exception;
	
	List<TrimmedCustomer> getBeatCustomers( int beatID, int tenantID);
	
	void deleteAssignedBeatCustomerLink(int customerID, int tenantID) throws Exception;
	
	int getBeatsCount(int tenantID);
	
	List<Beat> getBeatsNotMappedToCustomer(int customerID, int tenantID);
	
	List<Beat> getBeatsNotMappedToSalesExec(int tenantID, int manufacturerID, int salesExecID);
	
}
