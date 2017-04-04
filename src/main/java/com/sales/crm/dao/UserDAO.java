package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.User;

public interface UserDAO {
	
	void create(User user);
	
	User get(int userID);
	
	void update(User user);
	
	void delete(int userID);
	
	List<User> getResellerUsers(int resellerID);
	
	List<User> getUserByRole(int resellerID, int roleID);
}
