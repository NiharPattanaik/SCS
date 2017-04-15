package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Reseller;
import com.sales.crm.model.User;
import com.sales.crm.service.ResellerService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/web/resellerWeb")
public class ResellerWebController {
	
	@Autowired
	ResellerService resellerService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("reseller") Reseller reseller){
		resellerService.createReseller(reseller);
		return new ModelAndView("resSuccess");
	}
	
	
	/**
	 * Used without login
	 * 
	 * @param reseller
	 * @return
	 */
	@RequestMapping(value="/saveReseller",method = RequestMethod.POST)  
	public ModelAndView createReseller(@ModelAttribute("reseller") Reseller reseller){
		resellerService.createReseller(reseller);
		User user = new User();
		user.setUserName("user"+reseller.getResellerID());
		user.setPassword("pass"+reseller.getResellerID());
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setResellerID(reseller.getResellerID());
		Set<Integer> roleIDList = new HashSet<Integer>();
		roleIDList.add(1);
		user.setRoleIDs(roleIDList);
		try{
			userService.createUser(user);
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return new ModelAndView("/message", "message", "Admin User is successfully created. <br><b>User Name</b> - "+ user.getUserName() +"<br><b>password</b> - "+ user.getPassword());
	
	}
	
	@RequestMapping(value="/createResellerForm", method = RequestMethod.GET)  
	public ModelAndView createResellerForm(){
		return new ModelAndView("/create_reseller", "reseller", new Reseller());
	}
	
	@GetMapping(value="/{resellerID}")
	public ModelAndView get(@PathVariable int resellerID){
		Reseller reseller = resellerService.getReseller(resellerID);
		return new ModelAndView("/reseller_details", "reseller", reseller);
		
	}
	
	@RequestMapping(value="/editResellerForm/{resellerID}", method = RequestMethod.GET)  
	public ModelAndView editCustomerForm(@PathVariable int resellerID){
		Reseller reseller = resellerService.getReseller(resellerID);
		return new ModelAndView("/edit_reseller", "reseller", reseller);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("reseller") Reseller reseller){
		resellerService.updateReseller(reseller);
		return get(reseller.getResellerID());
	}	
	
	public void delete(@PathVariable int resellerID){
		resellerService.deleteReseller(resellerID);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
