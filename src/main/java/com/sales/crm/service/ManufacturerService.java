package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.ManufacturerDAO;
import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.Supplier;

@Service("manufacturerService")
public class ManufacturerService {
	
	@Autowired
	ManufacturerDAO manufacturerDAO;
	
	public Manufacturer getManufacturer(int manufacturerID) throws Exception{
		return manufacturerDAO.getManufacturer(manufacturerID);
	}
	
	public void createManufacturer(Manufacturer manufacturer) throws Exception{
		manufacturerDAO.createManufacturer(manufacturer);
	}
	
	public void updateManufacturer(Manufacturer manufacturer) throws Exception{
		manufacturerDAO.updateManufacturer(manufacturer);
	}
	
	public void deleteManufacturer(int manufacturerID) throws Exception{
		manufacturerDAO.deleteManufacturer(manufacturerID);
	}
	
	public List<Manufacturer> getResellerManufacturers(int resellerID){
		return manufacturerDAO.getResellerManufacturers(resellerID);
	}
	
	
}
