package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Area;

public interface AreaDAO {
	
	void create(Area area);
	
	Area get(int areaID);
	
	void update(Area area);
	
	void delete(int areaID);
	
	List<Area> getResellerAreas(int resellerID);
	
	List<Area> getBeatAreas(int beatID);

}
