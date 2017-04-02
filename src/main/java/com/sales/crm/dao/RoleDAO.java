package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Role;

public interface RoleDAO {
	
	void create(Role role);
	
	Role get(long roleID);
	
	void update(Role role);
	
	void delete(long roleID);
	
	List<Role> getRoles();

}
