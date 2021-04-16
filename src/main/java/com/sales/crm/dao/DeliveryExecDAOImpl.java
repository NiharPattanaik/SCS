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
import com.sales.crm.model.EntityStatusEnum;
import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.TrimmedCustomer;

@Repository("deliveryDAO")
public class DeliveryExecDAOImpl implements DeliveryExecDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(DeliveryExecDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	
	
	@Override
	public DeliveryExecutive getDelivExecutive(int delivExecID, int tenantID) {
		Session session = null;
		DeliveryExecutive delivExec = null;
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, USER_NAME, DESCRIPTION, EMAIL_ID, MOBILE_NO, FIRST_NAME, LAST_NAME, STATUS_ID, DATE_CREATED, DATE_MODIFIED FROM USER WHERE ID=? AND TENANT_ID = ?");
			query.setParameter(0, delivExecID);
			query.setParameter(1, tenantID);
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
				delivExec.setStatusID(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					delivExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					delivExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				delivExec.setTenantID(tenantID);
			}
			
			//Get Beats
			SQLQuery getBeats = session.createSQLQuery("SELECT a.* FROM BEAT a, DELIVERY_EXEC_BEATS b where a.ID=b.BEAT_ID AND a.TENANT_ID=b.TENANT_ID AND b.DELIV_EXEC_ID=? AND a.TENANT_ID = ?");
			getBeats.setParameter(0, delivExecID);
			getBeats.setParameter(1, tenantID);
			List beats = getBeats.list();
			List<Beat> beatList = new ArrayList<Beat>();
			List<Integer> beatIDsList = new ArrayList<Integer>();
			for(Object obj : beats){
				Object[] objs = (Object[])obj;
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[0])));
				beat.setCode(String.valueOf(objs[1]));
				beat.setName(String.valueOf(objs[2]));
				beat.setDescription(String.valueOf(objs[3]));
				beat.setCoverageSchedule(String.valueOf(objs[4]));
				beat.setDistance(Integer.valueOf(String.valueOf(objs[5])));
				beat.setStatusID(Integer.valueOf(String.valueOf(objs[6])));
				beat.setTenantID(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					beat.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				if(objs[9] != null){
					beat.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
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
	public List<DeliveryExecutive> getDelivExecutivesHavingBeatsAssigned(int tenantID) {
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
				delivExec.setCode(String.valueOf(objs[1]));
				delivExec.setUserName(String.valueOf(objs[2]));
				delivExec.setDescription(String.valueOf(objs[4]));
				delivExec.setEmailID(String.valueOf(objs[5]));
				delivExec.setMobileNo(String.valueOf(objs[6]));
				delivExec.setFirstName(String.valueOf(objs[7]));
				delivExec.setLastName(String.valueOf(objs[8]));
				delivExec.setStatusID(Integer.valueOf(String.valueOf(objs[9])));
				delivExec.setLoggedIn(Integer.valueOf(String.valueOf(objs[10])));
				delivExec.setTenantID(Integer.valueOf(String.valueOf(objs[11])));
				if(objs[12] != null){
					delivExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[12])).getTime()));
				}
				
				if(objs[13] != null){
					delivExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[13])).getTime()));
				}
				delivExec.setTenantID(tenantID);
				
				if(!delivExecsMap.containsKey(delivExec.getUserID())){
					delivExecsMap.put(delivExec.getUserID(), delivExec);
				}
				
				//Add Beats
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[14])));
				beat.setName(String.valueOf(objs[15]));
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
	public List<DeliveryExecutive> getDeliveryExecutives(int tenantID){
		Session session = null;
		Map<Integer, DeliveryExecutive> delivExecs = new HashMap<Integer, DeliveryExecutive>(); 
		try{
			Set<Integer> userIDs = new HashSet<Integer>();
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.USER_NAME, a.DESCRIPTION, a.EMAIL_ID, a.MOBILE_NO, a.FIRST_NAME, a.LAST_NAME, a.STATUS_ID, a.DATE_CREATED, a.DATE_MODIFIED, a.TENANT_ID FROM USER a, USER_ROLE b, TENANT_USER c WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND b.ROLE_ID= 3 AND c.TENANT_ID=?");
			query.setParameter(0, tenantID);
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
				delivExec.setStatusID(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					delivExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					delivExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				delivExec.setTenantID(Integer.valueOf(String.valueOf(objs[10])));
				delivExecs.put(delivExec.getUserID(), delivExec);
				//add user id to set
				userIDs.add(delivExec.getUserID());
			}
			
			//Get Beats
			if(userIDs.size() > 0){
				SQLQuery getBeats = session.createSQLQuery("SELECT b.DELIV_EXEC_ID, a.* FROM BEAT a, DELIVERY_EXEC_BEATS b where a.ID=b.BEAT_ID AND a.TENANT_ID=b.TENANT_ID AND b.DELIV_EXEC_ID in (" +StringUtils.join(userIDs, ",") +") AND a.TENANT_ID = ?");
				getBeats.setParameter(0, tenantID);
				List beats = getBeats.list();
				for(Object obj : beats){
					Object[] objs = (Object[])obj;
					int delivExeID = Integer.valueOf(String.valueOf(objs[0]));
					Beat beat = new Beat();
					beat.setBeatID(Integer.valueOf(String.valueOf(objs[1])));
					beat.setCode(String.valueOf(objs[2]));
					beat.setName(String.valueOf(objs[3]));
					beat.setDescription(String.valueOf(objs[4]));
					beat.setCoverageSchedule(String.valueOf(objs[5]));
					beat.setDistance(Integer.valueOf(String.valueOf(objs[6])));
					beat.setStatusID(Integer.valueOf(String.valueOf(objs[7])));
					beat.setTenantID(Integer.valueOf(String.valueOf(objs[8])));
					if(objs[9] != null){
						beat.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
					}
					if(objs[10] != null){
						beat.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[10])).getTime()));
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
	public void assignBeats(final int tenantID, final int delivExecID, final List<Integer> beatIDs) throws Exception{
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
						String sqlInsert = "INSERT INTO DELIVERY_EXEC_BEATS (DELIV_EXEC_ID, BEAT_ID, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, CURDATE())";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < beatIDs.size(); i++) {
							pstmt.setInt(1, delivExecID);
							pstmt.setInt(2, beatIDs.get(i));
							pstmt.setInt(3, tenantID);
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
	public void updateAssignedBeats(final int tenantID, final int delivExecID, final List<Integer> beatIDs) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Delete existing delivery Exec Beats
			SQLQuery deleteSalesExecBeats = session.createSQLQuery("DELETE FROM DELIVERY_EXEC_BEATS WHERE DELIV_EXEC_ID =? AND TENANT_ID = ?");
			deleteSalesExecBeats.setParameter(0, delivExecID);
			deleteSalesExecBeats.setParameter(1, tenantID);
			deleteSalesExecBeats.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO DELIVERY_EXEC_BEATS (DELIV_EXEC_ID, BEAT_ID, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, CURDATE())";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < beatIDs.size(); i++) {
							pstmt.setInt(1, delivExecID);
							pstmt.setInt(2, beatIDs.get(i));
							pstmt.setInt(3, tenantID);
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
	public void deleteBeatAssignment(int delivExecID, int tenatID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("DELETE FROM DELIVERY_EXEC_BEATS WHERE DELIV_EXEC_ID= ? AND TENANT_ID = ?");
			query.setParameter(0, delivExecID);
			query.setParameter(1, tenatID);
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
	public List<DeliveryExecutive> getDeliveryExecutivesScheduled(int tenantID) {
		Session session = null;
		List<DeliveryExecutive> delivExecList = new ArrayList<DeliveryExecutive>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, BEAT b, CUSTOMER c, DELIVERY_SCHEDULE d, TENANT_USER e  WHERE a.ID=d.DELIVERY_EXEC_ID AND b.ID=d.BEAT_ID AND c.ID=d.CUSTOMER_ID AND e.USER_ID=a.ID AND e.TENANT_ID=? group by a.ID");
			query.setParameter(0, tenantID);
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
	public List<Beat> getAssignedBeats(int delivExecID, int tenantID) {
		Session session = null;
		List<Beat> beats = new ArrayList<Beat>();
		try {
			session = sessionFactory.openSession();
			SQLQuery beatsQuery = session.createSQLQuery("SELECT a.ID, a.NAME, a.DESCRIPTION, a.COVERAGE_SCHEDULE, a.DISTANCE, a.DATE_CREATED, a.DATE_MODIFIED FROM BEAT a, DELIVERY_EXEC_BEATS b WHERE a.ID=b.BEAT_ID AND a.TENANT_ID=b.TENANT_ID AND DELIV_EXEC_ID=? AND a.TENANT_ID = ?");
			beatsQuery.setParameter(0, delivExecID);
			beatsQuery.setParameter(1, tenantID);
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
	public List<Beat> getScheduledVisitDelivExecBeats(int delivExecID, Date visitDate, int tenantID) {
		Session session = null;
		List<Beat> beats = new ArrayList<Beat>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT b.ID, b.NAME FROM USER a, BEAT b, DELIVERY_SCHEDULE c  WHERE a.ID=c.DELIVERY_EXEC_ID AND b.ID=c.BEAT_ID AND a.TENANT_ID=b.TENANT_ID AND a.ID=? AND c.VISIT_DATE = ? AND a.TENANT_ID = ? group by b.ID");
			query.setParameter(0, delivExecID);
			query.setParameter(1, visitDate);
			query.setParameter(2, tenantID);
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
	public List<DeliveryExecutive> getScheduledVisitDelivExecs(Date visitDate, int tenantID) {
		Session session = null;
		List<DeliveryExecutive> delivExecs = new ArrayList<DeliveryExecutive>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, DELIVERY_SCHEDULE b  WHERE a.ID=b.DELIVERY_EXEC_ID AND a.TENANT_ID=b.TENANT_ID AND b.VISIT_DATE = ? AND b.TENANT_ID = ? group by a.ID");
			query.setParameter(0, visitDate);
			query.setParameter(1, tenantID);
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
	public List<CustomerOrder> getScheduledCustomersOrdersForDelivery(int delivExecID, Date visitDate, int beatID, int tenantID){
		Session session = null;
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>(); 
		Map<TrimmedCustomer, List<Order>> customerOrderMap = new HashMap<TrimmedCustomer, List<Order>>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID CUST_ID, a.NAME, c.* FROM CUSTOMER a, DELIVERY_SCHEDULE b, ORDER_DETAILS c  WHERE a.ID=b.CUSTOMER_ID AND b.ORDER_ID=c.ID AND a.TENANT_ID=b.TENANT_ID AND b.TENANT_ID=c.TENANT_ID AND  b.DELIVERY_EXEC_ID= ? AND b.VISIT_DATE= ? AND b.BEAT_ID= ? AND a.TENANT_ID = ?");
			query.setParameter(0, delivExecID);
			query.setParameter(1, visitDate);
			query.setParameter(2, beatID);
			query.setParameter(3, tenantID);
			List lists = query.list();
			for(Object obj : lists){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				
				Order order = new Order();
				order.setOrderID(Integer.valueOf(String.valueOf(objs[2])));
				order.setCode(String.valueOf(objs[3]));
				order.setOrderBookingID(Integer.valueOf(String.valueOf(objs[4])));
				order.setNoOfLineItems(Integer.valueOf(String.valueOf(objs[5])));
				order.setBookValue(Double.valueOf(String.valueOf(objs[6])));
				order.setRemark(String.valueOf(objs[7]));
				order.setStatusID(Integer.valueOf(String.valueOf(objs[8])));
				order.setCustomerID(Integer.valueOf(String.valueOf(objs[9])));
				order.setTenantID(Integer.valueOf(String.valueOf(objs[10])));
				if(objs[11] != null){
					order.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[11])).getTime()));
				}
				
				if(objs[12] != null){
					order.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[12])).getTime()));
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
					"SELECT a.NAME FROM CUSTOMER a, DELIVERY_SCHEDULE b WHERE a.ID=b.CUSTOMER_ID AND AND a.TENANT_ID=b.TENANT_ID b.DELIVERY_EXEC_ID=? AND b.BEAT_ID=? AND b.VISIT_DATE = ? AND a.TENANT_ID= ? AND b.CUSTOMER_ID IN ("+ StringUtils.join(deliveryBookingSchedule.getCustomerIDs(), ",")+") group by a.NAME");
			query.setParameter(0, deliveryBookingSchedule.getDelivExecutiveID());
			query.setParameter(1, deliveryBookingSchedule.getBeatID());
			query.setParameter(2, deliveryBookingSchedule.getVisitDate());
			query.setParameter(3, deliveryBookingSchedule.getTenantID());
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
						String delivSchInsert = "INSERT INTO DELIVERY_SCHEDULE (ORDER_ID, DELIVERY_EXEC_ID, BEAT_ID, CUSTOMER_ID, VISIT_DATE, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, ?, ?, ?, CURDATE())";
						String orderUpdate = "UPDATE ORDER_DETAILS SET STATUS_ID= ?, DATE_MODIFIED = ? WHERE ID= ?";
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
								delivpstmt.setInt(6, deliveryBookingSchedule.getTenantID());
								delivpstmt.addBatch();
								
								//Order Update
								updateOrderpstmt.setInt(1, EntityStatusEnum.DELIVERY_SCHEDULED.getEntityStatus());
								updateOrderpstmt.setDate(2, new java.sql.Date(new Date().getTime()));
								updateOrderpstmt.setInt(3, entry.getValue().get(i));
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
	public void unscheduleDeliveryBooking(List<Integer> customerIDs, Date visitDate, int tenantID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			SQLQuery deleteSchQuery = session.createSQLQuery(
					"DELETE FROM DELIVERY_SCHEDULE WHERE CUSTOMER_ID IN (" +StringUtils.join(customerIDs, ",") +") AND VISIT_DATE = ? AND TENANT_ID = ?");
			deleteSchQuery.setParameter(0, visitDate);
			deleteSchQuery.setParameter(1, tenantID);
			SQLQuery updateOrderQuery = session.createSQLQuery("UPDATE ORDER_DETAILS SET STATUS_ID = ? WHERE ORDER_BOOKING_ID IN (SELECT a.ID FROM ORDER_BOOKING_SCHEDULE a, ORDER_BOOKING_SCHEDULE_CUSTOMERS b WHERE a.ID=b.ORDER_BOOKING_SCHEDULE_ID AND b.CUSTOMER_ID IN (" +StringUtils.join(customerIDs, ",") +") ) AND STATUS_ID = ? AND TENANT_ID = ?");
			updateOrderQuery.setParameter(0, EntityStatusEnum.ORDER_CREATED.getEntityStatus());
			updateOrderQuery.setParameter(1, EntityStatusEnum.DELIVERY_SCHEDULED.getEntityStatus());
			updateOrderQuery.setParameter(2, tenantID);
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
	public int getDeliveryExecutiveCount(int tenantID){
		Session session = null;
		int counts = 0;
		try{
			session = sessionFactory.openSession();
			SQLQuery count = session.createSQLQuery("SELECT COUNT(*) FROM USER a, USER_ROLE b, TENANT_USER c WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND b.ROLE_ID= 3 AND c.TENANT_ID=?");
			count.setParameter(0, tenantID);
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


	@Override
	public List<DeliveryBookingSchedule> getAllDeliveryBookedForDate(int tenantID, Date date) {
		Session session = null;
		List<DeliveryBookingSchedule> deliverySchedules = new ArrayList<DeliveryBookingSchedule>();
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.id,\n" + 
					"       a.ORDER_ID,\n" + 
					"       a.DELIVERY_EXEC_ID, 	\n" + 
					"       a.CUSTOMER_ID,\n" + 
					"       a.BEAT_ID,\n" + 
					"       b.NAME CUST_NAME,\n" + 
					"       d.NAME BEAT_NAME,\n" + 
					"       c.first_name,\n" + 
					"       c.last_name\n" + 
					"FROM   DELIVERY_SCHEDULE a,\n" + 
					"       CUSTOMER b,\n" + 
					"       USER c,\n" + 
					"       BEAT d,\n" + 
					"       ORDER_DETAILS e\n" + 
					"WHERE  a.customer_id = b.id\n" + 
					"       AND a.BEAT_ID = d.ID\n" + 
					"       AND a.ORDER_ID = e.ID\n" + 
					"       AND a.DELIVERY_EXEC_ID = c.ID\n" + 
					"       AND a.tenant_id = b.tenant_id\n" + 
					"       AND a.tenant_id = c.tenant_id\n" + 
					"       AND a.tenant_id = d.tenant_id\n" + 
					"       AND a.tenant_id = e.tenant_id\n" + 
					"       AND a.tenant_id = ? \n" + 
					"       AND a.visit_date = ? \n" + 
					" ");
			query.setParameter(0, tenantID);
			query.setParameter(1, new java.sql.Date(date.getTime()));
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				DeliveryBookingSchedule deliverySchedule = new DeliveryBookingSchedule();
				deliverySchedule.setID(Integer.valueOf(String.valueOf(objs[0])));
			//	deliverySchedule.setOr
				
			}

		}catch(Exception exception){
			logger.error("Error while fetching scheduled order bookings for " + new SimpleDateFormat("dd-MM-yyyy").format(date) +".");
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return null;
	}
	
	

}
