package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.Supplier;

public interface ManufacturerDAO {
	
	Manufacturer getManufacturer(int manufacturerID) throws Exception;
	
	void createManufacturer(Manufacturer manufacturer) throws Exception;
	
	void updateManufacturer(Manufacturer manufacturer) throws Exception;
	
	void deleteManufacturer(int manufacturerID) throws Exception;
	
	List<Manufacturer> getResellerManufacturers(int resellerID);
}
