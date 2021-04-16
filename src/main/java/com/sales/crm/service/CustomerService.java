package com.sales.crm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.CustomerDAO;
import com.sales.crm.model.Customer;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.EntityStatusEnum;
import com.sales.crm.model.TrimmedCustomer;

@Service("customerService")
public class CustomerService {
	
	@Autowired
	private CustomerDAO customerDAO;
	
	@Autowired
	private OrderService orderService;
	
	public Customer getCustomer(int customerID, int tenantID){
		Customer customer = customerDAO.get(customerID, tenantID);
		customer.setOrderingProcessInProgress(orderService.isOrderingProcessInProgressForCustomer(customerID, tenantID));
		return customer;
	}
	
	public void createCustomer(Customer customer) throws Exception{
		customerDAO.create(customer);
	}
	
	public void updateCustomer(Customer customer) throws Exception{
		customerDAO.update(customer);
	}
	
	public void deleteCustomer(int customerID, int tenantID) throws Exception{
		customerDAO.delete(customerID, tenantID);
	}
	
	public List<Customer> getTenantCustomers(int tenantID){
		return customerDAO.getTenantCustomers(tenantID);
	}
	
	public List<TrimmedCustomer> scheduledTrimmedCustomerslist(int salesExecID, Date visitDate, int tenantID) throws Exception{
		return customerDAO.scheduledTrimmedCustomerslist(salesExecID, visitDate, tenantID);
	}
	
	public List<TrimmedCustomer> getTenantTrimmedCustomers(int tenantID) {
		return customerDAO.getTenantTrimmedCustomers(tenantID);
	}
	
	public String getCustomerPrimaryMobileNo(int customerID, int tenantID) throws Exception{
		return customerDAO.getCustomerPrimaryMobileNo(customerID, tenantID);
	}
	
	public List<TrimmedCustomer> getCustomersToSchedule(int beatID, Date visitDate, int tenantID){
		return customerDAO.getCustomersToSchedule(beatID, visitDate, tenantID);
	}
	
	public List<CustomerOrder> getCustomersToScheduleDelivery(int beatID, Date visitDate, int tenantID){
		return customerDAO.getCustomersToScheduleDelivery(beatID, visitDate, tenantID);
	}
	
	public List<TrimmedCustomer> scheduledTrimmedCustomerslistForDeliveryToday(int delivExecID, Date visitDate, int tenantID){
		return customerDAO.scheduledTrimmedCustomerslistForDeliveryToday(delivExecID, visitDate, tenantID);
	}
	
	public List<TrimmedCustomer> getCustomerForOTPVerification(int userID, int otpType, int tenantID){
		return customerDAO.getCustomerForOTPVerification(userID, otpType, tenantID);
	}
	
	public void createCustomers(List<Customer> customers) throws Exception{
		customerDAO.createCustomers(customers);
	}
	
	public List<Customer> search(int tenantID, Map<String, Object> filterCriteria)throws Exception{
		return customerDAO.search(tenantID, filterCriteria);
	}
	
	public int getCustomersCount(int tenantID){
		return customerDAO.getCustomersCount(tenantID);
	}
	
	public void deactivateCustomer(int customerID, int tenantID) throws Exception{
		customerDAO.updateCustomerStatus(EntityStatusEnum.INACTIVE.getEntityStatus(), customerID, tenantID);
	}
	
	public void activateCustomer(int customerID, int tenantID) throws Exception{
		customerDAO.updateCustomerStatus(EntityStatusEnum.ACTIVE.getEntityStatus(), customerID, tenantID);
	}
}
