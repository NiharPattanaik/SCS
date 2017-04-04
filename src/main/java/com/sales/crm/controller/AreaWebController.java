package com.sales.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Area;
import com.sales.crm.service.AreaService;

@Controller
@RequestMapping("/areaWeb")
public class AreaWebController {

	@Autowired
	AreaService areaService;
	
	
	@GetMapping(value="/{areaID}")
	public ModelAndView get(@PathVariable int areaID){
		Area area = areaService.getArea(areaID);
		return new ModelAndView("/area_details", "area", area);
		
	}
	
	@RequestMapping(value="/createAreaForm", method = RequestMethod.GET)  
	public ModelAndView createAreaForm(Model model){
		return new ModelAndView("/create_area", "area", new Area());
	}
	
	@RequestMapping(value="/editAreaForm/{areaID}", method = RequestMethod.GET)  
	public ModelAndView editAreaForm(@PathVariable int areaID){
		Area area = areaService.getArea(areaID);
		return new ModelAndView("/edit_area", "area", area);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("area") Area area){
		areaService.createArea(area);
		return list(area.getResellerID());
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("area") Area area){
		areaService.updateArea(area);
		return get(area.getAreaID());
	}
	
	@DeleteMapping(value="/{areaID}")
	public void delete(@PathVariable int areaID){
		areaService.deleteArea(areaID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable int resellerID){
		List<Area> areas = areaService.getResellerAreas(resellerID);
		return new ModelAndView("/area_list","areas", areas);  
	}
	
	
	
}
