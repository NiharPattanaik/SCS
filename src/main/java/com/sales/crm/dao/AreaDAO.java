package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Area;

public interface AreaDAO {
	
	void create(Area area) throws Exception;
	
	Area get(int areaID);
	
	void update(Area area) throws Exception;
	
	void delete(int areaID) throws Exception;
	
	List<Area> getResellerAreas(int resellerID);
	
	List<Area> getBeatAreas(int beatID);
	
	public List<Area> getResellerAreasNotMappedToBeat(int resellerID);
	
	public List<Area> getResellerAreasNotMappedToBeatForEdit(int resellerID, int beatID);

}
