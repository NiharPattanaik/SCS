package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.ManufacturerDAO;
import com.sales.crm.model.ManufacturerSalesExecBeats;
import com.sales.crm.model.Manufacturer;

@Service("manufacturerService")
public class ManufacturerService {
	
	@Autowired
	private ManufacturerDAO manufacturerDAO;
	
	public Manufacturer getManufacturer(int manufacturerID, int tenantID){
		return manufacturerDAO.get(manufacturerID, tenantID);
	}
	
	public void createManufacturer(Manufacturer manufacturer) throws Exception{
		manufacturerDAO.create(manufacturer);
	}
	
	public void updateManufacturer(Manufacturer manufacturer) throws Exception{
		manufacturerDAO.update(manufacturer);
	}
	
	public void deleteManufacturer(int manufacturerID, int tenantID) throws Exception{
		manufacturerDAO.delete(manufacturerID, tenantID);
	}
	
	public List<Manufacturer> getTenantManufacturers(int tenantID){
		return manufacturerDAO.getTenantManufacturers(tenantID);
	}
	
	public int getManufacturersCount(int tenantID){
		return manufacturerDAO.getManufacturersCount(tenantID);
	}
	
	public List<Manufacturer> getManufacturerSalesExecsList(int tenantID){
		return manufacturerDAO.getManufacturerSalesExecsList(tenantID);
	}
	
	public void assignSalesExecutivesToManufacturer(int tenantID, Manufacturer manufacturer) throws Exception{
		manufacturerDAO.assignSalesExecutivesToManufacturer(tenantID, manufacturer);
	}
	
	public void updateAssignedSalesExecs(int manufacturerID, List<Integer> salesExecIDs, int tenantID) throws Exception{
		manufacturerDAO.updateAssignedSalesExecs(manufacturerID, salesExecIDs, tenantID);
	}
	
	public void deleteAassignedSalesExec(int manufacturerID, int tenantID) throws Exception{
		manufacturerDAO.deleteAassignedSalesExec(manufacturerID, tenantID);
	}
	
	public List<ManufacturerSalesExecBeats> getManufSalesExecBeats(int tenantID){
		return manufacturerDAO.getManufSalesExecBeats(tenantID);
	}
	
	public ManufacturerSalesExecBeats getManufSalesExecBeat(int tenantID, int manufID, int salesExecID){
		return manufacturerDAO.getManufSalesExecBeat(tenantID, manufID, salesExecID);
	}
	
}
