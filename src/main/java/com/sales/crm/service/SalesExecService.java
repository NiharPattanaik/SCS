package com.sales.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.sales.crm.dao.SalesExecDAO;
import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecBeatCustomer;
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
	
	public void assignBeats(final int salesExecID, final List<Integer> beatIDs) throws Exception{
		salesExecDAO.assignBeats(salesExecID, beatIDs);
	}

	public void updateAssignedBeats(final int salesExecID, final List<Integer> beatIDs) throws Exception{
		salesExecDAO.updateAssignedBeats(salesExecID, beatIDs);
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
	
	public void scheduleVistit(SalesExecBeatCustomer salesExecBeatCustomer){
		if(salesExecBeatCustomer.getVisitDate() == null){
			salesExecBeatCustomer.setVisitDate(new Date());
		}
		salesExecDAO.scheduleVistit( salesExecBeatCustomer);
	}
	
	public List<String> alreadyScheduledCustomer(SalesExecBeatCustomer salesExecBeatCustomer) throws Exception{
		if(salesExecBeatCustomer.getVisitDate() == null){
			salesExecBeatCustomer.setVisitDate(new Date());
		}
		return salesExecDAO.alreadyScheduledCustomer( salesExecBeatCustomer);
	}
	
	public List<SalesExecutive> getScheduledVisitSalesExecs(Date visitDate){
		return salesExecDAO.getScheduledVisitSalesExecs(visitDate);
	}
	
	public List<TrimmedCustomer> getScheduledVisitBeatCustomers(int salesExecID, Date visitDate, int beatID){
		return salesExecDAO.getScheduledVisitBeatCustomers(salesExecID, visitDate, beatID);
	}
	
	public void deleteBeatAssignment(int salesExecID) throws Exception{
		salesExecDAO.deleteBeatAssignment(salesExecID);
	}
	
	public List<SalesExecutive> getSalesExecutivesHavingBeatsAssigned(int resellerID){
		return salesExecDAO.getSalesExecutivesHavingBeatsAssigned(resellerID);
	}
}
