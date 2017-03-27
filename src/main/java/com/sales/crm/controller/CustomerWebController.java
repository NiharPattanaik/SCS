package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Customer;
import com.sales.crm.model.SalesExec;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.SalesExecService;

@Controller
@RequestMapping("/customerWeb")
public class CustomerWebController {

	@Autowired
	CustomerService customerService;
	
	@Autowired
	SalesExecService salesExecService;
	
	@GetMapping(value="/{customerID}")
	public ModelAndView get(@PathVariable long customerID){
		Customer customer = customerService.getCustomer(customerID);
		return new ModelAndView("/customer_details", "customer", customer);
		
	}
	
	@RequestMapping(value="/createCustomerForm", method = RequestMethod.GET)  
	public ModelAndView createCustomerForm(Model model){
		List<SalesExec> salesExecs = salesExecService.getResellerSalesExecs(13);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("customer", new Customer());
		modelMap.put("salesExecs", salesExecs);
		return new ModelAndView("/create_customer", modelMap);
	}
	
	@RequestMapping(value="/editCustomerForm/{customerID}", method = RequestMethod.GET)  
	public ModelAndView editCustomerForm(@PathVariable long customerID){
		Customer customer = customerService.getCustomer(customerID);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		List<SalesExec> salesExecs = salesExecService.getResellerSalesExecs(13);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("customer", customer);
		modelMap.put("salesExecs", salesExecs);
		return new ModelAndView("/edit_customer", modelMap);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("customer") Customer customer){
		customerService.createCustomer(customer);
		List<Customer> customers = customerService.getResellerCustomers(customer.getResellerID());
		return new ModelAndView("/customer_list","customers", customers); 
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     dateFormat.setLenient(false);
     webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
     }
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("customer") Customer customer){
		SalesExec salesExec = salesExecService.getSalesExec(customer.getSalesExec().getSalesExecID());
		customer.setSalesExec(salesExec);
		customerService.updateCustomer(customer);
		List<Customer> customers = customerService.getResellerCustomers(customer.getResellerID());
		return new ModelAndView("/customer_list","customers", customers); 
	}
	
	@DeleteMapping(value="/{customerID}")
	public void delete(@PathVariable long customerID){
		customerService.deleteCustomer(customerID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable long resellerID){
		List<Customer> customers = customerService.getResellerCustomers(resellerID);
		return new ModelAndView("/customer_list","customers", customers);  
	}
}
