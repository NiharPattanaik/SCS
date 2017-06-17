package com.sales.crm.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.RoleDAO;
import com.sales.crm.model.ResourcePermission;
import com.sales.crm.model.Role;
import com.sales.crm.model.User;

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
	
	public List<Role> getRoles(User user){
		List<Role> roles = user.getRoles();
		List<Integer> roleIDs = new ArrayList<Integer>();
		for(Role role : roles){
			roleIDs.add(role.getRoleID());
		}
		return roleDAO.getRoles(roleIDs);
	}
	
	public List<Role> getRoles(){
		return roleDAO.getRoles(null);
	}
	
	public List<ResourcePermission> getResourcePermissions() throws Exception {
		return roleDAO.getResourcePermissions();
	}

	public List<ResourcePermission> getRolesResourcePermissions(List<Integer> roleIDs, int resellerID) throws Exception {
		return roleDAO.getRoleResourcePermissions(roleIDs, resellerID);
	}
	
	public void saveRoleResourcePermission(List<ResourcePermission> resourcePermissions, int roleID, int resellerID) throws Exception{
		roleDAO.saveRoleResourcePermission(resourcePermissions, roleID, resellerID);
	}
	
	public List<Integer> getRoleResourcePermissionIDs(List<Integer> roleIDs, int resellerID) throws Exception {
		return roleDAO.getRoleResourcePermissionIDs(roleIDs, resellerID);
	}
	
	public List<Integer> getRoleResourcePermissionIDs(User user) throws Exception {
		List<Role> roles = user.getRoles();
		List<Integer> roleIDs = new ArrayList<Integer>();
		for(Role role : roles){
			roleIDs.add(role.getRoleID());
		}
		return roleDAO.getRoleResourcePermissionIDs(roleIDs, user.getResellerID());
	}
}
