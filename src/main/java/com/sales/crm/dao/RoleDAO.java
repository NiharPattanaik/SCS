package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.ResourcePermission;
import com.sales.crm.model.Role;

public interface RoleDAO {
	
	void create(Role role);
	
	Role get(int roleID);
	
	void update(Role role);
	
	void delete(int roleID);
	
	List<Role> getRoles(List<Integer> roleIDs);
	
	List<ResourcePermission> getResourcePermissions() throws Exception;
	
	List<ResourcePermission> getRoleResourcePermissions(List<Integer> roleIDs, int resellerID) throws Exception;
	
	void saveRoleResourcePermission(List<ResourcePermission> resourcePermissions) throws Exception;

	public List<Integer> getRoleResourcePermissionIDs(List<Integer> roleIDs, int resellerID) throws Exception;
}
