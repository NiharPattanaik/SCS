package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.SupplierDAO;
import com.sales.crm.model.SuppSalesExecBeats;
import com.sales.crm.model.Supplier;

@Service("supplierService")
public class SupplierService {
	
	@Autowired
	private SupplierDAO supplierDAO;
	
	public Supplier getSupplier(int supplierID){
		return supplierDAO.get(supplierID);
	}
	
	public void createSupplier(Supplier supplier) throws Exception{
		supplierDAO.create(supplier);
	}
	
	public void updateSupplier(Supplier supplier) throws Exception{
		supplierDAO.update(supplier);
	}
	
	public void deleteSupplier(int supplierID) throws Exception{
		supplierDAO.delete(supplierID);
	}
	
	public List<Supplier> getResellerSuppliers(int resellerID){
		return supplierDAO.getResellerSupplier(resellerID);
	}
	
	public int getSuppliersCount(int resellerID){
		return supplierDAO.getSuppliersCount(resellerID);
	}
	
	public List<Supplier> getSuppManufacturerList(int resellerID){
		return supplierDAO.getSuppManufacturerList(resellerID);
	}
	
	public void assignManufacturer(int supplierID, List<Integer> manufacturerIDs) throws Exception{
		supplierDAO.assignManufacturer(supplierID, manufacturerIDs);
	}
	
	public void updateAssignedManufacturer(int supplierID, List<Integer> manufacturerIDs) throws Exception{
		supplierDAO.updateAssignedManufacturer(supplierID, manufacturerIDs);
	}
	
	public void deleteAassignedManufacturer(int supplierID) throws Exception{
		supplierDAO.deleteAassignedManufacturer(supplierID);
	}
	
	public List<Supplier> getSuppSalesExecsList(int resellerID){
		return supplierDAO.getSuppSalesExecsList(resellerID);
	}
	
	public void assignSalesExecutivesToSupplier(int resellerID, Supplier supplier) throws Exception{
		supplierDAO.assignSalesExecutivesToSupplier(resellerID, supplier);
	}
	
	public void updateAssignedSalesExecs(int supplierID, List<Integer> salesExecIDs, int resellerID) throws Exception{
		supplierDAO.updateAssignedSalesExecs(supplierID, salesExecIDs, resellerID);
	}
	
	public void deleteAassignedSalesExec(int supplierID) throws Exception{
		supplierDAO.deleteAassignedSalesExec(supplierID);
	}
	
	public List<SuppSalesExecBeats> getSuppSalesExecBeats(int resellerID){
		return supplierDAO.getSuppSalesExecBeats(resellerID);
	}
	
	public SuppSalesExecBeats getSuppSalesExecBeat(int resellerID, int suppID, int salesExecID){
		return supplierDAO.getSuppSalesExecBeat(resellerID, suppID, salesExecID);
	}
	
}
