package com.sales.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.Beat;
import com.sales.crm.service.SalesExecService;

@RestController
@RequestMapping("/rest/salesExecReST")
public class SalesExecRESTController {
	
	@Autowired
	SalesExecService salesExecService;
	
	@GetMapping(value="/{salesExecID}")
	public List<Beat> getSalesExecsBeats(@PathVariable int salesExecID){
		return salesExecService.getAssignedBeats(salesExecID);
	}

}
