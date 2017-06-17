package com.sales.crm.service;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.CustomerDAO;
import com.sales.crm.dao.ResellerDAO;
import com.sales.crm.model.Address;
import com.sales.crm.model.BusinessEntity;
import com.sales.crm.model.Reseller;
import com.sales.crm.model.Role;
import com.sales.crm.model.User;

@Service("resellerService")
public class ResellerService {
	
	@Autowired
	private ResellerDAO resellerDAO;
	
	@Autowired
	private CustomerDAO customerService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	DataSourceTransactionManager transactionManager;
	
	public Reseller getReseller(int resellerID){
		Reseller reseller = resellerDAO.get(resellerID);
		reseller.setCustomers(customerService.getResellerCustomers(resellerID));
		return reseller;
	}
	
	public void createReseller(Reseller reseller) throws Exception{
		resellerDAO.create(reseller);
	}
	
	public void updateReseller(Reseller reseller){
		resellerDAO.update(reseller);
	}
	
	public void deleteReseller(int resellerID){
		resellerDAO.delete(resellerID);
	}
	
	public boolean isEmailIDAlreadyUsed(String emailID) throws Exception{
		return resellerDAO.isEmailIDAlreadyUsed(emailID);
	}
	
	public List<Reseller> getResellers() throws Exception{
		return resellerDAO.getResellers();
	}
	
	public void activateReseller(int resellerID){
		try{
			Reseller reseller = resellerDAO.get(resellerID);
			//update reseller
			reseller.setStatus(BusinessEntity.STATUS_ACTIVE);
			resellerDAO.update(reseller);
			//create user
			User user = new User();
			
			user.setPassword(String.valueOf(new Date().getTime()));
			user.setResellerID(resellerID);
			for(Address address : reseller.getAddress()){
				if(address.getAddrressType() == 1){
					user.setEmailID(address.getEmailID());
					user.setUserName(address.getEmailID());
					user.setMobileNo(address.getMobileNumberPrimary());
					user.setFirstName(address.getContactPerson());
					break;
				}
			}
			//Add Role
			Set<Integer> roleIds = new HashSet<Integer>();
			roleIds.add(100);
			user.setRoleIDs(roleIds);
			userService.createUser(user);
			
			//send email
			sendMail(user.getEmailID(), user.getUserName(), user.getPassword());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	private boolean sendMail(String emailID, String userName, String pass) {
		
		String msg = "Dear Reseller \n you are successfully on-boarded to the system. Please find the login details below. \n\n\n URL : http://35.189.180.185:8080/crm \n user name: "+ userName +"\n Password: "+ pass + "\n\n\n Regards, \n Team";

		final String username = "nihar.less.sec@gmail.com";
		final String password = "m1001636";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("from-email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailID));
			message.setSubject("You are onboared");
			message.setText(msg);
			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}
