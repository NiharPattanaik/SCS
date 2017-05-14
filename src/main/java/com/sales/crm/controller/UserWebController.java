package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import com.sales.crm.model.ResourcePermissionEnum;
import com.sales.crm.model.Role;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.User;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.RoleService;
import com.sales.crm.service.SalesExecService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/web/userWeb")
public class UserWebController {

	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	SalesExecService salesExecutiveService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/{userID}")
	public ModelAndView get(@PathVariable int userID){
		boolean isSalesExec = false;
		Map<String, Object> modelMap = new HashMap<String, Object>();
		User user = userService.getUser(userID);
		modelMap.put("user", user);
		List<Role> roles = user.getRoles();
		if(roles != null){
			for(Role role : roles){
				if(role.getRoleID() == 2){
					isSalesExec = true;
					break;
				}
			}
		}
		if(isSalesExec){
			SalesExecutive salesExecutive; salesExecutive = salesExecutiveService.getSalesExecutive(userID);
			if(salesExecutive.getBeats() != null && salesExecutive.getBeats().size() > 0){
				modelMap.put("beats", true);
			}
			
			if(salesExecutive.getCustomerIDs() != null && salesExecutive.getCustomerIDs().size() > 0){
				modelMap.put("customers", true);
			}
			
		}
		return new ModelAndView("/user_details", modelMap);
		
	}
	
	@GetMapping(value="/account/{userID}")
	public ModelAndView getAccountDetails(@PathVariable int userID){
		User user = userService.getUser(userID);
		return new ModelAndView("/account_details", "user", user);
		
	}
	
	@RequestMapping(value="/createUserForm", method = RequestMethod.GET)  
	public ModelAndView createUserForm(Model model){
		List<Role> roles = roleService.getRoles((User)httpSession.getAttribute("user"));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("user", new User());
		modelMap.put("roles", roles);
		return new ModelAndView("/create_user", modelMap);
	}
	
	@RequestMapping(value="/editUserForm/{userID}", method = RequestMethod.GET)  
	public ModelAndView editUserForm(@PathVariable int userID){
		List<Role> roles = roleService.getRoles((User)httpSession.getAttribute("user"));
		User user = userService.getUser(userID);
		Set<Integer> rolesIDSet = new HashSet<Integer>();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("user", user);
		modelMap.put("roles", roles);
		return new ModelAndView("/edit_user", modelMap);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("user") User user){
		user.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		String msg = "";
		int[] values = userService.isUserNameEmailIDPresent(user.getUserName(), user.getEmailID());
		if(values[0] == 1){
			msg = "User Name <b>"+ user.getUserName() + "</b> is already used. Please try with a different User Name.";  
		}else if(values[1] == 1){
			msg = "Email ID <b>"+ user.getEmailID() + "</b> is already used. Please try to provide a different email ID.";  
		}else{
			try{
				userService.createUser(user);
			}catch(Exception exception){
				msg = "User could be created successfully, please contact System Administrator.";
			}
		}
		return new ModelAndView("/create_user_conf", "msg", msg);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("user") User user){
		String msg = "";
		try{
			userService.updateUser(user);
		}catch(Exception exception){
			msg = "User details could not be updated successfully, please contact System Administrator. ";
		}
		Map<String, String> modelMap = new HashMap<String, String>();
		modelMap.put("msg", msg);
		modelMap.put("userID", String.valueOf(user.getUserID()));
		return new ModelAndView("/edit_user_conf", "map", modelMap);
	}
	
	@GetMapping(value="/delete/{userID}")
	public ModelAndView delete(@PathVariable int userID){
		String msg = "";
		try{
			userService.deleteUser(userID);
		}catch(Exception exception){
			msg = "User could not be successfully removed, please contact System Administrator";
		}
		return new ModelAndView("/delete_user_conf","msg", msg);  
	}
	
