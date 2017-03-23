package com.sales.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.Customer;
import com.sales.crm.model.SalesExec;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.SalesExecService;

@RestController
@RequestMapping("/salesexec")
public class SalesExecReSTController {

	@Autowired
	SalesExecService salesExecService;
	
	@GetMapping(value="/{salesExecID}")
	public SalesExec get(@PathVariable long salesExecID){
		return salesExecService.getSalesExec(salesExecID);
	}
	
	@PutMapping
	public ResponseEntity<SalesExec> create(@RequestBody SalesExec salesExec){
		salesExecService.createSalesExec(salesExec);
		return new ResponseEntity<SalesExec>(salesExec, HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<SalesExec> update(@RequestBody SalesExec salesExec){
		salesExecService.updateSalesExec(salesExec);
		return new ResponseEntity<SalesExec>(salesExec, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{salesExecID}")
	public void delete(@PathVariable long salesExecID){
		salesExecService.deleteSalesExec(salesExecID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public List<SalesExec> list(@PathVariable long resellerID){
		return salesExecService.getResellerSalesExecs(resellerID);
	}
}
