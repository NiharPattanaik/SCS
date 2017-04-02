package com.sales.crm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Customer;
import com.sales.crm.model.Role;
import com.sales.crm.model.User;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.RoleService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/userWeb")
public class UserWebController {

	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping(value="/{userID}")
	public ModelAndView get(@PathVariable long userID){
		User user = userService.getUser(userID);
		return new ModelAndView("/user_details", "user", user);
		
	}
	
	@RequestMapping(value="/createUserForm", method = RequestMethod.GET)  
	public ModelAndView createUserForm(Model model){
		List<Role> roles = roleService.getRoles();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("user", new User());
		modelMap.put("roles", roles);
		return new ModelAndView("/create_user", modelMap);
	}
	
	@RequestMapping(value="/editUserForm/{userID}", method = RequestMethod.GET)  
	public ModelAndView editUserForm(@PathVariable long userID){
		User user = userService.getUser(userID);
		return new ModelAndView("/edit_user", "user", user);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("user") User user){
		userService.createUser(user);
		return list(user.getResellerID());
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("user") User user){
		userService.updateUser(user);
		return get(user.getUserID());
	}
	
	@DeleteMapping(value="/{userID}")
	public void delete(@PathVariable long userID){
		userService.deleteUser(userID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable long resellerID){
		List<User> users = userService.getResellerUsers(resellerID);
		return new ModelAndView("/users_list","users", users);  
	}
	
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
