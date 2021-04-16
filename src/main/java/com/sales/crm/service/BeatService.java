package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.BeatDAO;
import com.sales.crm.model.Beat;
import com.sales.crm.model.TrimmedCustomer;

@Service("beatService")
public class BeatService {
	
	@Autowired
	private BeatDAO beatDAO;
	
	public Beat getBeat(int beatID, int tenantID){
		return beatDAO.get(beatID, tenantID);
	}
	
	public void createBeat(Beat beat) throws Exception{
		beatDAO.create(beat);
	}
	
	public void updateBeat(Beat beat) throws Exception{
		beatDAO.update(beat);
	}
	
	public void deleteBeat(int beatID, int tenantID) throws Exception{
		beatDAO.delete(beatID, tenantID);
	}
	
	public List<Beat> getTenantBeats(int tenantID){
		return beatDAO.getTenantBeats(tenantID);
	}
	
	public void assignBeatsToCustomer(int customerID, List<Integer> beatIDs, int tenantID) throws Exception{
		beatDAO.assignBeatsToCustomer(customerID, beatIDs, tenantID);	
	}
	
	public void updateAssignedBeatToCustomers(int customerID, List<Integer> beatIDs, int tenantID) throws Exception{
		beatDAO.updateAssignedBeatToCustomers(customerID, beatIDs, tenantID);
	}
	
	public List<TrimmedCustomer> getBeatCustomers(int beatID, int tenantID){
		return beatDAO.getBeatCustomers( beatID, tenantID);
	}
	
	public void deleteAssignedBeatCustomerLink(int customerID, int tenantID) throws Exception{
		beatDAO.deleteAssignedBeatCustomerLink(customerID, tenantID);
	}
	
	public int getBeatsCount(int tenantID){
		return beatDAO.getBeatsCount(tenantID);
	}
	
	public List<Beat> getBeatsNotMappedToCustomer(int customerID, int tenantID){
		return beatDAO.getBeatsNotMappedToCustomer(customerID, tenantID);
	}
	
	public List<Beat> getBeatsNotMappedToSalesExec(int tenantID, int manufacturerID, int salesExecID){
		return beatDAO.getBeatsNotMappedToSalesExec(tenantID, manufacturerID, salesExecID);
	}
}
