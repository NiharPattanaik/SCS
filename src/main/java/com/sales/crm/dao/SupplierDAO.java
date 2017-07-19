package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Supplier;

public interface SupplierDAO {
	
	void create(Supplier supplier) throws Exception;
	
	Supplier get(int supplierID);
	
	void update(Supplier supplier) throws Exception;
	
	void delete(int supplierID) throws Exception;
	
	List<Supplier> getResellerSupplier(int resellerID);
	
	int getSuppliersCount(int resellerID);
	
	List<Supplier> getSuppManufacturerList(int resellerID);
	
	void assignManufacturer(int supplierID, List<Integer> manufacturerIDs) throws Exception;
	
	void updateAssignedManufacturer(int supplierID, List<Integer> manufacturerIDs) throws Exception;
	
	void deleteAassignedManufacturer(int supplierID) throws Exception;

}
