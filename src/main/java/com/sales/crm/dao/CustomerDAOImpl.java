package com.sales.crm.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Address;
import com.sales.crm.model.Beat;
import com.sales.crm.model.Customer;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.Order;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.model.User;


@Repository("customerDAO")
public class CustomerDAOImpl implements CustomerDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(CustomerDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	
	@Override
	public void create(Customer customer) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			customer.setDateCreated(new Date());
			for(Address address : customer.getAddress()){
				address.setDateCreated(new Date());
			}
			session.save(customer);
			//Customer-Beat Assignment
			if(customer.getBeatID() != -1){
				SQLQuery customerBeatQuery = session.createSQLQuery("INSERT INTO BEAT_CUSTOMER VALUES (?, ?)");
				customerBeatQuery.setParameter(0, customer.getBeatID());
				customerBeatQuery.setParameter(1, customer.getCustomerID());
				customerBeatQuery.executeUpdate();
			}
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating customer", e);
			if(transaction != null){
				transaction.rollback();
			}
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public Customer get(int customerID) {
		Session session = null;
		Customer customer = null;
		try{
			session = sessionFactory.openSession();
			customer = (Customer)session.get(Customer.class, customerID);
			//Get Sales exec details
			SQLQuery salesExecQuery = session.createSQLQuery("SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, CUSTOMER_SALES_EXEC b WHERE b.SALES_EXEC_ID=a.ID AND b.CUSTOMER_ID= ?");
			salesExecQuery.setParameter(0, customer.getCustomerID());
			List salesExecs = salesExecQuery.list();
			if(salesExecs != null && salesExecs.size() == 1){
				Object[] objs = (Object[])salesExecs.get(0);
				customer.setSalesExecID(Integer.valueOf(String.valueOf(objs[0])));
				customer.setSalesExecName(String.valueOf(objs[1]) + " " + String.valueOf(objs[2]) );
			}
		}catch(Exception exception){
			logger.error("Error while fetching customer details", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customer;
	}

	@Override
	public void update(Customer customer) throws Exception{

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			customer.setDateModified(new Date());
			for(Address address : customer.getAddress()){
				address.setDateModified(new Date());
			}
			session.update(customer);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating customer", e);
			if(transaction != null){
				transaction.rollback();
			}
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

	@Override
	public void delete(int customerID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Customer customer = (Customer)session.get(Customer.class, customerID);
			transaction = session.beginTransaction();
			//Remove Customer_Sales_Executive Link
			/**
			SQLQuery removeCustSalesExecLink = session.createSQLQuery(" DELETE FROM CUSTOMER_SALES_EXEC WHERE CUSTOMER_ID=?");
			removeCustSalesExecLink.setParameter(0, customer.getCustomerID());
			removeCustSalesExecLink.executeUpdate();
			**/
			//Remove Customer
			session.delete(customer);
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting customer.", exception);
			if(transaction != null){
				transaction.rollback();
			}
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}

	@Override
	public List<Customer> getResellerCustomers(int resellerID) {
		Session session = null;
		List<Customer> customers = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Customer where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			customers = query.list();
			
			//Fetch Sales Execs
			Map<Integer, User> salesExecMap = new HashMap<Integer, User>();
			SQLQuery salesExecQry = session.createSQLQuery("SELECT d.CUSTOMER_ID, a.ID, a.USER_NAME, a.FIRST_NAME, a.LAST_NAME FROM USER a, USER_ROLE b, RESELLER_USER c, CUSTOMER_SALES_EXEC d  WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND d.SALES_EXEC_ID=a.ID AND b.ROLE_ID= 2 AND c.RESELLER_ID= ?");
			salesExecQry.setParameter(0, resellerID);
			List results = salesExecQry.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				int customerId = Integer.valueOf(String.valueOf(objs[0]));
				User user = new User();
				user.setUserID(Integer.valueOf(String.valueOf(objs[1])));
				user.setUserName(String.valueOf(objs[2]));
				user.setFirstName(String.valueOf(objs[3]));
				user.setLastName(String.valueOf(objs[4]));
				salesExecMap.put(customerId, user);
			}
			//Set Sales Execs in customer
			for(Customer customer : customers){
				if(salesExecMap.containsKey(customer.getCustomerID())){
					customer.setSalesExecID(salesExecMap.get(customer.getCustomerID()).getUserID());
					customer.setSalesExecName(salesExecMap.get(customer.getCustomerID()).getFirstName() +" "+salesExecMap.get(customer.getCustomerID()).getLastName());
				}
			}
		}catch(Exception exception){
			logger.error("Error while fetching customer List.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}

	@Override
	public List<TrimmedCustomer> scheduledTrimmedCustomerslist(int salesExecID, Date visitDate) throws Exception{
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.NAME, b.ID order_booking_id FROM CUSTOMER a, ORDER_BOOKING_SCHEDULE b, ORDER_BOOKING_SCHEDULE_CUSTOMERS c  WHERE a.ID = c.CUSTOMER_ID AND b.ID = c.ORDER_BOOKING_SCHEDULE_ID AND  c.STATUS = 1 AND b.SALES_EXEC_ID= ? AND b.VISIT_DATE = ?");
			query.setParameter( 0, salesExecID);
			query.setParameter(1, new java.sql.Date(visitDate.getTime()));
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				trimmedCustomer.setOrderBookingID(Integer.valueOf(String.valueOf(objs[2])));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}
	
	@Override
	public List<TrimmedCustomer> getResellerTrimmedCustomers(int resellerID){
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, NAME FROM CUSTOMER WHERE RESELLER_ID=?");
			query.setParameter( 0, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}
	
	@Override
	public String getCustomerPrimaryMobileNo(int customerID) throws Exception{

		Session session = null;
		String mobileNo = "";
		try{
			session = sessionFactory.openSession();
			SQLQuery mobileNoQuery = session.createSQLQuery("SELECT a.MOBILE_NUMBER_PRIMARY FROM ADDRESS a, CUSTOMER_ADDRESS b WHERE a.ID=b.ADDRESS_ID AND b.CUSTOMER_ID=? AND a.ADDRESS_TYPE=1");
			mobileNoQuery.setParameter(0, customerID);
			List numbers = mobileNoQuery.list();
			if(numbers != null && numbers.size() == 1){
				mobileNo =  "+91"+String.valueOf(numbers.get(0));
			}
		}catch(Exception exception){
			logger.error("Error while getting customer mobile number.", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return mobileNo;
	
	}
	
	@Override
	public List<TrimmedCustomer> getCustomersNotAssignedToAnyBeat(int resellerID){
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, NAME FROM CUSTOMER WHERE ID NOT IN (SELECT CUSTOMER_ID FROM BEAT_CUSTOMER) AND RESELLER_ID= ? ");
			query.setParameter( 0, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer not assigned to any beat.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}
	
	@Override
	public List<TrimmedCustomer> getCustomersBeatAssignmentForEdit(int beatID, int resellerID){
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, NAME FROM CUSTOMER WHERE ID NOT IN (SELECT CUSTOMER_ID FROM BEAT_CUSTOMER WHERE BEAT_ID != ? ) AND RESELLER_ID= ? ");
			query.setParameter( 0, beatID);
			query.setParameter(1, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer for beat-customer edit.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}

	@Override
	public List<TrimmedCustomer> getCustomersToSchedule(int beatID, Date visitDate) {
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, NAME FROM CUSTOMER  WHERE ID IN (SELECT CUSTOMER_ID FROM BEAT_CUSTOMER WHERE BEAT_ID = ? AND CUSTOMER_ID NOT IN (SELECT a.CUSTOMER_ID FROM ORDER_BOOKING_SCHEDULE_CUSTOMERS a, ORDER_BOOKING_SCHEDULE b where a.ORDER_BOOKING_SCHEDULE_ID = b.ID AND b.VISIT_DATE= ?))");
			query.setParameter( 0, beatID);
			query.setParameter(1, visitDate);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer for beat-customer edit.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}
	
	@Override
	public List<CustomerOrder> getCustomersToScheduleDelivery(int beatID, Date visitDate, int resellerID) {
		Session session = null;
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>(); 
		Map<TrimmedCustomer, List<Order>> custOrdersMap = new HashMap<TrimmedCustomer, List<Order>>();
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT c.ID CUST_ID, c.NAME CUST_NAME, b.* FROM ORDER_BOOKING_SCHEDULE a, ORDER_DETAILS b, CUSTOMER c, ORDER_BOOKING_SCHEDULE_CUSTOMERS d  WHERE a.ID=b.ORDER_BOOKING_ID AND d.CUSTOMER_ID=c.ID AND a.ID=d.ORDER_BOOKING_SCHEDULE_ID  AND a.VISIT_DATE < ? AND a.BEAT_ID= ? AND b.STATUS IN (2, 5) AND b.RESELLER_ID = ?");
			query.setParameter( 0, visitDate);
			query.setParameter(1, beatID);
			query.setParameter(2, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				
				Order order = new Order();
				order.setCustomerID(trimmedCustomer.getCustomerID());
				order.setOrderID(Integer.valueOf(String.valueOf(objs[2])));
				order.setOrderBookingID(Integer.valueOf(String.valueOf(objs[3])));
				order.setNoOfLineItems(Integer.valueOf(String.valueOf(objs[4])));
				order.setBookValue(Double.valueOf(String.valueOf(objs[5])));
				order.setRemark(String.valueOf(objs[6]));
				order.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				order.setResellerID(Integer.valueOf(String.valueOf(objs[8])));
				if(objs[10] != null){
					order.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[10])).getTime()));
				}
				
				if(!custOrdersMap.containsKey(trimmedCustomer)){
					custOrdersMap.put(trimmedCustomer, new ArrayList<Order>());
				}
				custOrdersMap.get(trimmedCustomer).add(order);
			}
			
			for(Map.Entry<TrimmedCustomer, List<Order>> entry : custOrdersMap.entrySet()){
				CustomerOrder customerOrder = new CustomerOrder();
				customerOrder.setCustomer(entry.getKey());
				customerOrder.setOrders(entry.getValue());
				customerOrders.add(customerOrder);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer for delivery schedule.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customerOrders;
	}

	@Override
	public List<TrimmedCustomer> scheduledTrimmedCustomerslistForDeliveryToday(int delivExecID, Date visitDate) {
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.NAME FROM CUSTOMER a, DELIVERY_SCHEDULE b, ORDER_DETAILS c WHERE a.ID=b.CUSTOMER_ID AND b.ORDER_ID = c.ID AND b.DELIVERY_EXEC_ID= ? AND b.VISIT_DATE= ? AND c.STATUS = 3 GROUP BY a.ID");
			query.setParameter( 0, delivExecID);
			query.setDate(1, visitDate);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer for beat-customer edit.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}

	@Override
	public List<TrimmedCustomer> getCustomerForOTPVerification(int userID, int otpType){
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>(); 
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.NAME, c.ID order_booking_id FROM CUSTOMER a, CUSTOMER_OTP b, ORDER_BOOKING_SCHEDULE c, ORDER_BOOKING_SCHEDULE_CUSTOMERS d WHERE a.ID=b.CUSTOMER_ID AND a.ID=d.CUSTOMER_ID AND c.ID=d.ORDER_BOOKING_SCHEDULE_ID AND b.FIELD_EXEC_ID = ? AND b.OTP_TYPE= ? AND b.SUBMITTED_OTP IS NULL AND DATE(b.GENERATED_DATE_TIME) = CURDATE() AND c.VISIT_DATE = CURDATE()");
			query.setParameter( 0, userID);
			query.setParameter(1, otpType);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				trimmedCustomer.setOrderBookingID(Integer.valueOf(String.valueOf(objs[2])));
				customers.add(trimmedCustomer);
			}
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer for otp registration.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}

	@Override
	public void createCustomers(List<Customer> customers) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			int beatID = -1;
			for(Customer customer : customers){
				//check for beat
				if(customer.getBeatName() != null && !customer.getBeatName().isEmpty()){
					SQLQuery getBeatQry = session.createSQLQuery(" SELECT ID FROM BEAT WHERE LOWER(NAME) = ? AND RESELLER_ID = ?");
					getBeatQry.setParameter( 0, customer.getBeatName().toLowerCase() );
					getBeatQry.setParameter(1, customer.getResellerID());
					List results = getBeatQry.list();
					if(results.isEmpty()){
						//create beat
						Beat beat = new Beat();
						beat.setResellerID(customer.getResellerID());
						beat.setName(customer.getBeatName());
						beat.setDescription(customer.getBeatName());
						beat.setDateCreated(new Date());
						session.save(beat);
						beatID = beat.getBeatID();
					}else{
						for(Object obj : results){
							beatID = Integer.valueOf(String.valueOf(obj));
						}
					}
				}
				//Save Customer
				session.save(customer);
				//Create customer-Beat link
				SQLQuery beatCustInsert = session.createSQLQuery("INSERT INTO BEAT_CUSTOMER VALUES (?, ?)");
				beatCustInsert.setParameter(0, beatID);
				beatCustInsert.setParameter(1, customer.getCustomerID());
				beatCustInsert.executeUpdate();
			}
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while bulk load of customers.", exception);
			transaction.rollback();
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	@Override
	public List<Customer> search(int resellerID, Map<String, Object> filterCriteria)throws Exception{
		Session session = null;
		List<Customer> customers = new ArrayList<Customer>();
		try{
			session = sessionFactory.openSession();
			StringBuilder queryBuilder = new StringBuilder(
					"SELECT * FROM (SELECT a.ID, a.NAME, a.DESCRIPTION, X.NAME BEAT_NAME, b.CITY, b.CONTACT_PERSON, b.PHONE_NO FROM ADDRESS b, CUSTOMER_ADDRESS c, CUSTOMER a LEFT JOIN (SELECT e.BEAT_ID, e.CUSTOMER_ID, d.NAME FROM BEAT_CUSTOMER e, BEAT d where d.ID=e.BEAT_ID) X on a.ID=X.CUSTOMER_ID WHERE c.ADDRESS_ID=b.ID AND b.ADDRESS_TYPE=1 AND a.ID=c.CUSTOMER_ID AND a.RESELLER_ID ="+ resellerID +") XYZ");
			if(filterCriteria != null && filterCriteria.size() > 0){
				queryBuilder.append(" WHERE ");
				int index = 0;
				for(Map.Entry<String, Object> entry : filterCriteria.entrySet()){
					if(index != 0){
						queryBuilder.append(" AND ");
					}
					String searchParam = entry.getKey();
					Object searchVal = entry.getValue();
					if(!searchParam.equals("ID")){
						queryBuilder.append(searchParam+" LIKE '%"+searchVal+"%'");
					}else {
						queryBuilder.append(searchParam+" = "+searchVal);
					}
				}
			}
			logger.debug(" search sql "+ queryBuilder.toString());
			SQLQuery query = session.createSQLQuery(queryBuilder.toString());
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				Customer customer = new Customer();
				customer.setCustomerID(Integer.parseInt(String.valueOf(objs[0])));
				customer.setName(String.valueOf(objs[1]) != null ? String.valueOf(objs[1]) : "");
				customer.setDescription(String.valueOf(objs[2]) != null ? String.valueOf(objs[2]) : "");
				customer.setBeatName((String.valueOf(objs[3]) == null || String.valueOf(objs[3]).equals("null")) ? "" : String.valueOf(objs[3]) );
				Address mainAdd = new Address();
				mainAdd.setCity(String.valueOf(objs[4]) != null ? String.valueOf(objs[4]) : "");
				mainAdd.setContactPerson(String.valueOf(objs[5]) != null ? String.valueOf(objs[5]) : "");
				mainAdd.setPhoneNumber(String.valueOf(objs[6]) != null ? String.valueOf(objs[6]) : "");
				List<Address> addressList = new ArrayList<Address>();
				addressList.add(mainAdd);
				customer.setAddress(addressList);
				customers.add(customer);
			}
		}catch(Exception exception){
			logger.error("Error while searching customers.", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return customers;
	}
	
	@Override
	public int getCustomersCount(int resellerID){
		Session session = null;
		int counts = 0;
		try{
			session = sessionFactory.openSession();
			SQLQuery count = session.createSQLQuery("SELECT COUNT(*) FROM CUSTOMER WHERE RESELLER_ID= ?");
			count.setParameter(0, resellerID);
			List results = count.list();
			if(results != null && results.size() == 1 ){
				counts = ((BigInteger)results.get(0)).intValue();
			}
		}catch(Exception exception){
			logger.error("Error while fetching number of customers.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return counts;
	}
	
}
