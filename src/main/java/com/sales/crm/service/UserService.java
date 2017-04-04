package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.UserDAO;
import com.sales.crm.model.User;

@Service("userService")
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	public User getUser(int userID){
		return userDAO.get(userID);
	}
	
	public void createUser(User user){
		userDAO.create(user);
	}
	
	public void updateUser(User user){
		userDAO.update(user);
	}
	
	public void deleteUser(int userID){
		userDAO.delete(userID);
	}
	
	public List<User> getResellerUsers(int userID){
		return userDAO.getResellerUsers(userID);
	}
	
	public List<User> getSalesExecutives(int resellerID){
		return userDAO.getUserByRole(resellerID, 2);
	}
}
