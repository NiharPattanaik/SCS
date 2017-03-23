package com.sales.crm.dao;

import com.sales.crm.model.Reseller;

public interface ResellerDAO {
	
	void create(Reseller reseller);
	
	Reseller get(long resellerID);
	
	void update(Reseller reseller);
	
	void delete(long resellerID);

}
