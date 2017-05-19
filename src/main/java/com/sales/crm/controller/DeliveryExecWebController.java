package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.sales.crm.model.DeliveryBookingSchedule;
import com.sales.crm.model.DeliveryExecutive;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.DeliveryExecService;

@Controller
@RequestMapping("/web/deliveryExecWeb")
public class DeliveryExecWebController {
	
	@Autowired
	private DeliveryExecService deliveryExecService;
	
	@Autowired
	HttpSession httpSession;
	
	
	@Autowired
	private BeatService beatService;

	@GetMapping(value="/scheduledDeliveryBookings")
	public ModelAndView getScheduledDeliveryBookingsList(){
		List<DeliveryExecutive> delivExecs = deliveryExecService.getDeliveryExecutivesScheduled(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("delivExecs", delivExecs);
		modelMap.put("deliveryBookingSchedule", new DeliveryBookingSchedule());
		return new ModelAndView("/delivery_booking_list", modelMap);
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
			msg = "Delivery Executive to Beats assignment could not be processed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/delivery_exec_assign_beats_conf", "msg", msg);
	}
	
	@GetMapping(value="/assignBeatEditForm/{delivExecID}") 
	public ModelAndView assignBeatToSalesExecEditForm(@PathVariable int delivExecID){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		DeliveryExecutive delivExec = deliveryExecService.getDelivExecutive(delivExecID);
		List<Beat> beats = beatService.getResellerBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		modelMap.put("delivExec", delivExec);
		modelMap.put("beats", beats);
		return new ModelAndView("/edit_assigned_beats_to_delivery_exec", modelMap);
	}
	
	@PostMapping(value="/updateAssignedBeats") 
	public ModelAndView updateAssignedBeat(@ModelAttribute("delivExec") DeliveryExecutive delivExec){
		String msg = "";
		try{
			deliveryExecService.updateAssignedBeats(delivExec.getUserID(), delivExec.getBeatIDLists());
		}catch(Exception exception){
			msg = "Beats assigned to Delivery Executive could not be updated successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/update_delivery_exec_beats_conf", "msg", msg);
	}
	
	@GetMapping(value="/deleteBeatsAssignment/{delivExecID}")
	public ModelAndView deleteBeatAssignment(@PathVariable int delivExecID){
		String msg = "";
		try{
			deliveryExecService.deleteBeatAssignment(delivExecID);
		}catch(Exception exception){
			msg = "Beats associated to Delivery Executive could not be removed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/remove_deliv_exec_assign_beats_conf","msg", msg); 
	}
	
	@GetMapping(value="/scheduleDeliveryBookingForm")
	public ModelAndView getScheduleOrderBookingForm(){
		List<DeliveryExecutive> delivExecs = deliveryExecService.getDeliveryExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("delivExecs", delivExecs);
		modelMap.put("deliveryBookingSchedule", new DeliveryBookingSchedule());
		return new ModelAndView("/schedule_delivery_booking", modelMap);
	}
	
	@PostMapping(value="/scheduleDeliveryBooking") 
	public ModelAndView scheduleOrderBooking(@ModelAttribute("deliveryBookingSchedule") DeliveryBookingSchedule deliveryBookingSchedule){
		String msg = "";
		List<String> customerNames = null;
		try{
			customerNames = deliveryExecService.alreadyDeliveryBookingScheduledCustomer(deliveryBookingSchedule);
			if(customerNames != null && customerNames.size() > 0){
				msg = "<br>Customers <br><b>"+ StringUtils.join(customerNames, "<br>") +"</b><br>are already scheduled for a visit for <b>" + new SimpleDateFormat("dd-MM-yyyy").format(deliveryBookingSchedule.getVisitDate()) + "</b> date.";
			}
		}catch(Exception exception){
			msg = "Scheduling of order delivery could not be processed successfully, please contact the System Administrator.";
		}
		
		if(msg.equals("")){
			try{
				deliveryExecService.scheduleDeliveryBooking(deliveryBookingSchedule);
			}catch(Exception exception){
				msg = "Scheduling of order delivery could not be processed successfully, please contact the System Administrator.";
			}
		}
		return new ModelAndView("/delivery_booking_schedule_conf", "msg", msg);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}
	
}
