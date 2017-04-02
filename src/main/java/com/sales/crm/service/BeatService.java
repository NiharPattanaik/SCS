package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.BeatDAO;
import com.sales.crm.model.Beat;

@Service("beatService")
public class BeatService {
	
	@Autowired
	private BeatDAO beatDAO;
	
	public Beat getBeat(long beatID){
		return beatDAO.get(beatID);
	}
	
	public void createBeat(Beat beat){
		beatDAO.create(beat);
	}
	
	public void updateBeat(Beat beat){
		beatDAO.update(beat);
	}
	
	public void deleteBeat(long beatID){
		beatDAO.delete(beatID);
	}
	
	public List<Beat> getResellerBeats(long resellerID){
		return beatDAO.getResellerBeats(resellerID);
	}
}
