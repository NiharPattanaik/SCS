package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Reseller;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.User;

public interface UserDAO {
	
	void create(User user);
	
	User get(int userID);
	
	void update(User user);
	
	void delete(int userID);
	
	List<User> getResellerUsers(int resellerID);
	
	List<User> getUserByRole(int resellerID, int roleID);
	
	Reseller getUserReseller(int userId);
	
	User getUser(String userName);
	
	boolean validateUserCredential(String userName, String password);
	
}
