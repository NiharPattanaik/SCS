package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Customer;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.User;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.SalesExecService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/web/customerWeb")
public class CustomerWebController {

	@Autowired
	CustomerService customerService;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	SalesExecService salesExecService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/{customerID}")
	public ModelAndView get(@PathVariable int customerID){
		Customer customer = customerService.getCustomer(customerID);
		return new ModelAndView("/customer_details", "customer", customer);
		
	}
	
	@RequestMapping(value="/createCustomerForm", method = RequestMethod.GET)  
	public ModelAndView createCustomerForm(Model model){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("customer", new Customer());
		modelMap.put("salesExecs", salesExecs);
		return new ModelAndView("/create_customer", modelMap);
	}
	
	@RequestMapping(value="/editCustomerForm/{customerID}", method = RequestMethod.GET)  
	public ModelAndView editCustomerForm(@PathVariable int customerID){
		Customer customer = customerService.getCustomer(customerID);
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("customer", customer);
		modelMap.put("salesExecs", salesExecs);
		return new ModelAndView("/edit_customer", modelMap);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("customer") Customer customer){
		customer.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		customerService.createCustomer(customer);
		List<Customer> customers = customerService.getResellerCustomers(customer.getResellerID());
		return new ModelAndView("/customer_list","customers", customers); 
	}
	
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("customer") Customer customer){
		customerService.updateCustomer(customer);
		List<Customer> customers = customerService.getResellerCustomers(customer.getResellerID());
		return new ModelAndView("/customer_list","customers", customers); 
	}
	
	@GetMapping(value="/delete/{customerID}")
	public ModelAndView delete(@PathVariable int customerID){
		customerService.deleteCustomer(customerID);
		List<Customer> customers = customerService.getResellerCustomers(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/customer_list","customers", customers); 
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable int resellerID){
		List<Customer> customers = customerService.getResellerCustomers(resellerID);
		return new ModelAndView("/customer_list","customers", customers);  
	}
	
	@InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
     dateFormat.setLenient(false);
     webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
     
     webDataBinder.registerCustomEditor(User.class, "salesExec", new CustomNumberEditor(Integer.class, true)
	    {
	      @Override
	      public void setValue(Object value)
	      {
	    	 if(value instanceof Integer) {
	    		 User user = new User();
	    		 if(value != null){
	    			 user.setUserID(Integer.parseInt(String.valueOf(value)));
	    		 }
	    		 super.setValue(user);
	    	 }else{
	    		 super.setValue(value);
	    	 }
	      }


	    });  
     }
	
}
