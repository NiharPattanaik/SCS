package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Area;

public interface AreaDAO {
	
	void create(Area area);
	
	Area get(long areaID);
	
	void update(Area area);
	
	void delete(long areaID);
	
	List<Area> getResellerAreas(long resellerID);
	

}
