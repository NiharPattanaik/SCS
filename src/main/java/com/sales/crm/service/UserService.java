package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.UserDAO;
import com.sales.crm.model.Reseller;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.User;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public User getUser(int userID){
		return userDAO.get(userID);
	}
	
	public User getUser(String userName) throws Exception{
		return userDAO.getUser(userName);
	}
	
	public void createUser(User user) throws Exception{
		userDAO.create(user);
	}
	
	public void updateUser(User user) throws Exception{
		userDAO.update(user);
	}
	
	public void deleteUser(int userID){
		userDAO.delete(userID);
	}
	
	public List<User> getResellerUsers(int userID){
		return userDAO.getResellerUsers(userID);
	}
	
	public Reseller getUserReseller(int userId){
		return userDAO.getUserReseller(userId);
	}
	
	public boolean validateUserCredential(String userName, String password){
		return userDAO.validateUserCredential(userName, password);
	}
	
	public int[] isUserNameEmailIDPresent(String userName, String email){
		return userDAO.isUserNameEmailIDPresent(userName, email);
	}
	
}
