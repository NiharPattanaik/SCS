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
	
	public void assignBeatToCustomers(int beatID, List<Integer> customerIDs) throws Exception{
		beatDAO.assignBeatToCustomers(beatID, customerIDs);	
	}
	
	public void updateAssignedBeatToCustomers(int beatID, List<Integer> customerIDs){
		beatDAO.updateAssignedBeatToCustomers(beatID, customerIDs);
	}
	
	public List<TrimmedCustomer> getBeatCustomers(int beatID){
		return beatDAO.getBeatCustomers( beatID);
	}
	
	public void deleteAssignedBeatCustomerLink(int beatID) throws Exception{
		beatDAO.deleteAssignedBeatCustomerLink(beatID);
	}
}
