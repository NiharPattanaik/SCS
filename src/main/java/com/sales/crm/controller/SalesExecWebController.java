package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.SuppSalesExecBeats;
import com.sales.crm.model.Supplier;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.SalesExecService;
import com.sales.crm.service.SupplierService;

@Controller
@RequestMapping("/web/salesExecWeb")
public class SalesExecWebController {
	
	@Autowired
	SalesExecService salesExecService;
	
	@Autowired
	BeatService beatService;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	SupplierService supplierService;
	
	
	@GetMapping(value="/beatlist")
	public ModelAndView salesExecBeatsList(){
		//List<SalesExecutive> salesExecs = salesExecService.getSalesExecutivesHavingBeatsAssigned(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		List<SuppSalesExecBeats> suppSalesExecBeats = supplierService.getSuppSalesExecBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/salesexec_beats_list","suppSalesExecBeats", suppSalesExecBeats);  
	}
	
	@GetMapping(value="/deleteBeatsAssignment/{supplierID}/{salesExecID}")
	public ModelAndView deleteBeatAssignment(@PathVariable("supplierID") int supplierID, @PathVariable("salesExecID") int salesExecID){
		String msg = "";
		try{
			salesExecService.deleteBeatAssignment(supplierID, salesExecID);
		}catch(Exception exception){
			msg = "Beats associated to Sales Executive could not be removed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/remove_assign_beats_conf","msg", msg); 
	}
	
	@GetMapping(value="/assignBeatForm") 
	public ModelAndView assignBeatToSalesExecForm(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<Supplier> suppliers = supplierService.getResellerSuppliers(resellerID);
		SuppSalesExecBeats suppSalesExecBeats = new SuppSalesExecBeats();
		modelMap.put("suppliers", suppliers);
		modelMap.put("suppSalesExecBeats", suppSalesExecBeats);
		return new ModelAndView("/assign_beats", modelMap);
	}
	
	@PostMapping(value="/assignBeat") 
	public ModelAndView assignBeatToSalesExec(@ModelAttribute("suppSalesExecBeats") SuppSalesExecBeats suppSalesExecBeats){
		String msg = "";
		try{
			int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
			salesExecService.assignBeats(resellerID, suppSalesExecBeats.getSupplier().getSupplierID(), suppSalesExecBeats.getSalesExecutive().getUserID(),suppSalesExecBeats.getBeatIDLists());
		}catch(Exception exception){
			msg = "Sales Executive to Beats assignment could not be processed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/assign_beats_conf", "msg", msg);
	}
	
	
	@GetMapping(value="/assignBeatEditForm/{suppID}/{salesExecID}") 
	public ModelAndView assignBeatToSalesExecEditForm(@PathVariable("suppID") int suppID, @PathVariable("salesExecID") int salesExecID){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//SalesExecutive salesExec = salesExecService.getSalesExecutive(salesExecID);
		List<Beat> beats = beatService.getResellerBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		SuppSalesExecBeats suppSalesExecBeats = supplierService.getSuppSalesExecBeat(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))), suppID, salesExecID);
		modelMap.put("beats", beats);
		modelMap.put("suppSalesExecBeats", suppSalesExecBeats);
		return new ModelAndView("/edit_assigned_beats", modelMap);
	}
	
	@PostMapping(value="/updateAssignedBeats") 
	public ModelAndView updateAssignedBeat(@ModelAttribute("suppSalesExecBeats") SuppSalesExecBeats suppSalesExecBeats){
		String msg = "";
		try{
			int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
			salesExecService.updateAssignedBeats(resellerID, suppSalesExecBeats.getSupplier().getSupplierID(), suppSalesExecBeats.getSalesExecutive().getUserID(), suppSalesExecBeats.getBeatIDLists());
		}catch(Exception exception){
			msg = "Beats assigned to Sales Executive could not be updated successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/update_assign_beats_conf", "msg", msg);
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
