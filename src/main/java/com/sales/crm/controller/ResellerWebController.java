package com.sales.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Reseller;
import com.sales.crm.service.ResellerService;

@Controller
@RequestMapping("/resellerWeb")
public class ResellerWebController {
	
	@Autowired
	ResellerService resellerService;
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("reseller") Reseller reseller){
		resellerService.createReseller(reseller);
		return new ModelAndView("resSuccess");
	}
	
	@RequestMapping(value="/createResellerForm", method = RequestMethod.GET)  
	public ModelAndView createResellerForm(){
		return new ModelAndView("/createReseller");
	}
	
	public Reseller get(@PathVariable long resellerID){
		return resellerService.getReseller(resellerID);
	}
	
	public ResponseEntity<Reseller> update(@RequestBody Reseller reseller){
		resellerService.updateReseller(reseller);
		return new ResponseEntity<Reseller>(reseller, HttpStatus.OK);
	}
	
	public void delete(@PathVariable long resellerID){
		resellerService.deleteReseller(resellerID);
	}
	
	
}
