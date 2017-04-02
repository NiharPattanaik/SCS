package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Beat;

public interface BeatDAO {
	
	void create(Beat beat);
	
	Beat get(long beatID);
	
	void update(Beat beat);
	
	void delete(long beatID);
	
	List<Beat> getResellerBeats(long resellerID);
	

}
