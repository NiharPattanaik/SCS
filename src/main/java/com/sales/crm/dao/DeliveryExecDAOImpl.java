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
import com.sales.crm.model.DeliveryExecutive;

@Repository("deliveryDAO")
public class DeliveryExecDAOImpl implements DeliveryExecDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(DeliveryExecDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");


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
					if(delivExecs.get(salesExeID).getBeats() == null){
						delivExecs.get(salesExeID).setBeats( new ArrayList<Beat>());
					}
					delivExecs.get(salesExeID).getBeats().add(beat);
					
					//Set Beat Ids
					if(delivExecs.get(salesExeID).getBeatIDLists() == null){
						delivExecs.get(salesExeID).setBeatIDLists(new ArrayList<Integer>());
					}
					delivExecs.get(salesExeID).getBeatIDLists().add(beat.getBeatID());
				}
			}
			
		}catch(Exception exception){
			logger.error("Error while fetching list of sales executives", exception);
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
						String sqlInsert = "INSERT INTO DELIVERY_EXEC_BEATS (DELEV_EXEC_ID, BEAT_ID) VALUES (?, ?)";
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

}
