package com.sales.crm.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Beat;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.DeliveryBookingSchedule;
import com.sales.crm.model.DeliveryExecutive;
import com.sales.crm.model.Order;
import com.sales.crm.model.TrimmedCustomer;

@Repository("deliveryDAO")
public class DeliveryExecDAOImpl implements DeliveryExecDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(DeliveryExecDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	
	
	@Override
	public DeliveryExecutive getDelivExecutive(int delivExecID) {
		Session session = null;
		DeliveryExecutive delivExec = null;
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, USER_NAME, DESCRIPTION, EMAIL_ID, MOBILE_NO, FIRST_NAME, LAST_NAME, STATUS, DATE_CREATED, DATE_MODIFIED, COMPANY_ID FROM USER WHERE ID=?");
			query.setParameter(0, delivExecID);
			List results = query.list();
			for(Object obj : results){
				delivExec = new DeliveryExecutive();
				Object[] objs = (Object[])obj;
				delivExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				delivExec.setUserName(String.valueOf(objs[1]));
				delivExec.setDescription(String.valueOf(objs[2]));
				delivExec.setEmailID(String.valueOf(objs[3]));
				delivExec.setMobileNo(String.valueOf(objs[4]));
				delivExec.setFirstName(String.valueOf(objs[5]));
				delivExec.setLastName(String.valueOf(objs[6]));
				delivExec.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					delivExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					delivExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				delivExec.setCompanyID(Integer.valueOf(String.valueOf(objs[10])));
			}
			
			//Get Beats
			SQLQuery getBeats = session.createSQLQuery("SELECT b.DELIV_EXEC_ID, a.* FROM BEAT a, DELIVERY_EXEC_BEATS b where a.ID=b.BEAT_ID AND b.DELIV_EXEC_ID=? ");
			getBeats.setParameter(0, delivExecID);
			List beats = getBeats.list();
			List<Beat> beatList = new ArrayList<Beat>();
			List<Integer> beatIDsList = new ArrayList<Integer>();
			for(Object obj : beats){
				Object[] objs = (Object[])obj;
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[1])));
				beat.setResellerID(Integer.valueOf(String.valueOf(objs[2])));
				beat.setName(String.valueOf(objs[3]));
				beat.setDescription(String.valueOf(objs[4]));
				beat.setCoverageSchedule(String.valueOf(objs[5]));
				beat.setDistance(Integer.valueOf(String.valueOf(objs[6])));
				if(objs[7] != null){
					beat.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[7])).getTime()));
				}
				if(objs[8] != null){
					beat.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				beatList.add(beat);
				beatIDsList.add(beat.getBeatID());
			}
			delivExec.setBeats(beatList);
			delivExec.setBeatIDLists(beatIDsList);
		}catch(Exception exception){
			logger.error("Error while fetching delivery executive details.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return delivExec;
	}
	
	
	@Override
	public List<DeliveryExecutive> getDelivExecutivesHavingBeatsAssigned(int resellerID) {
		Session session = null;
		Map<Integer, DeliveryExecutive> delivExecsMap = new HashMap<Integer, DeliveryExecutive>(); 
		try{
			Set<Integer> userIDs = new HashSet<Integer>();
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.*, b.ID BEAT_ID, b.NAME BEAT_NAME FROM USER a, BEAT b, DELIVERY_EXEC_BEATS c WHERE a.ID=c.DELIV_EXEC_ID AND b.ID=c.BEAT_ID;");
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				DeliveryExecutive delivExec = new DeliveryExecutive();
				delivExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				delivExec.setUserName(String.valueOf(objs[1]));
				delivExec.setDescription(String.valueOf(objs[3]));
				delivExec.setEmailID(String.valueOf(objs[4]));
				delivExec.setMobileNo(String.valueOf(objs[5]));
				delivExec.setFirstName(String.valueOf(objs[6]));
				delivExec.setLastName(String.valueOf(objs[7]));
				delivExec.setStatus(Integer.valueOf(String.valueOf(objs[8])));
				if(objs[9] != null){
					delivExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				
				if(objs[10] != null){
					delivExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[10])).getTime()));
				}
				delivExec.setCompanyID(Integer.valueOf(String.valueOf(objs[11])));
				delivExec.setResellerID(resellerID);
				
				if(!delivExecsMap.containsKey(delivExec.getUserID())){
					delivExecsMap.put(delivExec.getUserID(), delivExec);
				}
				
				//Add Beats
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[13])));
				beat.setName(String.valueOf(objs[14]));
				if(delivExecsMap.get(delivExec.getUserID()).getBeats() == null){
					delivExecsMap.get(delivExec.getUserID()).setBeats(new ArrayList<Beat>());
				}
				delivExecsMap.get(delivExec.getUserID()).getBeats().add(beat);
			}
		}catch(Exception exception){
			logger.error("Error while fetching list of delivery executives", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<DeliveryExecutive>(delivExecsMap.values());
	}
	
	
	@Override
	public List<DeliveryExecutive> getDeliveryExecutives(int resellerID){
		Session session = null;
		Map<Integer, DeliveryExecutive> delivExecs = new HashMap<Integer, DeliveryExecutive>(); 
		try{
			Set<Integer> userIDs = new HashSet<Integer>();
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.USER_NAME, a.DESCRIPTION, a.EMAIL_ID, a.MOBILE_NO, a.FIRST_NAME, a.LAST_NAME, a.STATUS, a.DATE_CREATED, a.DATE_MODIFIED, a.COMPANY_ID FROM USER a, USER_ROLE b, RESELLER_USER c WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND b.ROLE_ID= 3 AND c.RESELLER_ID=?");
			query.setParameter(0, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				DeliveryExecutive delivExec = new DeliveryExecutive();
				delivExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				delivExec.setUserName(String.valueOf(objs[1]));
				delivExec.setDescription(String.valueOf(objs[2]));
				delivExec.setEmailID(String.valueOf(objs[3]));
				delivExec.setMobileNo(String.valueOf(objs[4]));
				delivExec.setFirstName(String.valueOf(objs[5]));
				delivExec.setLastName(String.valueOf(objs[6]));
				delivExec.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					delivExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					delivExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				delivExec.setCompanyID(Integer.valueOf(String.valueOf(objs[10])));
				delivExec.setResellerID(resellerID);
				delivExecs.put(delivExec.getUserID(), delivExec);
				//add user id to set
				userIDs.add(delivExec.getUserID());
			}
			
			//Get Beats
			if(userIDs.size() > 0){
				SQLQuery getBeats = session.createSQLQuery("SELECT b.DELIV_EXEC_ID, a.* FROM BEAT a, DELIVERY_EXEC_BEATS b where a.ID=b.BEAT_ID AND b.DELIV_EXEC_ID in (" +StringUtils.join(userIDs, ",") +")");
				//getRoles.setParameter(0, userIDs);
				List beats = getBeats.list();
				for(Object obj : beats){
					Object[] objs = (Object[])obj;
					int delivExeID = Integer.valueOf(String.valueOf(objs[0]));
					Beat beat = new Beat();
					beat.setBeatID(Integer.valueOf(String.valueOf(objs[1])));
					beat.setResellerID(Integer.valueOf(String.valueOf(objs[2])));
					beat.setName(String.valueOf(objs[3]));
					beat.setDescription(String.valueOf(objs[4]));
					beat.setCoverageSchedule(String.valueOf(objs[5]));
					beat.setDistance(Integer.valueOf(String.valueOf(objs[6])));
					if(objs[7] != null){
						beat.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[7])).getTime()));
					}
					if(objs[8] != null){
						beat.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
					}
					//Sets Beats list
					if(delivExecs.get(delivExeID).getBeats() == null){
						delivExecs.get(delivExeID).setBeats( new ArrayList<Beat>());
					}
					delivExecs.get(delivExeID).getBeats().add(beat);
					
					//Set Beat Ids
					if(delivExecs.get(delivExeID).getBeatIDLists() == null){
						delivExecs.get(delivExeID).setBeatIDLists(new ArrayList<Integer>());
					}
					delivExecs.get(delivExeID).getBeatIDLists().add(beat.getBeatID());
				}
			}
			
		}catch(Exception exception){
			logger.error("Error while fetching list of delivery executives", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<DeliveryExecutive>(delivExecs.values());
	}
	
	
	@Override
	public void assignBeats(final int delivExecID, final List<Integer> beatIDs) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO DELIVERY_EXEC_BEATS (DELIV_EXEC_ID, BEAT_ID) VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < beatIDs.size(); i++) {
							pstmt.setInt(1, delivExecID);
							pstmt.setInt(2, beatIDs.get(i));
							pstmt.addBatch();
						}
						pstmt.executeBatch();
					} finally {
						pstmt.close();
					}
				}
			});
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while assigning beats.", exception);
			if(transaction != null){
				transaction.rollback();
			}
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public void updateAssignedBeats(final int delivExecID, final List<Integer> beatIDs) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Delete existing delivery Exec Beats
			SQLQuery deleteSalesExecBeats = session.createSQLQuery("DELETE FROM DELIVERY_EXEC_BEATS WHERE DELIV_EXEC_ID =? ");
			deleteSalesExecBeats.setParameter(0, delivExecID);
			deleteSalesExecBeats.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO DELIVERY_EXEC_BEATS (DELIV_EXEC_ID, BEAT_ID) VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < beatIDs.size(); i++) {
							pstmt.setInt(1, delivExecID);
							pstmt.setInt(2, beatIDs.get(i));
							pstmt.addBatch();
						}
						pstmt.executeBatch();
					} finally {
						pstmt.close();
					}
				}
			});
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while updating assigned beats", exception);
			if(transaction != null){
				transaction.rollback();
			}
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public void deleteBeatAssignment(int delivExecID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("DELETE FROM DELIVERY_EXEC_BEATS WHERE DELIV_EXEC_ID= ?");
			query.setParameter(0, delivExecID);
			query.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Delivery Executive beats could not be successfully removed", exception);
			if(transaction != null){
				transaction.rollback();
			}
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	@Override
	public List<DeliveryExecutive> getDeliveryExecutivesScheduled(int resellerID) {
		Session session = null;
		List<DeliveryExecutive> delivExecList = new ArrayList<DeliveryExecutive>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, BEAT b, CUSTOMER c, DELIVERY_SCHEDULE d, RESELLER_USER e  WHERE a.ID=d.DELIVERY_EXEC_ID AND b.ID=d.BEAT_ID AND c.ID=d.CUSTOMER_ID AND e.USER_ID=a.ID AND e.RESELLER_ID=? group by a.ID");
			query.setParameter(0, resellerID);
			List delivExecs = query.list();
			for(Object obj : delivExecs){
				Object[] objs = (Object[])obj;
				DeliveryExecutive delivExecutive = new DeliveryExecutive();
				delivExecutive.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				delivExecutive.setFirstName(String.valueOf(objs[1]));
				delivExecutive.setLastName(String.valueOf(objs[2]));
				delivExecList.add(delivExecutive);
			}
			return delivExecList;
		} catch (Exception exception) {
			logger.error("Error fetching delivery executives mapped to beat and customer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	
	@Override
	public List<Beat> getAssignedBeats(int delivExecID) {
		Session session = null;
		List<Beat> beats = new ArrayList<Beat>();
		try {
			session = sessionFactory.openSession();
			SQLQuery beatsQuery = session.createSQLQuery("SELECT a.ID, a.NAME, a.DESCRIPTION, a.COVERAGE_SCHEDULE, a.DISTANCE, a.DATE_CREATED, a.DATE_MODIFIED FROM BEAT a, DELIVERY_EXEC_BEATS b WHERE a.ID=b.BEAT_ID AND DELIV_EXEC_ID=?");
			beatsQuery.setParameter(0, delivExecID);
			List beatsList = beatsQuery.list();
			for(Object obj : beatsList){
				Object[] objs = (Object[])obj;
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[0])));
				beat.setName(String.valueOf(objs[1]));
				beat.setDescription(String.valueOf(objs[2]));
				beat.setCoverageSchedule(String.valueOf(objs[3]));
				beat.setDistance(Integer.valueOf(String.valueOf(objs[4])));
				if(objs[5] != null){
					beat.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[5])).getTime()));
				}
				if(objs[6] != null){
					beat.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[6])).getTime()));
				}
				beats.add(beat);
			}
			return beats;
		} catch (Exception exception) {
			logger.error("Error while fetching assigned beats.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	@Override
	public List<Beat> getScheduledVisitDelivExecBeats(int delivExecID, Date visitDate) {
		Session session = null;
		List<Beat> beats = new ArrayList<Beat>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT b.ID, b.NAME FROM USER a, BEAT b, DELIVERY_SCHEDULE c  WHERE a.ID=c.DELIVERY_EXEC_ID AND b.ID=c.BEAT_ID AND a.ID=? AND c.VISIT_DATE = ? group by b.ID");
			query.setParameter(0, delivExecID);
			query.setParameter(1, visitDate);
			List lists = query.list();
			for(Object obj : lists){
				Object[] objs = (Object[])obj;
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[0])));
				beat.setName(String.valueOf(objs[1]));
				beats.add(beat);
			}
			return beats;
		} catch (Exception exception) {
			logger.error("Error fetching delivery executives mapped to beat and customer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	
	@Override
	public List<DeliveryExecutive> getScheduledVisitDelivExecs(Date visitDate, int resellerID) {
		Session session = null;
		List<DeliveryExecutive> delivExecs = new ArrayList<DeliveryExecutive>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, DELIVERY_SCHEDULE b  WHERE a.ID=b.DELIVERY_EXEC_ID AND b.VISIT_DATE = ? AND b.RESELLER_ID = ? group by a.ID");
			query.setParameter(0, visitDate);
			query.setParameter(1, resellerID);
			List lists = query.list();
			for(Object obj : lists){
				Object[] objs = (Object[])obj;
				DeliveryExecutive delivExecutive = new DeliveryExecutive();
				delivExecutive.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				delivExecutive.setFirstName(String.valueOf(objs[1]));
				delivExecutive.setLastName(String.valueOf(objs[2]));
				delivExecutive.setName(String.valueOf(objs[1]) +" "+ String.valueOf(objs[2]));
				delivExecs.add(delivExecutive);
			}
			return delivExecs;
		} catch (Exception exception) {
			logger.error("Error fetching delivery executives scheduled for visit.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	@Override
	public List<CustomerOrder> getScheduledCustomersOrdersForDelivery(int delivExecID, Date visitDate, int beatID){
		Session session = null;
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>(); 
		Map<TrimmedCustomer, List<Order>> customerOrderMap = new HashMap<TrimmedCustomer, List<Order>>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID CUST_ID, a.NAME, c.* FROM CUSTOMER a, DELIVERY_SCHEDULE b, ORDER_DETAILS c  WHERE a.ID=b.CUSTOMER_ID AND b.ORDER_ID=c.ID AND  b.DELIVERY_EXEC_ID= ? AND b.VISIT_DATE= ? AND b.BEAT_ID= ? ");
			query.setParameter(0, delivExecID);
			query.setParameter(1, visitDate);
			query.setParameter(2, beatID);
			List lists = query.list();
			for(Object obj : lists){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				
				Order order = new Order();
				order.setOrderID(Integer.valueOf(String.valueOf(objs[2])));
				order.setOrderBookingID(Integer.valueOf(String.valueOf(objs[3])));
				order.setNoOfLineItems(Integer.valueOf(String.valueOf(objs[4])));
				order.setBookValue(Double.valueOf(String.valueOf(objs[5])));
				order.setRemark(String.valueOf(objs[6]));
				order.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				order.setResellerID(Integer.valueOf(String.valueOf(objs[8])));
				if(objs[9] != null){
					order.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				
				if(!customerOrderMap.containsKey(trimmedCustomer)){
					customerOrderMap.put(trimmedCustomer, new ArrayList<Order>());
				}
				customerOrderMap.get(trimmedCustomer).add(order);
			}
			
			for(Map.Entry<TrimmedCustomer, List<Order>> entry : customerOrderMap.entrySet()){
				CustomerOrder customerOrder = new CustomerOrder();
				customerOrder.setCustomer(entry.getKey());
				customerOrder.setOrders(entry.getValue());
				customerOrders.add(customerOrder);
			}
			
		} catch (Exception exception) {
			logger.error("Error fetching customers schedukled for delivery.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return customerOrders;
	}
	
	@Override
	public List<String> alreadyDeliveryBookingScheduledCustomer(DeliveryBookingSchedule deliveryBookingSchedule) throws Exception{
		Session session = null;
		List<String> customerNames = new ArrayList<String>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.NAME FROM CUSTOMER a, DELIVERY_SCHEDULE b WHERE a.ID=b.CUSTOMER_ID AND b.DELIVERY_EXEC_ID=? AND b.BEAT_ID=? AND b.VISIT_DATE = ? AND b.CUSTOMER_ID IN ("+ StringUtils.join(deliveryBookingSchedule.getCustomerIDs(), ",")+") group by a.NAME");
			query.setParameter(0, deliveryBookingSchedule.getDelivExecutiveID());
			query.setParameter(1, deliveryBookingSchedule.getBeatID());
			query.setParameter(2, deliveryBookingSchedule.getVisitDate());
			List lists = query.list();
			for(Object obj : lists){
				customerNames.add(String.valueOf(obj));
			}
		} catch (Exception exception) {
			logger.error("Error fetching delivery executive names for scheduled delivery booking.", exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return customerNames;
	}
	
	@Override
	public void scheduleDeliveryBooking(final DeliveryBookingSchedule deliveryBookingSchedule) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement delivpstmt = null;
					PreparedStatement updateOrderpstmt = null;
					try {
						String delivSchInsert = "INSERT INTO DELIVERY_SCHEDULE (ORDER_ID, DELIVERY_EXEC_ID, BEAT_ID, CUSTOMER_ID, VISIT_DATE, RESELLER_ID) VALUES (?, ?, ?, ?, ?, ?)";
						String orderUpdate = "UPDATE ORDER_DETAILS SET STATUS= 3, DATE_MODIFIED = ? WHERE ID= ?";
						delivpstmt = connection.prepareStatement(delivSchInsert);
						updateOrderpstmt = connection.prepareStatement(orderUpdate);
						for(Map.Entry<Integer, List<Integer>> entry : deliveryBookingSchedule.getCustomerOrderMap().entrySet()){
							int customerID = entry.getKey();
							for (int i = 0; i < entry.getValue().size(); i++) {
								//Delivery Schedule
								delivpstmt.setInt(1, entry.getValue().get(i));
								delivpstmt.setInt(2, deliveryBookingSchedule.getDelivExecutiveID());
								delivpstmt.setInt(3, deliveryBookingSchedule.getBeatID());
								delivpstmt.setInt(4, customerID);
								delivpstmt.setDate(5, new java.sql.Date(deliveryBookingSchedule.getVisitDate().getTime()));
								delivpstmt.setInt(6, deliveryBookingSchedule.getResellerID());
								delivpstmt.addBatch();
								
								//Order Update
								updateOrderpstmt.setDate(1, new java.sql.Date(new Date().getTime()));
								updateOrderpstmt.setInt(2, entry.getValue().get(i));
								updateOrderpstmt.addBatch();
							}
						}
						delivpstmt.executeBatch();
						updateOrderpstmt.executeBatch();
					} finally {
						delivpstmt.close();
					}
				}
			});
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while scheduling delivery executives visti.", exception);
			if(transaction != null){
				transaction.rollback();
			}
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}


	@Override
	public void unscheduleDeliveryBooking(List<Integer> customerIDs, Date visitDate) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			SQLQuery deleteSchQuery = session.createSQLQuery(
					"DELETE FROM DELIVERY_SCHEDULE WHERE CUSTOMER_ID IN (" +StringUtils.join(customerIDs, ",") +") AND VISIT_DATE = ?");
			deleteSchQuery.setParameter(0, visitDate);
			SQLQuery updateOrderQuery = session.createSQLQuery("UPDATE ORDER_DETAILS SET STATUS = 2 WHERE ORDER_BOOKING_ID IN (SELECT a.ID FROM ORDER_BOOKING_SCHEDULE a, ORDER_BOOKING_SCHEDULE_CUSTOMERS b WHERE a.ID=b.ORDER_BOOKING_SCHEDULE_ID AND b.CUSTOMER_ID IN (" +StringUtils.join(customerIDs, ",") +") ) AND STATUS = 3");
			transaction = session.beginTransaction();
			deleteSchQuery.executeUpdate();
			updateOrderQuery.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			if(transaction != null){
				transaction.rollback();
			}
			logger.error("error while unscheduling delivery booking", exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
	}
	
	@Override
	public int getDeliveryExecutiveCount(int resellerID){
		Session session = null;
		int counts = 0;
		try{
			session = sessionFactory.openSession();
			SQLQuery count = session.createSQLQuery("SELECT COUNT(*) FROM USER a, USER_ROLE b, RESELLER_USER c WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND b.ROLE_ID= 3 AND c.RESELLER_ID=?");
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
