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
	
	@GetMapping(value="/{delivExecID}/{tenantID}")
	public List<Beat> getDeliveryExecsBeats(@PathVariable int delivExecID, @PathVariable int tenantID){
		return delivExecService.getAssignedBeats(delivExecID, tenantID);
	}
	
	@GetMapping(value="/scheduledDeliveryBeats/{delivExecID}/{visitDate}/{tenantID}")
	public List<Beat> getDeliveryExecsBeatsScheduledVisit(@PathVariable("delivExecID") int delivExecID, @PathVariable("visitDate") String visitDate, @PathVariable("tenantID") int tenantID){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		return delivExecService.getScheduledVisitDelivExecBeats(delivExecID, date, tenantID);
	}
	
	
	@GetMapping(value="/list/{visitDate}/{tenantID}")
	public List<DeliveryExecutive> getScheduledVisitDelivExecs(@PathVariable("visitDate") String visitDate, @PathVariable("tenantID") int tenantID){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return delivExecService.getScheduledVisitDelivExecs(date, tenantID);
	}

	
	@GetMapping(value="/deliveryScheduledCustomers/{delivExecID}/{visitDate}/{beatID}/{tenantID}")
	public List<CustomerOrder> getScheduledCustomersForDelivery(@PathVariable("delivExecID") int delivExecID, @PathVariable("visitDate") String visitDate, @PathVariable("beatID") int beatID, @PathVariable("tenantID") int tenantID){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return delivExecService.getScheduledCustomersOrdersForDelivery(delivExecID, date, beatID, tenantID);
	}

}
