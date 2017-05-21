package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.Beat;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.DeliveryExecutive;
import com.sales.crm.service.DeliveryExecService;

@RestController
@RequestMapping("/rest/deliveryExecReST")
public class DeliveryExecRESTController {
	
	@Autowired
	DeliveryExecService delivExecService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/{delivExecID}")
	public List<Beat> getDeliveryExecsBeats(@PathVariable int delivExecID){
		return delivExecService.getAssignedBeats(delivExecID);
	}
	
	@GetMapping(value="/scheduledDeliveryBeats/{delivExecID}/{visitDate}")
	public List<Beat> getDeliveryExecsBeatsScheduledVisit(@PathVariable("delivExecID") int delivExecID, @PathVariable("visitDate") String visitDate){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		return delivExecService.getScheduledVisitDelivExecBeats(delivExecID, date);
	}
	
	
	@GetMapping(value="/list/{visitDate}")
	public List<DeliveryExecutive> getScheduledVisitDelivExecs(@PathVariable String visitDate){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return delivExecService.getScheduledVisitDelivExecs(date, Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
	}

	
	@GetMapping(value="/deliveryScheduledCustomers/{delivExecID}/{visitDate}/{beatID}")
	public List<CustomerOrder> getScheduledCustomersForDelivery(@PathVariable("delivExecID") int delivExecID, @PathVariable("visitDate") String visitDate, @PathVariable("beatID") int beatID){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return delivExecService.getScheduledCustomersOrdersForDelivery(delivExecID, date, beatID);
	}

}
