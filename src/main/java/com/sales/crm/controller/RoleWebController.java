package com.sales.crm.controller;

import java.util.List;

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

import com.sales.crm.model.Role;
import com.sales.crm.service.RoleService;

@Controller
@RequestMapping("/role")
public class RoleWebController {

	@Autowired
	RoleService roleService;
	
	
	@GetMapping(value="/{roleID}")
	public ModelAndView get(@PathVariable int roleID){
		Role role = roleService.getRole(roleID);
		return new ModelAndView("/role_details", "role", role);
		
	}
	
	@RequestMapping(value="/createRoleForm", method = RequestMethod.GET)  
	public ModelAndView createRoleForm(Model model){
		return new ModelAndView("/create_role", "role", new Role());
	}
	
	@RequestMapping(value="/editRoleForm/{userID}", method = RequestMethod.GET)  
	public ModelAndView editRoleForm(@PathVariable int roleID){
		Role role = roleService.getRole(roleID);
		return new ModelAndView("/edit_role", "role", role);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("role") Role role){
		roleService.createRole(role);
		return list();
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("role") Role role){
		roleService.updateRole(role);
		return get(role.getRoleID());
	}
	
	@DeleteMapping(value="/{roleID}")
	public void delete(@PathVariable int roleID){
		roleService.deleteRole(roleID);
	}
	
	@GetMapping(value="/list")
	public ModelAndView list(){
		List<Role> roles = roleService.getRoles();
		return new ModelAndView("/roles_list","roles", roles);  
	}
	
	
}
