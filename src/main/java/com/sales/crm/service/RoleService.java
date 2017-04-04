package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.RoleDAO;
import com.sales.crm.model.Role;

@Service
public class RoleService {
	
	@Autowired
	private RoleDAO roleDAO;
	
	public Role getRole(int roleID){
		return roleDAO.get(roleID);
	}
	
	public void createRole(Role role){
		roleDAO.create(role);
	}
	
	public void updateRole(Role role){
		roleDAO.update(role);
	}
	
	public void deleteRole(int roleID){
		roleDAO.delete(roleID);
	}
	
	public List<Role> getRoles(){
		return roleDAO.getRoles();
	}
}
