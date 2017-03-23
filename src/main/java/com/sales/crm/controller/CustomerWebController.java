package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Customer;
import com.sales.crm.service.CustomerService;

@Controller
@RequestMapping("/customerWeb")
public class CustomerWebController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping(value="/{customerID}")
	public Customer get(@PathVariable long customerID){
		return customerService.getCustomer(customerID);
	}
	
	@RequestMapping(value="/createCustomerForm", method = RequestMethod.GET)  
	public ModelAndView createResellerForm(Model model){
		model.addAttribute("customer", new Customer());
		return new ModelAndView("/create_customer");
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ResponseEntity<Customer> create(@ModelAttribute("customer") Customer customer){
		customerService.createCustomer(customer);
		return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
     dateFormat.setLenient(false);
     webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
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
