package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.SalesExec;

public interface SalesExecDAO {
	
	void create(SalesExec salesExec);
	
	SalesExec get(int salesExecID);
	
	void update(SalesExec salesExec);
	
	void delete(int salesExecID);
	
	List<SalesExec> getResellerSalesExecs(int resellerID);

}