	@GetMapping(value="/list")
	public ModelAndView list(){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		int loggedInUserId = ((User)httpSession.getAttribute("user")).getUserID();
		List<User> users = userService.getResellerUsers(resellerID, loggedInUserId);
		return new ModelAndView("/users_list","users", users);  
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)  
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response){
		String userName = request.getParameter("uname");
		String password = request.getParameter("psw");
		User user;
		List<Integer> resourcePermIDs;
		try{
			user = userService.getUser(userName);
			resourcePermIDs = roleService.getRoleResourcePermissionIDs(user);
		}catch(Exception exception){
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("msg", "Something went wrong!. Please try after sometime and if the issue persists please contact System Administrator");
			return new ModelAndView("/login", modelMap); 
		}
		
		if(!userService.validateUserCredential(userName, password)){
			Map<String, Object> modelMap = new HashMap<String, Object>();
			modelMap.put("msg", "Invalid user name or password.");
			return new ModelAndView("/login", modelMap); 
		}else{
			httpSession.setAttribute("user", user);
			httpSession.setAttribute("resellerID", user.getResellerID());
			httpSession.setAttribute("userFullName", user.getFirstName() + " " + user.getLastName());
			httpSession.setAttribute("resourcePermIDs", resourcePermIDs);
			return new ModelAndView(getHomePage(resourcePermIDs)); 
		}
	}
	
	
	@GetMapping(value="/changePassForm")
	public ModelAndView getChangePasswordForm(){
		User user = userService.getUser(((User)httpSession.getAttribute("user")).getUserID());
		return new ModelAndView("/change_password", "user", user);
		
	}
	
	@RequestMapping(value="/updatePassword",method = RequestMethod.POST)  
	public ModelAndView updatePassword(@ModelAttribute("user") User user){
		String msg = "";
		try{
			userService.updatePassword(user);
		}catch(Exception e){
			msg = "Password could not be updated successfully. Please try after sometime if error persists contact System Administrator. ";
		}
		return new ModelAndView("/change_pass_conf", "msg", msg);
	}	
	
	/**
	@GetMapping(value="/createAdminUser/{resellerID}")  
	public ModelAndView createAdminUser(@PathVariable int resellerID){
		User user = new User();
		user.setUserName("user"+resellerID);
		user.setPassword("pass"+resellerID);
		user.setFirstName("firstName");
		user.setLastName("lastName");
		user.setResellerID(resellerID);
		Set<Integer> roleIDList = new HashSet<Integer>();
		roleIDList.add(1);
		user.setRoleIDs(roleIDList);
		userService.createUser(user);
		return new ModelAndView("/message", "message", "Admin User is successfully created. <br><b>User Name</b> - "+ user.getUserName() +"<br><b>password</b> - "+ user.getPassword());
	}
	**/
	
	private boolean isAdminUser(User user){
		List<Role> roles = user.getRoles();
		for(Role role : roles){
			if(role.getRoleID() == 1){
				return true;
			}
		}
		
		return false;
	}
	
	
	private String getHomePage(List resourcePermIDs){
		if(resourcePermIDs.contains(ResourcePermissionEnum.CUSTOMER_LIST.getResourcePermissionID())){
			return "redirect:/web/customerWeb/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.RESELLER_LIST.getResourcePermissionID())){
			return "redirect:/web/resellerWeb/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.SUPPLIER_LIST.getResourcePermissionID())){
			return "redirect:/web/supplierWeb/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.AREA_LIST.getResourcePermissionID())){
			return "redirect:/web/areaWeb/list";	
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_LIST.getResourcePermissionID())){
			return "redirect:/web/beatWeb/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.RESELLER_READ.getResourcePermissionID())){
			return "redirect:/web/resellerWeb/view";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.USER_LIST.getResourcePermissionID())){
			return "redirect:/web/userWeb/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.ROLE_LIST.getResourcePermissionID())){
			return "redirect:/web/role/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.USER_VIEW_ASSIGNED_BEATS.getResourcePermissionID())){
			return "redirect:/web/salesExecWeb/beatlist";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.BEAT_VIEW_ASSOCIATED_CUSTOMERS.getResourcePermissionID())){
			return "redirect:/web/beatWeb/beat-customers/list";
		}else if(resourcePermIDs.contains(ResourcePermissionEnum.USER_VIEW_SCHEDULED_VISITS.getResourcePermissionID())){
			return "redirect:/web/orderWeb/scheduledOrderBookings";
		}
		
		return "redirect:/web/customerWeb/list";
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
}
