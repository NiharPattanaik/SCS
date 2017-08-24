package com.sales.crm.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.Beat;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.SalesExecService;

@RestController
@RequestMapping("/rest/salesExecReST")
public class SalesExecRESTController {
	
	@Autowired
	SalesExecService salesExecService;
	
	@Autowired
	HttpSession httpSession;
	
	
	@GetMapping(value="/{salesExecID}")
	public List<Beat> getSalesExecsBeats(@PathVariable int salesExecID){
		return salesExecService.getAssignedBeats(salesExecID);
	}
	
	@GetMapping(value="/scheduledVisit/{salesExecID}/{visitDate}")
	public List<Beat> getSalesExecsBeatsScheduledVisit(@PathVariable("salesExecID") int salesExecID, @PathVariable("visitDate") String visitDate){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		
		return salesExecService.getScheduledVisitSalesExecBeats(salesExecID, date);
	}
	
	
	@GetMapping(value="/list/{visitDate}")
	public List<SalesExecutive> getScheduledVisitSalesExecs(@PathVariable String visitDate){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return salesExecService.getScheduledVisitSalesExecs(date, Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
	}

	
	@GetMapping(value="/scheduledVisit/{salesExecID}/{visitDate}/{beatID}")
	public List<TrimmedCustomer> getScheduledVisitBeatCustomers(@PathVariable("salesExecID") int salesExecID, @PathVariable("visitDate") String visitDate, @PathVariable("beatID") int beatID){
		Date date = new Date();
		try{
			date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return salesExecService.getScheduledVisitBeatCustomers(salesExecID, date, beatID);
	}
	
	@GetMapping(value="/salesExecNotMappedToSupp/{supplierID}")
	public List<SalesExecutive> getSalesExecNotMappedToSupp(@PathVariable("supplierID") int supplierID){
		return salesExecService.getSalesExecsNotMappedToSupplier(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))), supplierID);
	}

}
