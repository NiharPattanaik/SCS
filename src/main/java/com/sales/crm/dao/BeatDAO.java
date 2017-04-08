package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;
import com.sales.crm.model.Customer;
import com.sales.crm.model.TrimmedCustomer;

public interface BeatDAO {
	
	void create(Beat beat);
	
	Beat get(int beatID);
	
	void update(Beat beat);
	
	void delete(int beatID);
	
	List<Beat> getResellerBeats(int resellerID);
	
	
	void assignBeatToCustomers(int beatID, List<Integer> customerIDs);
	
	void updateAssignedBeatToCustomers(final int beatID, final List<Integer> customerIDs);
	
	List<TrimmedCustomer> getBeatCustomers( int beatID);
	
}
