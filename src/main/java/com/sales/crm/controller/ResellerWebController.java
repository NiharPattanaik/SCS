package com.sales.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Reseller;
import com.sales.crm.service.ResellerService;

@Controller
@RequestMapping("/web/resellerWeb")
public class ResellerWebController {
	
	@Autowired
	ResellerService resellerService;
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("reseller") Reseller reseller){
		resellerService.createReseller(reseller);
		return new ModelAndView("resSuccess");
	}
	
	
	/**
	 * Used without login
	 * 
	 * @param reseller
	 * @return
	 */
	@RequestMapping(value="/saveReseller",method = RequestMethod.POST)  
	public ModelAndView createReseller(@ModelAttribute("reseller") Reseller reseller){
		resellerService.createReseller(reseller);
		return new ModelAndView("/message", "message", "Reseller is created with ID "+ reseller.getResellerID());
	}
	
	@RequestMapping(value="/createResellerForm", method = RequestMethod.GET)  
	public ModelAndView createResellerForm(){
		return new ModelAndView("/create_reseller", "reseller", new Reseller());
	}
	
	@GetMapping(value="/{resellerID}")
	public ModelAndView get(@PathVariable int resellerID){
		Reseller reseller = resellerService.getReseller(resellerID);
		return new ModelAndView("/reseller_details", "reseller", reseller);
		
	}
	
	@RequestMapping(value="/editResellerForm/{resellerID}", method = RequestMethod.GET)  
	public ModelAndView editCustomerForm(@PathVariable int resellerID){
		Reseller reseller = resellerService.getReseller(resellerID);
		return new ModelAndView("/edit_reseller", "reseller", reseller);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("reseller") Reseller reseller){
		resellerService.updateReseller(reseller);
		return get(reseller.getResellerID());
	}	
	public void delete(@PathVariable int resellerID){
		resellerService.deleteReseller(resellerID);
	}
	
	
}
