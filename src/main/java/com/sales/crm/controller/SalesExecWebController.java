package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Beat;
import com.sales.crm.model.Customer;
import com.sales.crm.model.SalesExecBeatCustomer;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.SalesExecService;

@Controller
@RequestMapping("/web/salesExecWeb")
public class SalesExecWebController {
	
	@Autowired
	SalesExecService salesExecService;
	
	@Autowired
	BeatService beatService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/beatlist/{resellerID}")
	public ModelAndView salesExecBeatsList(@PathVariable int resellerID){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutivesHavingBeatsAssigned(resellerID);
		return new ModelAndView("/salesexec_beats_list","salesExecs", salesExecs);  
	}
	
	@GetMapping(value="/deleteBeatsAssignment/{salesExecID}")
	public ModelAndView deleteBeatAssignment(@PathVariable int salesExecID){
		String msg = "";
		try{
			salesExecService.deleteBeatAssignment(salesExecID);
		}catch(Exception exception){
			msg = "Beats associated to Sales Executive could not be removed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/remove_assign_beats_conf","msg", msg); 
	}
	
	@GetMapping(value="/assignBeatForm") 
	public ModelAndView assignBeatToSalesExecForm(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		List<Beat> beats = beatService.getResellerBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")))) ;
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("beats", beats);
		modelMap.put("salesExec", new SalesExecutive());
		return new ModelAndView("/assign_beats", modelMap);
	}
	
	@PostMapping(value="/assignBeat") 
	public ModelAndView assignBeatToSalesExec(@ModelAttribute("salesExec") SalesExecutive salesExec){
		String msg = "";
		try{
			salesExecService.assignBeats(salesExec.getUserID(),salesExec.getBeatIDLists());
		}catch(Exception exception){
			msg = "Sales Executive to Beats assignment could not be processed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/assign_beats_conf", "msg", msg);
	}
	
	
	@GetMapping(value="/assignBeatEditForm/{salesExecID}") 
	public ModelAndView assignBeatToSalesExecEditForm(@PathVariable int salesExecID){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		SalesExecutive salesExec = salesExecService.getSalesExecutive(salesExecID);
		List<Beat> beats = beatService.getResellerBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		modelMap.put("salesExec", salesExec);
		modelMap.put("beats", beats);
		return new ModelAndView("/edit_assigned_beats", modelMap);
	}
	
	@PostMapping(value="/updateAssignedBeats") 
	public ModelAndView updateAssignedBeat(@ModelAttribute("salesExec") SalesExecutive salesExec){
		String msg = "";
		try{
			salesExecService.updateAssignedBeats(salesExec.getUserID(), salesExec.getBeatIDLists());
		}catch(Exception exception){
			msg = "Beats assigned to Sales Executive could not be updated successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/update_assign_beats_conf", "msg", msg);
	}
	
	@GetMapping(value="/salesExecBeatsCustList")
	public ModelAndView getSalesExecBeatsCustomersList(){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecMapsBeatsCustomers(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("salesExecutive", new SalesExecutive());
		return new ModelAndView("/salesexec_beats_customers_list", modelMap);
	}
	
	@GetMapping(value="/salesExecScheduleForm")
	public ModelAndView getSalesExecScheduleForm(){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("salesExecBeatCustomer", new SalesExecBeatCustomer());
		return new ModelAndView("/salesexec_schedule_visit", modelMap);
	}
	
	@PostMapping(value="/scheduleVisit") 
	public ModelAndView scheduleSalesExecVisit(@ModelAttribute("salesExecBeatCustomer") SalesExecBeatCustomer salesExecBeatCustomer){
		String msg = "";
		List<String> customerNames = null;
		try{
			customerNames = salesExecService.alreadyScheduledCustomer(salesExecBeatCustomer);
			if(customerNames != null && customerNames.size() > 0){
				msg = "<br>Customers <br><b>"+ StringUtils.join(customerNames, "<br>") +"</b><br>are already scheduled for a visit for <b>" + new SimpleDateFormat("dd-MM-yyyy").format(salesExecBeatCustomer.getVisitDate()) + "</b> date.";
			}
		}catch(Exception exception){
			msg = "Scheduling customer visit could not be processed successfully, please contact the System Administrator.";
		}
		
		if(msg.equals("")){
			try{
				salesExecService.scheduleVistit(salesExecBeatCustomer);
			}catch(Exception exception){
				msg = "Scheduling customer visit could not be processed successfully, please contact the System Administrator.";
			}
		}
		return new ModelAndView("/salesexec_schedule_visit_conf", "msg", msg);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
		binder.registerCustomEditor(List.class, "beats", new CustomCollectionEditor(List.class) {
			@Override
			protected Object convertElement(Object element) {
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(element)));

				return beat;
			}

		});
	}
}
