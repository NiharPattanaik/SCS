package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.SupplierDAO;
import com.sales.crm.model.Supplier;

@Service("supplierService")
public class SupplierService {
	
	@Autowired
	private SupplierDAO supplierDAO;
	
	public Supplier getSupplier(int supplierID){
		return supplierDAO.get(supplierID);
	}
	
	public void createSupplier(Supplier supplier){
		supplierDAO.create(supplier);
	}
	
	public void updateSupplier(Supplier supplier){
		supplierDAO.update(supplier);
	}
	
	public void deleteSupplier(int supplierID){
		supplierDAO.delete(supplierID);
	}
	
	public List<Supplier> getResellerSuppliers(int resellerID){
		return supplierDAO.getResellerSupplier(resellerID);
	}
	
}
