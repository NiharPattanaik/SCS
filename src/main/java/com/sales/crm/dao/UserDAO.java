package com.sales.crm.dao;

import java.util.List;

import com.sales.crm.model.Reseller;
import com.sales.crm.model.SecurityQuestion;
import com.sales.crm.model.User;

public interface UserDAO {
	
	void create(User user) throws Exception;
	
	User get(int userID);
	
	void update(User user) throws Exception;
	
	void delete(int userID);
	
	List<User> getResellerUsers(int resellerID, int loggedInUserID);
	
	List<User> getUserByRole(int resellerID, int roleID);
	
	Reseller getUserReseller(int userId);
	
	User getUser(String userName) throws Exception;
	
	boolean validateUserCredential(String userName, String password);
	
	int[] isUserNameEmailIDPresent(String userName, String email);
	
	void updatePassword(User user) throws Exception;
	
	List<SecurityQuestion> getAllSecurityQuestions();
	
	void updateFirstLoginPassword(User user);
}
