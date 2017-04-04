package com.sales.crm.dao;

import com.sales.crm.model.Reseller;

public interface ResellerDAO {
	
	void create(Reseller reseller);
	
	Reseller get(int resellerID);
	
	void update(Reseller reseller);
	
	void delete(int resellerID);

}
