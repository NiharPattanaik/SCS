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
	
	public Area getArea(long areaID){
		return areaDAO.get(areaID);
	}
	
	public void createArea(Area area){
		areaDAO.create(area);
	}
	
	public void updateArea(Area area){
		areaDAO.update(area);
	}
	
	public void deleteArea(long areaID){
		areaDAO.delete(areaID);
	}
	
	public List<Area> getResellerAreas(long resellerID){
		return areaDAO.getResellerAreas(resellerID);
	}
}
