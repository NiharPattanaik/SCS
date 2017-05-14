package com.sales.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Beat;
import com.sales.crm.model.DeliveryExecutive;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.DeliveryExecService;
import com.sales.crm.service.SalesExecService;

@Controller
@RequestMapping("/web/deliveryWeb")
public class DeliveryExecWebController {
	
	@Autowired
	private DeliveryExecService deliveryExecService;
	
	@Autowired
	HttpSession httpSession;
	
	SalesExecService salesExecService;
	
	@Autowired
	private BeatService beatService;

	@GetMapping(value="/scheduledDeliveryBookings")
	public ModelAndView getScheduledDeliveryBookingsList(){
		//List<SalesExecutive> salesExecs = salesExecService.getSalesExecMapsBeatsCustomers(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//modelMap.put("salesExecs", salesExecs);
		modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		return new ModelAndView("/order_schedule_list", modelMap);
	}
	
	@GetMapping(value="/beatlist")
	public ModelAndView salesExecBeatsList(){
		List<DeliveryExecutive> delivExecs = deliveryExecService.getDelivExecutivesHavingBeatsAssigned(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/delivexec_beats_list","delivExecs", delivExecs);  
	}
	
	@GetMapping(value="/assignBeatForm") 
	public ModelAndView assignBeatToSalesExecForm(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<DeliveryExecutive> delivExecs = deliveryExecService.getDeliveryExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		List<Beat> beats = beatService.getResellerBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")))) ;
		modelMap.put("delivExecs", delivExecs);
		modelMap.put("beats", beats);
		modelMap.put("delivExec", new DeliveryExecutive());
		return new ModelAndView("/delivExec_assign_beats", modelMap);
	}
	
	@PostMapping(value="/assignBeat") 
	public ModelAndView assignBeatToSalesExec(@ModelAttribute("delivExec") DeliveryExecutive delivExec){
		String msg = "";
		try{
			deliveryExecService.assignBeats(delivExec.getUserID(),delivExec.getBeatIDLists());
		}catch(Exception exception){
			msg = "Sales Executive to Beats assignment could not be processed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/delivery_exec_assign_beats_conf", "msg", msg);
	}
	
}
