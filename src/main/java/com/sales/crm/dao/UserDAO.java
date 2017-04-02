package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.User;

public interface UserDAO {
	
	void create(User user);
	
	User get(long userID);
	
	void update(User user);
	
	void delete(long userID);
	
	List<User> getResellerUsers(long resellerID);
	

}
