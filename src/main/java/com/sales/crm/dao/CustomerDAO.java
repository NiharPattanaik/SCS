package com.sales.crm.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sales.crm.model.Customer;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.TrimmedCustomer;

public interface CustomerDAO {
	
	void create(Customer customer) throws Exception;
	
	Customer get(int customerID);
	
	void update(Customer customer) throws Exception;
	
	void delete(int customerID) throws Exception;
	
	List<Customer> getResellerCustomers(int resellerID);
	
	List<TrimmedCustomer> scheduledTrimmedCustomerslist(int salesExecID, Date visitDate) throws Exception;
	
	List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID);
	
	String getCustomerPrimaryMobileNo(int customerID) throws Exception;
	
	List<TrimmedCustomer> getCustomersToSchedule(int beatID, Date visitDate);
	
	List<CustomerOrder> getCustomersToScheduleDelivery(int beatID, Date visitDate, int resellerID);
	
	List<TrimmedCustomer> scheduledTrimmedCustomerslistForDeliveryToday(int delivExecID, Date visitDate);
	
	List<TrimmedCustomer> getCustomerForOTPVerification(int userID, int otpType);
	
	void createCustomers(List<Customer> customers) throws Exception;
	
	List<Customer> search(int resellerID, Map<String, Object> filterCriteria)throws Exception;
	
	int getCustomersCount(int resellerID);
}
