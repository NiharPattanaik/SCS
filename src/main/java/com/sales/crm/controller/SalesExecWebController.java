package com.sales.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.SalesExec;
import com.sales.crm.service.SalesExecService;

@RestController
@RequestMapping("/salesexecWeb")
public class SalesExecWebController {

	@Autowired
	SalesExecService salesExecService;
	
	@GetMapping(value="/{salesExecID}")
	public ModelAndView get(@PathVariable int salesExecID){
		SalesExec salesExec = salesExecService.getSalesExec(salesExecID);
		return new ModelAndView("/salesExec_details", "salesExec", salesExec);
		
	}
	
	@RequestMapping(value="/createSalesExecForm", method = RequestMethod.GET)  
	public ModelAndView createSalesExecForm(Model model){
		return new ModelAndView("/create_salesExec", "salesExec", new SalesExec());
	}
	
	@RequestMapping(value="/editSalesExecForm/{salesExecID}", method = RequestMethod.GET)  
	public ModelAndView editSalesExecForm(@PathVariable int salesExecID){
		SalesExec salesExec = salesExecService.getSalesExec(salesExecID);
		return new ModelAndView("/edit_salesExec", "salesExec", salesExec);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("salesExec") SalesExec salesExec){
		salesExec.setResellerID(13);
		salesExecService.createSalesExec(salesExec);
		List<SalesExec> salesExecs = salesExecService.getResellerSalesExecs(salesExec.getResellerID());
		return new ModelAndView("/salesExecs_list","salesExecs", salesExecs); 
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("salesExec") SalesExec salesExec){
		salesExecService.updateSalesExec(salesExec);
		return get(salesExec.getSalesExecID());
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable int resellerID){
		List<SalesExec> salesExecs = salesExecService.getResellerSalesExecs(resellerID);
		return new ModelAndView("/salesExecs_list","salesExecs", salesExecs);  
	}
}
