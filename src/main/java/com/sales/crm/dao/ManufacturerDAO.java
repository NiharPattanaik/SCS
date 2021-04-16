package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.ManufacturerSalesExecBeats;

public interface ManufacturerDAO {
	
	void create(Manufacturer manufacturer) throws Exception;
	
	Manufacturer get(int manufacturerID, int tenantID );
	
	void update(Manufacturer manufacturer) throws Exception;
	
	void delete(int manufacturerID, int tenantID) throws Exception;
	
	List<Manufacturer> getTenantManufacturers(int tenantID);
	
	int getManufacturersCount(int tenantID);
	
	//List<Manufacturer> getSuppManufacturerList(int tenantID);
	
	//void assignManufacturer(int manufacturerID, List<Integer> manufacturerIDs, int tenantID) throws Exception;
	
	//void updateAssignedManufacturer(int manufacturerID, List<Integer> manufacturerIDs, int tenantID) throws Exception;
	
	//void deleteAassignedManufacturer(int manufacturerID, int tenantID) throws Exception;
	
	public List<Manufacturer> getManufacturerSalesExecsList(int tenantID) ;
	
	public void assignSalesExecutivesToManufacturer(int tenantID, Manufacturer manufacturer) throws Exception;

	void updateAssignedSalesExecs(int manufacturerID, List<Integer> salesExecIDs, int tenantID) throws Exception;
	
	void deleteAassignedSalesExec(int manufacturerID, int tenantID) throws Exception;
	
	List<ManufacturerSalesExecBeats> getManufSalesExecBeats(int tenantID);
	
	ManufacturerSalesExecBeats getManufSalesExecBeat(int tenantID, int manufID, int salesExecID);
}
