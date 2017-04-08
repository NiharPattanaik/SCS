package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.SalesExecDAO;
import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecBeatCustomer;
import com.sales.crm.model.SalesExecutive;

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
	
	public void assignBeats(final int salesExecID, final List<Integer> beatIDs){
		salesExecDAO.assignBeats(salesExecID, beatIDs);
	}

	public void updateAssignedBeats(final int salesExecID, final List<Integer> beatIDs){
		salesExecDAO.updateAssignedBeats(salesExecID, beatIDs);
	}
	
	public List<SalesExecutive> getSalesExecMapsBeatsCustomers(int resellerID){
		return salesExecDAO.getSalesExecMapsBeatsCustomers(resellerID);
	}
	
	public List<Beat> getAssignedBeats(int salesExecID){
		return salesExecDAO.getAssignedBeats(salesExecID);
	}
	
	public void scheduleVistit(SalesExecBeatCustomer salesExecBeatCustomer){
		salesExecDAO.scheduleVistit( salesExecBeatCustomer);
	}
}
