package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	public void createBeat(Beat beat){
		beatDAO.create(beat);
	}
	
	public void updateBeat(Beat beat){
		beatDAO.update(beat);
	}
	
	public void deleteBeat(int beatID){
		beatDAO.delete(beatID);
	}
	
	public List<Beat> getResellerBeats(int resellerID){
		return beatDAO.getResellerBeats(resellerID);
	}
	
	public void assignBeatToCustomers(int beatID, List<Integer> customerIDs){
		beatDAO.assignBeatToCustomers(beatID, customerIDs);	
	}
	
	public void updateAssignedBeatToCustomers(int beatID, List<Integer> customerIDs){
		beatDAO.updateAssignedBeatToCustomers(beatID, customerIDs);
	}
	
	public List<TrimmedCustomer> getBeatCustomers(int beatID){
		return beatDAO.getBeatCustomers( beatID);
	}
}
