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
	
	@GetMapping(value="/beatlist")
	public ModelAndView salesExecBeatsList(){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutivesHavingBeatsAssigned(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
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
