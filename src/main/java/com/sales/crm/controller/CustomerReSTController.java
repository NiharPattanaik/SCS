package com.sales.crm.controller;

import java.util.List;

import org.apache.log4j.Logger;
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
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.CustomerService;

@RestController
@RequestMapping("/rest/customer")
public class CustomerReSTController {

	private static Logger logger = Logger.getLogger(CustomerReSTController.class);
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping(value="/{customerID}")
	public Customer get(@PathVariable int customerID){
		return customerService.getCustomer(customerID);
	}
	
	@PutMapping
	public ResponseEntity<Customer> create(@RequestBody Customer customer){
		try{
			customerService.createCustomer(customer);
		}catch(Exception exception){
			logger.error("Error while creating a new customer.", exception);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
	}
	
	@PostMapping
	public ResponseEntity<Customer> update(@RequestBody Customer customer){
		try{
			customerService.updateCustomer(customer);
		}catch(Exception exception){
			logger.error("Error while updating a customer.", exception);
		}
		return new ResponseEntity<Customer>(customer, HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{customerID}")
	public void delete(@PathVariable int customerID){
		try{
			customerService.deleteCustomer(customerID);
		}catch(Exception exception){
			logger.error("Error while deleting a customer.", exception);
		}
	}
	
	@GetMapping(value="/list/{resellerID}")
	public List<Customer> list(@PathVariable int resellerID){
		return customerService.getResellerCustomers(resellerID);
	}
	
	@GetMapping(value="/trimmedCustomers/{resellerID}")
	public List<TrimmedCustomer> trimmedCustomerslist(@PathVariable int resellerID){
		return customerService.getResellerTrimmedCustomers(resellerID);
	}
}
