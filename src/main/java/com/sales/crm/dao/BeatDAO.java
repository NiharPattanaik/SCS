package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.Customer;
import com.sales.crm.model.TrimmedCustomer;

public interface BeatDAO {
	
	void create(Beat beat) throws Exception;
	
	Beat get(int beatID);
	
	void update(Beat beat) throws Exception;
	
	void delete(int beatID) throws Exception;
	
	List<Beat> getResellerBeats(int resellerID);
	
	
	void assignBeatToCustomers(int beatID, List<Integer> customerIDs) throws Exception;
	
	void updateAssignedBeatToCustomers(final int beatID, final List<Integer> customerIDs);
	
	List<TrimmedCustomer> getBeatCustomers( int beatID);
	
	void deleteAssignedBeatCustomerLink(int beatID) throws Exception;
	
}
