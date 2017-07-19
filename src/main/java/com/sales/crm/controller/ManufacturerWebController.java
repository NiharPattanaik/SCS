package com.sales.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.Supplier;
import com.sales.crm.service.ManufacturerService;
import com.sales.crm.service.SupplierService;

@Controller
@RequestMapping("/web/manufacturerWeb")
public class ManufacturerWebController {
	
	@Autowired
	ManufacturerService manufacturerService;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	SupplierService supplierService;
	
	@GetMapping(value="/{manufacturerID}")
	public ModelAndView get(@PathVariable int manufacturerID){
		Manufacturer manufacturer = null;
		try{
			manufacturer = manufacturerService.getManufacturer(manufacturerID);
		}catch(Exception exception){
			String msg = "Manufacturer details could not be fetched successfully. Please retry after sometime and if error persists contact System Administrator. ";
			return new ModelAndView("/manufacturer_details_error", "msg", msg);
		}
		return new ModelAndView("/manufacturer_details", "manufacturer", manufacturer);
		
	}
	
	@RequestMapping(value="/createManufacturerForm", method = RequestMethod.GET)  
	public ModelAndView createManufacturerForm(Model model){
		return new ModelAndView("/create_manufacturer", "manufacturer", new Manufacturer());
	}
	
	@RequestMapping(value="/editManufacturerForm/{manufacturerID}", method = RequestMethod.GET)  
	public ModelAndView editManufacturerForm(@PathVariable int manufacturerID){
		Manufacturer manufacturer = null;
		try{
			manufacturer = manufacturerService.getManufacturer(manufacturerID);
		}catch(Exception exception){
			String msg = "Manufacturer details could not be fetched successfully. Please retry after sometime and if error persists contact System Administrator. ";
			return new ModelAndView("/manufacturer_details_error", "msg", msg);
		}
		return new ModelAndView("/edit_manufacturer", "manufacturer", manufacturer);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("manufacturer") Manufacturer manufacturer){
		manufacturer.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		String msg = "";
		try{
			manufacturerService.createManufacturer(manufacturer);
		}catch(Exception exception){
			msg = "New Manufacturer could not be created successfully, Please retry after sometime and if error persists contact System Administrator.  ";
		}
		return new ModelAndView("/create_manufacturer_conf", "msg", msg);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("manufacturer") Manufacturer manufacturer){
		String msg = "";
		try{
			manufacturerService.updateManufacturer(manufacturer);
		}catch(Exception exception){
			msg = "Manufacturer details could not be updated successfully, Please retry after sometime and if error persists contact System Administrator.  ";
		}
		Map<String, String> modelMap = new HashMap<String, String>();
		modelMap.put("msg", msg);
		modelMap.put("manufacturerID", String.valueOf(manufacturer.getManufacturerID()));
		return new ModelAndView("/edit_manufacturer_conf", "map", modelMap);
	}
	
	@GetMapping(value="/delete/{manufacturerID}")
	public ModelAndView delete(@PathVariable("manufacturerID") int manufacturerID){
		String msg = "";
		try{
			manufacturerService.deleteManufacturer(manufacturerID);
		}catch(Exception exception){
			msg = "Manufacturer could not be successfully removed, Please retry after sometime and if error persists contact System Administrator. ";
		}
		return new ModelAndView("/delete_manufacturer_conf", "msg", msg);
	}
	
	@GetMapping(value="/list")
	public ModelAndView list(){
		List<Manufacturer> manufacturers = manufacturerService.getResellerManufacturers(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/manufacturer_list","manufacturers", manufacturers);  
	}
	
	

}
