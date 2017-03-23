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
import com.sales.crm.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerReSTController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping(value="/{customerID}")
	public Customer get(@PathVariable long customerID){
		return customerService.getCustomer(customerID);
	}
	
	@PutMapping
	public ResponseEntity<Customer> create(@RequestBody Customer customer){
		customerService.createCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<Customer> update(@RequestBody Customer customer){
		customerService.updateCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{customerID}")
	public void delete(@PathVariable long customerID){
		customerService.deleteCustomer(customerID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public List<Customer> list(@PathVariable long resellerID){
		return customerService.getResellerCustomers(resellerID);
	}
}
