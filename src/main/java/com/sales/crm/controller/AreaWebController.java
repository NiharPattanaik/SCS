package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
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
@RequestMapping("/web/areaWeb")
public class AreaWebController {

	@Autowired
	AreaService areaService;
	
	@Autowired
	HttpSession httpSession;
	
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
		area.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		String msg = "";
		try{
			areaService.createArea(area);
		}catch(Exception exception){
			msg = "New Area could not be created successfully, please contact System Administrator. ";
		}
		return new ModelAndView("/create_area_conf", "msg", msg);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("area") Area area){
		String msg = "";
		try{
			areaService.updateArea(area);
		}catch(Exception exception){
			msg = "Area details could not be updated successfully, please contact System Administrator. ";
		}
		Map<String, String> modelMap = new HashMap<String, String>();
		modelMap.put("msg", msg);
		modelMap.put("areaID", String.valueOf(area.getAreaID()));
		return new ModelAndView("/edit_area_conf", "map", modelMap);
	}
	
	@GetMapping(value="/delete/{areaID}")
	public ModelAndView delete(@PathVariable int areaID){
		areaService.deleteArea(areaID);
		List<Area> areas = areaService.getResellerAreas(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/area_list","areas", areas);  
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable int resellerID){
		List<Area> areas = areaService.getResellerAreas(resellerID);
		return new ModelAndView("/area_list","areas", areas);  
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
