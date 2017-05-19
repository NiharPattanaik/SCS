package com.sales.crm.dao;

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
import com.sales.crm.model.OrderStatusEnum;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;

@Repository("salesExecDAO")
public class SalesExecDAOImpl implements SalesExecDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(SalesExecDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public SalesExecutive get(int salesExecID) {
		Session session = null;
		SalesExecutive salesExec = null;
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT ID, USER_NAME, DESCRIPTION, EMAIL_ID, MOBILE_NO, FIRST_NAME, LAST_NAME, STATUS, DATE_CREATED, DATE_MODIFIED, COMPANY_ID FROM USER WHERE ID=?");
			query.setParameter(0, salesExecID);
			List results = query.list();
			for(Object obj : results){
				salesExec = new SalesExecutive();
				Object[] objs = (Object[])obj;
				salesExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				salesExec.setUserName(String.valueOf(objs[1]));
				salesExec.setDescription(String.valueOf(objs[2]));
				salesExec.setEmailID(String.valueOf(objs[3]));
				salesExec.setMobileNo(String.valueOf(objs[4]));
				salesExec.setFirstName(String.valueOf(objs[5]));
				salesExec.setLastName(String.valueOf(objs[6]));
				salesExec.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					salesExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					salesExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				salesExec.setCompanyID(Integer.valueOf(String.valueOf(objs[10])));
			}
			
			//Get Beats
			SQLQuery getBeats = session.createSQLQuery("SELECT b.SALES_EXEC_ID, a.* FROM BEAT a, SALES_EXEC_BEATS b where a.ID=b.BEAT_ID AND b.SALES_EXEC_ID=? ");
			getBeats.setParameter(0, salesExecID);
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
			salesExec.setBeats(beatList);
			salesExec.setBeatIDLists(beatIDsList);
			
			//Get Customer IDs
			SQLQuery custQuery = session.createSQLQuery("SELECT CUSTOMER_ID FROM CUSTOMER_SALES_EXEC WHERE SALES_EXEC_ID= ? ");
			custQuery.setParameter(0, salesExecID);
			List<Integer> custIDs = custQuery.list();
			if(custIDs != null && custIDs.size() > 0){
				salesExec.setCustomerIDs(custIDs);
			}
		}catch(Exception exception){
			logger.error("Error while fetching sales executive details.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return salesExec;
	}

	@Override
	public List<SalesExecutive> getSalesExecutives(int resellerID){
		Session session = null;
		Map<Integer, SalesExecutive> salesExecs = new HashMap<Integer, SalesExecutive>(); 
		try{
			Set<Integer> userIDs = new HashSet<Integer>();
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.USER_NAME, a.DESCRIPTION, a.EMAIL_ID, a.MOBILE_NO, a.FIRST_NAME, a.LAST_NAME, a.STATUS, a.DATE_CREATED, a.DATE_MODIFIED, a.COMPANY_ID FROM USER a, USER_ROLE b, RESELLER_USER c WHERE a.ID=b.USER_ID AND a.ID=c.USER_ID AND b.ROLE_ID= ? AND c.RESELLER_ID=?");
			query.setParameter(0, 2);
			query.setParameter(1, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				SalesExecutive salesExec = new SalesExecutive();
				salesExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				salesExec.setUserName(String.valueOf(objs[1]));
				salesExec.setDescription(String.valueOf(objs[2]));
				salesExec.setEmailID(String.valueOf(objs[3]));
				salesExec.setMobileNo(String.valueOf(objs[4]));
				salesExec.setFirstName(String.valueOf(objs[5]));
				salesExec.setLastName(String.valueOf(objs[6]));
				salesExec.setStatus(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					salesExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
				}
				
				if(objs[9] != null){
					salesExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				salesExec.setCompanyID(Integer.valueOf(String.valueOf(objs[10])));
				salesExec.setResellerID(resellerID);
				salesExecs.put(salesExec.getUserID(), salesExec);
				//add user id to set
				userIDs.add(salesExec.getUserID());
			}
			
			//Get Beats
			if(userIDs.size() > 0){
				SQLQuery getBeats = session.createSQLQuery("SELECT b.SALES_EXEC_ID, a.* FROM BEAT a, SALES_EXEC_BEATS b where a.ID=b.BEAT_ID AND b.SALES_EXEC_ID in (" +StringUtils.join(userIDs, ",") +")");
				//getRoles.setParameter(0, userIDs);
				List beats = getBeats.list();
				for(Object obj : beats){
					Object[] objs = (Object[])obj;
					int salesExeID = Integer.valueOf(String.valueOf(objs[0]));
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
					if(salesExecs.get(salesExeID).getBeats() == null){
						salesExecs.get(salesExeID).setBeats( new ArrayList<Beat>());
					}
					salesExecs.get(salesExeID).getBeats().add(beat);
					
					//Set Beat Ids
					if(salesExecs.get(salesExeID).getBeatIDLists() == null){
						salesExecs.get(salesExeID).setBeatIDLists(new ArrayList<Integer>());
					}
					salesExecs.get(salesExeID).getBeatIDLists().add(beat.getBeatID());
				}
			}
			
		}catch(Exception exception){
			logger.error("Error while fetching list of sales executives", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<SalesExecutive>(salesExecs.values());
	}
	
	
	@Override
	public List<SalesExecutive> getSalesExecutivesHavingBeatsAssigned(int resellerID){
		Session session = null;
		Map<Integer, SalesExecutive> salesExecsMap = new HashMap<Integer, SalesExecutive>(); 
		try{
			Set<Integer> userIDs = new HashSet<Integer>();
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.*, b.ID BEAT_ID, b.NAME BEAT_NAME FROM USER a, BEAT b, SALES_EXEC_BEATS c WHERE a.ID=c.SALES_EXEC_ID AND b.ID=c.BEAT_ID;");
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				SalesExecutive salesExec = new SalesExecutive();
				salesExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				salesExec.setUserName(String.valueOf(objs[1]));
				salesExec.setDescription(String.valueOf(objs[3]));
				salesExec.setEmailID(String.valueOf(objs[4]));
				salesExec.setMobileNo(String.valueOf(objs[5]));
				salesExec.setFirstName(String.valueOf(objs[6]));
				salesExec.setLastName(String.valueOf(objs[7]));
				salesExec.setStatus(Integer.valueOf(String.valueOf(objs[8])));
				if(objs[9] != null){
					salesExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
				}
				
				if(objs[10] != null){
					salesExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[10])).getTime()));
				}
				salesExec.setCompanyID(Integer.valueOf(String.valueOf(objs[11])));
				salesExec.setResellerID(resellerID);
				
				if(!salesExecsMap.containsKey(salesExec.getUserID())){
					salesExecsMap.put(salesExec.getUserID(), salesExec);
				}
				
				//Add Beats
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[13])));
				beat.setName(String.valueOf(objs[14]));
				if(salesExecsMap.get(salesExec.getUserID()).getBeats() == null){
					salesExecsMap.get(salesExec.getUserID()).setBeats(new ArrayList<Beat>());
				}
				salesExecsMap.get(salesExec.getUserID()).getBeats().add(beat);
			}
		}catch(Exception exception){
			logger.error("Error while fetching list of sales executives", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<SalesExecutive>(salesExecsMap.values());
	}

	@Override
	public void assignBeats(final int salesExecID, final List<Integer> beatIDs) throws Exception{
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
						String sqlInsert = "INSERT INTO SALES_EXEC_BEATS VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < beatIDs.size(); i++) {
							pstmt.setInt(1, salesExecID);
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
	public void updateAssignedBeats(final int salesExecID, final List<Integer> beatIDs) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Delete existing sales Exec Beats
			SQLQuery deleteSalesExecBeats = session.createSQLQuery("DELETE FROM SALES_EXEC_BEATS WHERE SALES_EXEC_ID =? ");
			deleteSalesExecBeats.setParameter(0, salesExecID);
			deleteSalesExecBeats.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO SALES_EXEC_BEATS VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < beatIDs.size(); i++) {
							pstmt.setInt(1, salesExecID);
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
	public List<SalesExecutive> getSalesExecMapsBeatsCustomers(int resellerID) {
		Session session = null;
		List<SalesExecutive> salesExecList = new ArrayList<SalesExecutive>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, BEAT b, CUSTOMER c, ORDER_BOOKING_SCHEDULE d, RESELLER_USER e  WHERE a.ID=d.SALES_EXEC_ID AND b.ID=d.BEAT_ID AND c.ID=d.CUSTOMER_ID AND e.USER_ID=a.ID AND e.RESELLER_ID=? group by a.ID");
			query.setParameter(0, resellerID);
			List salesExecs = query.list();
			for(Object obj : salesExecs){
				Object[] objs = (Object[])obj;
				SalesExecutive salesExecutive = new SalesExecutive();
				salesExecutive.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				salesExecutive.setFirstName(String.valueOf(objs[1]));
				salesExecutive.setLastName(String.valueOf(objs[2]));
				salesExecList.add(salesExecutive);
			}
			return salesExecList;
		} catch (Exception exception) {
			logger.error("Error fetching sales executives mapped to beat and customer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public List<Beat> getAssignedBeats(int salesExecID) {
		Session session = null;
		List<Beat> beats = new ArrayList<Beat>();
		try {
			session = sessionFactory.openSession();
			SQLQuery beatsQuery = session.createSQLQuery("SELECT a.ID, a.NAME, a.DESCRIPTION, a.COVERAGE_SCHEDULE, a.DISTANCE, a.DATE_CREATED, a.DATE_MODIFIED FROM BEAT a, SALES_EXEC_BEATS b WHERE a.ID=b.BEAT_ID AND SALES_EXEC_ID=?");
			beatsQuery.setParameter(0, salesExecID);
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
	public List<Beat> getScheduledVisitSalesExecBeats(int salesExecID, Date visitDate) {
		Session session = null;
		List<Beat> beats = new ArrayList<Beat>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT b.ID, b.NAME FROM USER a, BEAT b, ORDER_BOOKING_SCHEDULE c  WHERE a.ID=c.SALES_EXEC_ID AND b.ID=c.BEAT_ID AND a.ID=? AND c.VISIT_DATE = ? group by b.ID");
			query.setParameter(0, salesExecID);
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
			logger.error("Error fetching sales executives mapped to beat and customer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public List<SalesExecutive> getScheduledVisitSalesExecs(Date visitDate, int resellerID) {
		Session session = null;
		List<SalesExecutive> salesExecs = new ArrayList<SalesExecutive>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.FIRST_NAME, a.LAST_NAME FROM USER a, ORDER_BOOKING_SCHEDULE b  WHERE a.ID=b.SALES_EXEC_ID AND b.VISIT_DATE = ? AND b.RESELLER_ID = ? group by a.ID");
			query.setParameter(0, visitDate);
			query.setParameter(1, resellerID);
			List lists = query.list();
			for(Object obj : lists){
				Object[] objs = (Object[])obj;
				SalesExecutive salesExecutive = new SalesExecutive();
				salesExecutive.setUserID(Integer.valueOf(String.valueOf(objs[0])));
				salesExecutive.setFirstName(String.valueOf(objs[1]));
				salesExecutive.setLastName(String.valueOf(objs[2]));
				salesExecutive.setName(String.valueOf(objs[1]) +" "+ String.valueOf(objs[2]));
				salesExecs.add(salesExecutive);
			}
			return salesExecs;
		} catch (Exception exception) {
			logger.error("Error fetching sales executives mapped to beat and customer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	@Override
	public List<TrimmedCustomer> getScheduledVisitBeatCustomers(int salesExecID, Date visitDate, int beatID){
		Session session = null;
		List<TrimmedCustomer> trimmedCustomers = new ArrayList<TrimmedCustomer>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.NAME FROM CUSTOMER a, ORDER_BOOKING_SCHEDULE b WHERE a.ID=b.CUSTOMER_ID AND b.SALES_EXEC_ID= ? AND b.VISIT_DATE= ? AND b.BEAT_ID= ? group by a.ID");
			query.setParameter(0, salesExecID);
			query.setParameter(1, visitDate);
			query.setParameter(2, beatID);
			List lists = query.list();
			for(Object obj : lists){
				Object[] objs = (Object[])obj;
				TrimmedCustomer trimmedCustomer = new TrimmedCustomer();
				trimmedCustomer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				trimmedCustomer.setCustomerName(String.valueOf(objs[1]));
				trimmedCustomers.add(trimmedCustomer);
			}
			return trimmedCustomers;
		} catch (Exception exception) {
			logger.error("Error fetching sales executives mapped to beat and customer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}
	
	@Override
	public void deleteBeatAssignment(int salesExecID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("DELETE FROM SALES_EXEC_BEATS WHERE SALES_EXEC_ID= ?");
			query.setParameter(0, salesExecID);
			query.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Sales Executive beats could not be successfully removed", exception);
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
}
