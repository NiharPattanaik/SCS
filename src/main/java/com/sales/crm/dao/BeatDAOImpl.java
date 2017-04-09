package com.sales.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Area;
import com.sales.crm.model.Beat;
import com.sales.crm.model.TrimmedCustomer;

@Repository("beatDAO")
public class BeatDAOImpl implements BeatDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private static Logger logger = Logger.getLogger(BeatDAOImpl.class);
	
	@Override
	public void create(Beat beat) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			beat.setDateCreated(new Date());
			session.save(beat);
			// create user_role
			if (beat.getAreas() != null && beat.getAreas().size() > 0) {
				for (Area area : beat.getAreas()) {
					SQLQuery createUserRole = session.createSQLQuery("INSERT INTO BEAT_AREA VALUES (?, ?)");
					createUserRole.setParameter(0, beat.getBeatID());
					createUserRole.setParameter(1, area.getAreaID());
					createUserRole.executeUpdate();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			logger.error("Error while creating beat.", e);
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
	public Beat get(int beatID) {

		Session session = null;
		Beat beat = null;
		try {
			session = sessionFactory.openSession();
			beat = (Beat) session.get(Beat.class, beatID);
			//Get Areas
			SQLQuery areasQuery = session.createSQLQuery("SELECT b.BEAT_ID, a.* FROM AREA A, BEAT_AREA b WHERE a.ID = b.AREA_ID AND b.BEAT_ID= ?");
			areasQuery.setParameter(0, beat.getBeatID());
			List areas = areasQuery.list();
			List<Area> areaList = new ArrayList<Area>();
			for (Object obj : areas) {
				Object[] objs = (Object[]) obj;
				Area area = new Area();
				area.setAreaID(Integer.valueOf(String.valueOf(objs[1])));
				area.setResellerID(Integer.valueOf(String.valueOf(objs[2])));
				area.setName(String.valueOf(objs[3]));
				area.setDescription(String.valueOf(objs[4]));
				area.setWordNo(String.valueOf(objs[5]));
				area.setPinCode(String.valueOf(objs[6]));
				areaList.add(area);
			}
			
			//GET Beat Customers
			List<Integer> customerIDs = new ArrayList<Integer>();
			SQLQuery beatCustomersQuery = session.createSQLQuery("SELECT a.ID FROM CUSTOMER a, BEAT_CUSTOMER b WHERE a.ID = b.CUSTOMER_ID AND b.BEAT_ID=? ");
			beatCustomersQuery.setParameter(0, beatID);
			List beatCustIDList = beatCustomersQuery.list();
			for (Object obj : beatCustIDList) {
				customerIDs.add(Integer.valueOf(String.valueOf(obj)));
			}
			
			//Add Areas
			if(areaList.size() > 0){
				beat.setAreas(areaList);
			}
			
			//Add Customer IDS
			if(customerIDs.size() > 0){
				beat.setCustomerIDs(customerIDs);
			}
		} catch (Exception exception) {
			logger.error("Error while fetching beat details.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return beat;

	}

	@Override
	public void update(Beat beat) {

		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			beat.setDateModified(new Date());
			session.update(beat);
			//update Beat_Area
			//TODO: Need to use update instead of delete and then insert
			if(beat.getAreas() != null && beat.getAreas().size() > 0){
				SQLQuery deleteUserRole = session.createSQLQuery("DELETE FROM BEAT_AREA WHERE BEAT_ID= ?");
				deleteUserRole.setParameter(0, beat.getBeatID());
				deleteUserRole.executeUpdate();
				for(Area area : beat.getAreas()){
					SQLQuery createBeatArea = session.createSQLQuery("INSERT INTO BEAT_AREA VALUES (?, ?)");
					createBeatArea.setParameter(0, beat.getBeatID());
					createBeatArea.setParameter(1, area.getAreaID());
					createBeatArea.executeUpdate();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			logger.error("Error while updating beat.", e);
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
	public void delete(int beatID) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			Beat beat = (Beat) session.get(Beat.class, beatID);
			transaction = session.beginTransaction();
			//Delete BEAT_AREA
			SQLQuery beatAreaQuery = session.createSQLQuery("DELETE FROM BEAT_AREA WHERE BEAT_ID=? ");
			beatAreaQuery.setParameter(0, beatID);
			beatAreaQuery.executeUpdate();
			//Delete Beat
			session.delete(beat);
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while deleting beat.", exception);
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
	public List<Beat> getResellerBeats(int resellerID) {
		Session session = null;
		List<Beat> beats = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Beat where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			beats = query.list();

			// get beat ids
			SQLQuery beatsIDQuery = session.createSQLQuery("SELECT ID FROM BEAT WHERE RESELLER_ID= ?");
			beatsIDQuery.setParameter(0, resellerID);
			List beatIDs = beatsIDQuery.list();

			//Beat-Area Map
			Map<Integer, List<Area>> beatAreaMap = new HashMap<Integer, List<Area>>();
			//Beat-Customers Map
			Map<Integer, List<TrimmedCustomer>> beatCustomersMap = new HashMap<Integer, List<TrimmedCustomer>>();
			//beat-customer id map
			Map<Integer, List<Integer>> beatCustomerIDsMap = new HashMap<Integer, List<Integer>>();
			
			if (beatIDs != null && beatIDs.size() > 0) {
				// Get Areas
				SQLQuery areasQuery = session.createSQLQuery(
						"SELECT b.BEAT_ID, a.* FROM AREA A, BEAT_AREA b WHERE a.ID = b.AREA_ID AND b.BEAT_ID IN ("
								+ StringUtils.join(beatIDs, ",") + ")");
				List areas = areasQuery.list();
				for (Object obj : areas) {
					Object[] objs = (Object[]) obj;
					int beatID = Integer.valueOf(String.valueOf(objs[0]));
					Area area = new Area();
					area.setAreaID(Integer.valueOf(String.valueOf(objs[1])));
					area.setResellerID(Integer.valueOf(String.valueOf(objs[2])));
					area.setName(String.valueOf(objs[3]));
					area.setDescription(String.valueOf(objs[4]));
					area.setWordNo(String.valueOf(objs[5]));
					area.setPinCode(String.valueOf(objs[6]));

					if (!beatAreaMap.containsKey(beatID)) {
						beatAreaMap.put(beatID, new ArrayList<Area>());
					}
					beatAreaMap.get(beatID).add(area);
				}
				
				//GET Beat Customers
				SQLQuery beatCustomersQuery = session.createSQLQuery("SELECT b.BEAT_ID, a.* FROM CUSTOMER a, BEAT_CUSTOMER b WHERE a.ID = b.CUSTOMER_ID");
				List beatCustList = beatCustomersQuery.list();
				for (Object obj : beatCustList) {
					Object[] objs = (Object[]) obj;
					int beatID = Integer.valueOf(String.valueOf(objs[0]));
					TrimmedCustomer customer = new TrimmedCustomer();
					customer.setCustomerID(Integer.valueOf(String.valueOf(objs[1])));
					customer.setCustomerName(String.valueOf(objs[2]));
					
					if(!beatCustomersMap.containsKey(beatID)){
						beatCustomersMap.put(beatID, new ArrayList<TrimmedCustomer>());
						beatCustomerIDsMap.put(beatID, new ArrayList<Integer>());
					}
					beatCustomersMap.get(beatID).add(customer);
					beatCustomerIDsMap.get(beatID).add(customer.getCustomerID());
				}
				
			}
			
			
			// Add area and Customers to Beat
			for (Beat beat : beats) {
				if (beatAreaMap.containsKey(beat.getBeatID())) {
					beat.setAreas(beatAreaMap.get(beat.getBeatID()));
					beat.setCustomers(beatCustomersMap.get(beat.getBeatID()));
					beat.setCustomerIDs(beatCustomerIDsMap.get(beat.getBeatID()));
				}
			}

		} catch (Exception exception) {
			logger.error("Error while fetching reseller beats.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return beats;
	}

	@Override
	public void assignBeatToCustomers(final int beatID, final List<Integer> customerIDs) {

		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO BEAT_CUSTOMER VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < customerIDs.size(); i++) {
							pstmt.setInt(1, beatID);
							pstmt.setInt(2, customerIDs.get(i));
							pstmt.addBatch();
						}
						pstmt.executeBatch();
					} finally {
						pstmt.close();
					}
				}
			});
			transaction.commit();
		} catch (Exception e) {
			logger.error("Error while assigning beat to customer.", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	
	@Override
	public void updateAssignedBeatToCustomers(final int beatID, final List<Integer> customerIDs) {

		Session session = null;
		
		try {
			session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			//Delete existing Beat-Customer
			SQLQuery deleteSalesExecBeats = session.createSQLQuery("DELETE FROM BEAT_CUSTOMER WHERE BEAT_ID =? ");
			deleteSalesExecBeats.setParameter(0, beatID);
			deleteSalesExecBeats.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO BEAT_CUSTOMER VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < customerIDs.size(); i++) {
							pstmt.setInt(1, beatID);
							pstmt.setInt(2, customerIDs.get(i));
							pstmt.addBatch();
						}
						pstmt.executeBatch();
					} finally {
						pstmt.close();
					}
				}
			});
			transaction.commit();
		} catch (Exception e) {
			logger.error("Error while updating assigned beats to customer", e);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	@Override
	public List<TrimmedCustomer> getBeatCustomers(int beatID) {
		Session session = null;
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		try {
			session = sessionFactory.openSession();
			SQLQuery beatsQuery = session.createSQLQuery("SELECT a.ID, a.NAME FROM CUSTOMER a, BEAT_CUSTOMER b WHERE a.ID = b.CUSTOMER_ID AND b.BEAT_ID = ?");
			beatsQuery.setParameter(0, beatID);
			List customerList = beatsQuery.list();
			for(Object obj : customerList){
				Object[] objs = (Object[])obj;
				TrimmedCustomer customer = new TrimmedCustomer();
				customer.setCustomerID(Integer.valueOf(String.valueOf(objs[0])));
				customer.setCustomerName(String.valueOf(objs[1]));
				customers.add(customer)	;		
			}
			return customers;
		} catch (Exception exception) {
			logger.error("Error while getting customers for beat.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return null;
	}

}