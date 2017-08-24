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
	
	public Beat getBeat(int beatID){
		return beatDAO.get(beatID);
	}
	
	public void createBeat(Beat beat) throws Exception{
		beatDAO.create(beat);
	}
	
	public void updateBeat(Beat beat) throws Exception{
		beatDAO.update(beat);
	}
	
	public void deleteBeat(int beatID) throws Exception{
		beatDAO.delete(beatID);
	}
	
	public List<Beat> getResellerBeats(int resellerID){
		return beatDAO.getResellerBeats(resellerID);
	}
	
	public void assignBeatsToCustomer(int customerID, List<Integer> beatIDs) throws Exception{
		beatDAO.assignBeatsToCustomer(customerID, beatIDs);	
	}
	
	public void updateAssignedBeatToCustomers(int customerID, List<Integer> beatIDs) throws Exception{
		beatDAO.updateAssignedBeatToCustomers(customerID, beatIDs);
	}
	
	public List<TrimmedCustomer> getBeatCustomers(int beatID){
		return beatDAO.getBeatCustomers( beatID);
	}
	
	public void deleteAssignedBeatCustomerLink(int customerID) throws Exception{
		beatDAO.deleteAssignedBeatCustomerLink(customerID);
	}
	
	public int getBeatsCount(int resellerID){
		return beatDAO.getBeatsCount(resellerID);
	}
	
	public List<Beat> getBeatsNotMappedToCustomer(int customerID){
		return beatDAO.getBeatsNotMappedToCustomer(customerID);
	}
	
	public List<Beat> getBeatsNotMappedToSalesExec(int resellerID, int supplierID, int salesExecID){
		return beatDAO.getBeatsNotMappedToSalesExec(resellerID, supplierID, salesExecID);
	}
}
