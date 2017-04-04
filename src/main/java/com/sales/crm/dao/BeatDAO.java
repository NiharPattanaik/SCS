package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;

public interface BeatDAO {
	
	void create(Beat beat);
	
	Beat get(int beatID);
	
	void update(Beat beat);
	
	void delete(int beatID);
	
	List<Beat> getResellerBeats(int resellerID);
	

}
