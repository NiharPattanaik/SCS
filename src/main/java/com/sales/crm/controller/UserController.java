package com.sales.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Customer;
import com.sales.crm.service.CustomerService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value="/login",method = RequestMethod.POST)  
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		if(request.getParameter("uname").equals("admin") &&
				request.getParameter("psw").equals("admin")){
			List<Customer> customers = customerService.getResellerCustomers(13);
			return new ModelAndView("/customer_list","customers", customers); 
		}else{
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("msg", "Invalid user name or password.");
			return new ModelAndView("/login", modelMap); 
		}
	}
	
	
}
