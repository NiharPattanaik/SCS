package com.sales.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.Beat;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.SalesExecService;

@RestController
@RequestMapping("/rest/beatReST")
public class BeatRESTController {
	
	@Autowired
	BeatService beatService;
	
	@GetMapping(value="/{beatID}")
	public List<TrimmedCustomer> getBeatCustomers(@PathVariable int beatID){
		return beatService.getBeatCustomers(beatID);
	}

}
