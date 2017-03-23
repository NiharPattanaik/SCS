package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.SalesExec;

public interface SalesExecDAO {
	
	void create(SalesExec salesExec);
	
	SalesExec get(long salesExecID);
	
	void update(SalesExec salesExec);
	
	void delete(long salesExecID);
	
	List<SalesExec> getResellerSalesExecs(long resellerID);

}
