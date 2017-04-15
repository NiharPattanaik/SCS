package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Supplier;

public interface SupplierDAO {
	
	void create(Supplier supplier) throws Exception;
	
	Supplier get(int supplierID);
	
	void update(Supplier supplier) throws Exception;
	
	void delete(int supplierID);
	
	List<Supplier> getResellerSupplier(int resellerID);
	
}
