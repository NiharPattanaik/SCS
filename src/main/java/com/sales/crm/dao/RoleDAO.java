package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Role;

public interface RoleDAO {
	
	void create(Role role);
	
	Role get(int roleID);
	
	void update(Role role);
	
	void delete(int roleID);
	
	List<Role> getRoles();

}
