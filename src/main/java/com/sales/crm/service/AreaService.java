package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.AreaDAO;
import com.sales.crm.model.Area;

@Service("areaService")
public class AreaService {
	
	@Autowired
	private AreaDAO areaDAO;
	
	public Area getArea(int areaID){
		return areaDAO.get(areaID);
	}
	
	public void createArea(Area area) throws Exception{
		areaDAO.create(area);
	}
	
	public void updateArea(Area area) throws Exception{
		areaDAO.update(area);
	}
	
	public void deleteArea(int areaID) throws Exception{
		areaDAO.delete(areaID);
	}
	
	public List<Area> getResellerAreas(int resellerID){
		return areaDAO.getResellerAreas(resellerID);
	}
	
	public List<Area> getBeatAreas(int beatID){
		return areaDAO.getBeatAreas(beatID);
	}
}
