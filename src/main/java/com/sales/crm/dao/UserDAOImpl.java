package com.sales.crm.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Beat;
import com.sales.crm.model.Reseller;
import com.sales.crm.model.Role;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.User;

@Repository("userDAO")
public class UserDAOImpl implements UserDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	public void create(User user) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setDateCreated(new Date());
			// save user
			session.save(user);
			// create reseller_user
			SQLQuery createResellerUser = session.createSQLQuery("INSERT INTO RESELLER_USER VALUES (?, ?)");
			createResellerUser.setParameter(0, user.getResellerID());
			createResellerUser.setParameter(1, user.getUserID());
			createResellerUser.executeUpdate();
			// create user_role
			if (user.getRoleIDs() != null && user.getRoleIDs().size() > 0) {
				for (int roleID : user.getRoleIDs()) {
					SQLQuery createUserRole = session.createSQLQuery("INSERT INTO USER_ROLE VALUES (?, ?)");
					createUserRole.setParameter(0, user.getUserID());
					createUserRole.setParameter(1, roleID);
					createUserRole.executeUpdate();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public User get(int userID) {

		Session session = null;
		User user = null;
		List<Role> roles = new ArrayList<Role>();
		try{
			session = sessionFactory.openSession();
			user = (User)session.get(User.class, userID);
			Set<Integer> roleIDs = new HashSet<Integer>();
			//Get Roles
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.ROLE_NAME, a.DESCRIPTION FROM ROLE a, USER_ROLE b WHERE a.ID=b.ROLE_ID AND b.USER_ID=? ");
			query.setParameter(0, user.getUserID());
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				Role role = new Role();
				role.setRoleID(Integer.valueOf(String.valueOf(objs[0])));
				role.setRoleName(String.valueOf(objs[1]));
				role.setDescription(String.valueOf(objs[2]));
				roles.add(role);
				roleIDs.add(role.getRoleID());
			}
			//Get Reseller ID
			SQLQuery resellerIDQuery = session.createSQLQuery("SELECT a.ID FROM RESELLER a, RESELLER_USER b WHERE a.ID=b.RESELLER_ID AND b.USER_ID= ?");
			resellerIDQuery.setParameter(0, userID);
			List ids = resellerIDQuery.list();
			if(ids != null && ids.size() == 1){
				user.setResellerID(Integer.valueOf(String.valueOf(ids.get(0))));
			}
			if(roles.size() > 0){
				user.setRoles(roles);
				user.setRoleIDs(roleIDs);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return user;
	
	}

	@Override
	public void update(User user) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			user.setDateModified(new Date());
			session.update(user);
			//update user_role
			//TODO: Need to use update instead of delete and then insert
			if(user.getRoleIDs() != null && user.getRoleIDs().size() > 0){
				SQLQuery deleteUserRole = session.createSQLQuery("DELETE FROM USER_ROLE WHERE USER_ID= ?");
				deleteUserRole.setParameter(0, user.getUserID());
				deleteUserRole.executeUpdate();
				for(int roleID : user.getRoleIDs()){
					SQLQuery createUserRole = session.createSQLQuery("INSERT INTO USER_ROLE VALUES (?, ?)");
					createUserRole.setParameter(0, user.getUserID());
					createUserRole.setParameter(1, roleID);
					createUserRole.executeUpdate();
				}
			}
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	
		
	}

	@Override
	public void delete(int userID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			User user = (User)session.get(User.class, userID);
			transaction = session.beginTransaction();
			
			//Delete from RESELLER_USER
			SQLQuery resellerUserQuery = session.createSQLQuery("DELETE FROM RESELLER_USER WHERE USER_ID=? ");
			resellerUserQuery.setParameter(0, userID);
			resellerUserQuery.executeUpdate();
			
			//Delete from USER_ROLE
			SQLQuery userRoleQuery = session.createSQLQuery("DELETE FROM USER_ROLE WHERE USER_ID=? ");
			userRoleQuery.setParameter(0, userID);
			userRoleQuery.executeUpdate();
			
			//Delete user
			session.delete(user);
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}

	//TODO : SQL needs to be improved
	@Override
	public List<User> getResellerUsers(int resellerID) {

		Session session = null;
		List<User> users = new ArrayList<User>(); 
		try{
			session = sessionFactory.openSession();
			//Get USER IDS from reseller id
			SQLQuery getUserIds = session.createSQLQuery("SELECT USER_ID FROM RESELLER_USER WHERE RESELLER_ID = ?");
			getUserIds.setParameter(0, resellerID);
			List userIDs = getUserIds.list();
			if(userIDs.size() > 0){
				Query userQuery = session.createQuery("FROM User WHERE userID IN (:ids)");
				userQuery.setParameterList("ids", userIDs);
				users = userQuery.list();
			}
			
			Map<Integer, List<Role>> rolesMap = new HashMap<Integer, List<Role>>();
			if(userIDs.size() > 0){
				SQLQuery getRoles = session.createSQLQuery("SELECT b.USER_ID, a.ID, a.ROLE_NAME, a.DESCRIPTION FROM  ROLE a, USER_ROLE b WHERE b.ROLE_ID=a.ID AND b.USER_ID IN (" +StringUtils.join(userIDs, ",") +")");
				//getRoles.setParameter(0, userIDs);
				List results = getRoles.list();
				for(Object obj : results){
					Object[] objs = (Object[])obj;
					int userID = Integer.valueOf(String.valueOf(objs[0]));
					Role role = new Role();
					role.setRoleID(Integer.valueOf(String.valueOf(objs[1])));
					role.setRoleName(String.valueOf(objs[2]));
					role.setDescription(String.valueOf(objs[3]));
					if(rolesMap.get(userID) == null){
						rolesMap.put(userID, new ArrayList<Role>());
					}
					rolesMap.get(userID).add(role);
				}
			}
			
			//Set Role in Users
			for(User user : users){
				if(rolesMap.containsKey(user.getUserID())){
					user.setRoles(rolesMap.get(user.getUserID()));
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return users;
	}

	@Override
	public List<User> getUserByRole(int resellerID, int roleID) {

		Session session = null;
		List<User> users = new ArrayList<User>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.USER_NAME, a.DESCRIPTION, a.EMAIL_ID, a.MOBILE_NO, a.FIRST_NAME, a.LAST_NAME, a.STATUS, a.DATE_CREATED, a.DATE_MODIFIED, a.COMPANY_ID FROM USER a, USER_ROLE b, RESELLER_USER c WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND b.ROLE_ID= ? AND c.RESELLER_ID=?");
			query.setParameter(0, roleID);
			query.setParameter(1, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				User user = new User();
				user.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				user.setUserName(String.valueOf(objs[1]));
				user.setDescription(String.valueOf(objs[2]));
				user.setEmailID(String.valueOf(objs[3]));
				user.setMobileNo(String.valueOf(objs[4]));
				user.setFirstName(String.valueOf(objs[5]));
				user.setLastName(String.valueOf(objs[6]));
				user.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					user.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					user.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				user.setCompanyID(Integer.valueOf(String.valueOf(objs[10])));
				user.setResellerID(resellerID);
				users.add(user);
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return users;
	}

	@Override
	public Reseller getUserReseller(int userId) {

		Session session = null;
		try{
			session = sessionFactory.openSession();
			//Get Reseller ID
			SQLQuery resellerIDQuery = session.createSQLQuery("SELECT a.ID FROM RESELLER a, RESELLER_USER b WHERE a.ID=b.RESELLER_ID AND b.USER_ID= ?");
			resellerIDQuery.setParameter(0, userId);
			List ids = resellerIDQuery.list();
			if(ids != null && ids.size() == 1){
				return (Reseller)session.get(Reseller.class, Integer.valueOf(String.valueOf(ids.get(0))));
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return null;
	
	}

	@Override
	public User getUser(String userName) {
		Session session = null;
		User user = null;
		try{
			session = sessionFactory.openSession();
			Query userQuery = session.createQuery("from User where userName = :userName");
			userQuery.setParameter("userName", userName);
			List users = userQuery.list();
			if(users != null && users.size() == 1){
				user = (User)users.get(0);
				//Get Roles
				List<Role> roles = new ArrayList<Role>();
				SQLQuery rolesQuery = session.createSQLQuery("SELECT a.ID, a.ROLE_NAME, a.DESCRIPTION FROM ROLE a, USER_ROLE b WHERE a.ID=b.ROLE_ID AND b.USER_ID=? ");
				rolesQuery.setParameter(0, user.getUserID());
				List results = rolesQuery.list();
				for(Object obj : results){
					Object[] objs = (Object[])obj;
					Role role = new Role();
					role.setRoleID(Integer.valueOf(String.valueOf(objs[0])));
					role.setRoleName(String.valueOf(objs[1]));
					role.setDescription(String.valueOf(objs[2]));
					roles.add(role);
				}
				//Get Reseller ID
				SQLQuery resellerIDQuery = session.createSQLQuery("SELECT a.ID FROM RESELLER a, RESELLER_USER b WHERE a.ID=b.RESELLER_ID AND b.USER_ID= ?");
				resellerIDQuery.setParameter(0, user.getUserID());
				List ids = resellerIDQuery.list();
				if(ids != null && ids.size() == 1){
					user.setResellerID(Integer.valueOf(String.valueOf(ids.get(0))));
				}
				if(roles.size() > 0){
					user.setRoles(roles);
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return user;
	}

	@Override
	public boolean validateUserCredential(String userName, String password) {
		Session session = null;
		try{
			session = sessionFactory.openSession();
			SQLQuery userQuery = session.createSQLQuery("SELECT COUNT(*) FROM USER WHERE USER_NAME=? AND PASSWORD= ?");
			userQuery.setParameter(0, userName);
			userQuery.setParameter(1,  password);
			List counts = userQuery.list();
			if(counts != null && counts.size() == 1 && ((BigInteger)counts.get(0)).intValue() == 1){
				return true;
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return false;
	}
	
	

}
