package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Reseller;

public interface ResellerDAO {
	
	void create(Reseller reseller) throws Exception;
	
	Reseller get(int resellerID);
	
	void update(Reseller reseller);
	
	void delete(int resellerID);
	
	boolean isEmailIDAlreadyUsed(String emailID) throws Exception;
	
	List<Reseller> getResellers() throws Exception;

}
