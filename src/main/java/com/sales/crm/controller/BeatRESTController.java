package com.sales.crm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.Beat;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.BeatService;

@RestController
@RequestMapping("/rest/beatReST")
public class BeatRESTController {
	
	@Autowired
	BeatService beatService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/{beatID}")
	public List<TrimmedCustomer> getBeatCustomers(@PathVariable int beatID){
		return beatService.getBeatCustomers(beatID);
	}
	
	@GetMapping(value="/beatsNotMappedToCustomer/{customerID}")
	public List<Beat> getBeatsNotMappedToCustomer(@PathVariable("customerID") int customerID){
		return beatService.getBeatsNotMappedToCustomer(customerID);
	}
	
	@GetMapping(value="/beatsNotMappedToSalesExec/{supplierID}/{salesExecID}")
	public List<Beat> getBeatsNotMappedToSalesExec(@PathVariable("supplierID") int supplierID, @PathVariable("salesExecID") int salesExecID){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		return beatService.getBeatsNotMappedToSalesExec(resellerID, supplierID, salesExecID);
	}

}
