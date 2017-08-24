package com.sales.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.SalesExecDAO;
import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;

@Service("salesExecService")
public class SalesExecService {
	
	@Autowired
	SalesExecDAO salesExecDAO;
	
	public SalesExecutive getSalesExecutive(int salesExecID){
		return salesExecDAO.get(salesExecID);
	}

	public List<SalesExecutive> getSalesExecutives(int resellerID){
		return salesExecDAO.getSalesExecutives(resellerID);
	}
	
	public void assignBeats(int resellerID, int supplierID, int salesExecID, List<Integer> beatIDs) throws Exception{
		salesExecDAO.assignBeats(resellerID, supplierID, salesExecID, beatIDs);
	}

	public void updateAssignedBeats(int resellerID, int supplierID, int salesExecID, List<Integer> beatIDs) throws Exception{
		salesExecDAO.updateAssignedBeats(resellerID, supplierID, salesExecID, beatIDs);
	}
	
	public List<SalesExecutive> getSalesExecMapsBeatsCustomers(int resellerID){
		return salesExecDAO.getSalesExecMapsBeatsCustomers(resellerID);
	}
	
	public List<Beat> getAssignedBeats(int salesExecID){
		return salesExecDAO.getAssignedBeats(salesExecID);
	}
	
	
	
	public List<Beat> getScheduledVisitSalesExecBeats(int salesExecID, Date visitDate){
		return salesExecDAO.getScheduledVisitSalesExecBeats(salesExecID, visitDate);
	}
	
	public List<SalesExecutive> getScheduledVisitSalesExecs(Date visitDate, int resellerID){
		return salesExecDAO.getScheduledVisitSalesExecs(visitDate, resellerID);
	}
	
	public List<TrimmedCustomer> getScheduledVisitBeatCustomers(int salesExecID, Date visitDate, int beatID){
		return salesExecDAO.getScheduledVisitBeatCustomers(salesExecID, visitDate, beatID);
	}
	
	public void deleteBeatAssignment(int supplierID, int salesExecID) throws Exception{
		salesExecDAO.deleteBeatAssignment(supplierID, salesExecID);
	}
	
	public List<SalesExecutive> getSalesExecutivesHavingBeatsAssigned(int resellerID){
		return salesExecDAO.getSalesExecutivesHavingBeatsAssigned(resellerID);
	}
	
	public int getSalesExecutiveCount(int resellerID){
		return salesExecDAO.getSalesExecutiveCount(resellerID);
	}
	
	public List<SalesExecutive> getSalesExecsNotMappedToSupplier(int resellerID, int supplierID){
		return salesExecDAO.getSalesExecsNotMappedToSupplier(resellerID, supplierID);
	}
	
}
