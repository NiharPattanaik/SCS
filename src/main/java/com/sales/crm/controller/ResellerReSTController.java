package com.sales.crm.controller;

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

import com.sales.crm.model.Reseller;
import com.sales.crm.service.ResellerService;

@RestController
@RequestMapping("/reseller")
public class ResellerReSTController {
	
	@Autowired
	ResellerService resellerService;
	
	
	@PutMapping
	public ResponseEntity<Reseller> create(@RequestBody Reseller reseller){
		resellerService.createReseller(reseller);
		return new ResponseEntity<Reseller>(reseller, HttpStatus.CREATED);
	}
	
	@GetMapping(value="/{resellerID}")
	public Reseller get(@PathVariable long resellerID){
		return resellerService.getReseller(resellerID);
	}
	
	@PostMapping
	public ResponseEntity<Reseller> update(@RequestBody Reseller reseller){
		resellerService.updateReseller(reseller);
		return new ResponseEntity<Reseller>(reseller, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{resellerID}")
	public void delete(@PathVariable long resellerID){
		resellerService.deleteReseller(resellerID);
	}
	
	
}
