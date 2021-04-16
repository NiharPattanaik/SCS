package com.sales.crm.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Address;
import com.sales.crm.model.Beat;
import com.sales.crm.model.EntityStatusEnum;
import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.ManufacturerAreaManager;
import com.sales.crm.model.ManufacturerSalesExecBeats;
import com.sales.crm.model.ManufacturerSalesOfficer;
import com.sales.crm.model.SalesExecutive;


@Repository("manufacturerDAO")
public class ManufacturerDAOImpl implements ManufacturerDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(ManufacturerDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	
	@Override
	public void create(Manufacturer manufacturer) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			manufacturer.setDateCreated(new Date());
			manufacturer.setCode(UUID.randomUUID().toString());
			manufacturer.setStatusID(EntityStatusEnum.ACTIVE.getEntityStatus());
			session.save(manufacturer);
			for(Address address : manufacturer.getAddress()){
				address.setDateCreated(new Date());
				address.setCode(UUID.randomUUID().toString());
				address.setTenantID(manufacturer.getTenantID());
				address.setStatusID(EntityStatusEnum.ACTIVE.getEntityStatus());
				//Save Adddress
				session.save(address);
				//Insert into Manufacturer Address
				SQLQuery query = session.createSQLQuery("INSERT INTO MANUFACTURER_ADDRESS (MANUFACTURER_ID, ADDRESS_ID, TENANT_ID, DATE_CREATED) VALUES (?, ? , ?, CURDATE())");
				query.setInteger(0, manufacturer.getManufacturerID());
				query.setInteger(1, address.getId());
				query.setInteger(2, manufacturer.getTenantID());
				query.executeUpdate();
			}
			
			//Save Sales Officer
			if(manufacturer.getSalesOfficer() != null){
				ManufacturerSalesOfficer salesOfficer = manufacturer.getSalesOfficer();
				SQLQuery insertSalesOff = session.createSQLQuery("INSERT INTO MANUFACTURER_SALES_OFFICER (CODE, NAME, CONTACT_NO, EFFECTIVE_FROM, MANUFACTURER_ID, STATUS_ID, TENANT_ID, DATE_CREATED ) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE())");
				insertSalesOff.setParameter(0, UUID.randomUUID().toString());
				insertSalesOff.setParameter(1, salesOfficer.getName());
				insertSalesOff.setParameter(2, salesOfficer.getContactNo());
				insertSalesOff.setDate(3, salesOfficer.getEffectiveFrom());
				insertSalesOff.setParameter(4, manufacturer.getManufacturerID());
				insertSalesOff.setParameter(5, EntityStatusEnum.ACTIVE.getEntityStatus());
				insertSalesOff.setParameter(6, manufacturer.getTenantID());
				insertSalesOff.executeUpdate();
			}
			//Save Area Manager
			if(manufacturer.getAreaManager() != null){
				ManufacturerAreaManager areaMgr = manufacturer.getAreaManager();
				SQLQuery insertAreaMgr = session.createSQLQuery("INSERT INTO MANUFACTURER_AREA_MANAGER (CODE, NAME, CONTACT_NO, EFFECTIVE_FROM, MANUFACTURER_ID, STATUS_ID, TENANT_ID, DATE_CREATED ) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE())");
				insertAreaMgr.setParameter(0, UUID.randomUUID().toString());
				insertAreaMgr.setParameter(1, areaMgr.getName());
				insertAreaMgr.setParameter(2, areaMgr.getContactNo());
				insertAreaMgr.setDate(3, areaMgr.getEffectiveFrom());
				insertAreaMgr.setParameter(4, manufacturer.getManufacturerID());
				insertAreaMgr.setParameter(5, EntityStatusEnum.ACTIVE.getEntityStatus());
				insertAreaMgr.setParameter(6, manufacturer.getTenantID());
				insertAreaMgr.executeUpdate();
			}
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating manufacturer.", e);
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
	
	private List<Address> getManufacturerAddresses(Session session, int manufacturerID, int tenantID) throws Exception {
		// Get Manufacturer Address
		SQLQuery addressQuery = session.createSQLQuery(
				"SELECT a.* FROM Address a, MANUFACTURER_ADDRESS b WHERE a.ID = b.ADDRESS_ID AND b.MANUFACTURER_ID = ? AND b.TENANT_ID=?");
		addressQuery.setParameter(0, manufacturerID);
		addressQuery.setParameter(1, tenantID);
		List addresses = addressQuery.list();
		List<Address> addressList = new ArrayList<Address>(2);
		for (Object obj : addresses) {
			Object[] objs = (Object[]) obj;
			Address address = new Address();
			address.setId(Integer.valueOf(String.valueOf(objs[0])));
			address.setCode(String.valueOf(objs[1]));
			address.setAddressLine1(String.valueOf(objs[2]));
			address.setAddressLine2(String.valueOf(objs[3]));
			address.setStreet(String.valueOf(objs[4]));
			address.setCity(String.valueOf(objs[5]));
			address.setState(String.valueOf(objs[6]));
			address.setCountry(String.valueOf(objs[7]));
			address.setPostalCode(String.valueOf(objs[8]));
			address.setContactPerson(String.valueOf(objs[9]));
			address.setPhoneNumber(String.valueOf(objs[10]));
			address.setMobileNumberPrimary(String.valueOf(objs[11]));
			address.setMobileNumberSecondary(String.valueOf(objs[12]));
			address.setEmailID(String.valueOf(objs[13]));
			address.setAddrressType(Integer.valueOf(String.valueOf(objs[14])));
			address.setStatusID(Integer.valueOf(String.valueOf(objs[15])));
			address.setTenantID(Integer.valueOf(String.valueOf(objs[16])));
			address.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[17])).getTime()));
			if (objs[18] != null) {
				address.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[18])).getTime()));
			}
			addressList.add(address);
		}
		return addressList;
	}

	private List<ManufacturerSalesOfficer> getManufacturerSalesOfficers(Session session, int manufacturerID, int tenantID) {
		// get Sales Officer
		Query soQuery = session.createQuery("FROM ManufacturerSalesOfficer WHERE manufacturerID =:id AND tenantID = :tenantID");
		soQuery.setParameter("id", manufacturerID);
		soQuery.setParameter("tenantID", tenantID);
		List<ManufacturerSalesOfficer> sos = soQuery.list();
		return sos;
	}

	private List<ManufacturerAreaManager> getManufacturerAreaManagers(Session session, int manufacturerID, int tenantID) {
		// get Area Manager
		Query amQuery = session.createQuery("FROM ManufacturerAreaManager WHERE manufacturerID =:id AND tenantID = :tenantID");
		amQuery.setParameter("id", manufacturerID);
		amQuery.setParameter("tenantID", tenantID);
		List<ManufacturerAreaManager> ams = amQuery.list();
		return ams;
	}

	/**
	private List<Integer> getManufacturerManufacturerIDS(Session session, int manufacturerID, int tenantID) {
		// Get Mapped Manufacturers
		SQLQuery manufQuery = session.createSQLQuery(
				"SELECT MANUFACTURER_ID FROM SUPPLIER_MANUFACTURER WHERE SUPPLIER_ID = ? AND TENANT_ID= ? ");
		manufQuery.setParameter(0, manufacturerID);
		manufQuery.setParameter(1, tenantID);
		List<Integer> manufacturerIDs = manufQuery.list();
		return manufacturerIDs;
	}
	**/

	private List<Beat> getManufacturerBeats(Session session, int manufacturerID, int tenantID) throws Exception{
		// Get beats
		List<Beat> beats = new ArrayList<Beat>();
		SQLQuery beatQuery = session.createSQLQuery(
				"SELECT * FROM BEAT WHERE ID IN (SELECT BEAT_ID FROM MANUFACTURER_BEAT WHERE MANUFACTURER_ID = ?) AND TENANT_ID= ?");
		beatQuery.setParameter(0, manufacturerID);
		beatQuery.setParameter(1, tenantID);
		List resutls = beatQuery.list();
		for (Object obj : resutls) {
			Object[] objs = (Object[]) obj;
			Beat beat = new Beat();
			beat.setBeatID(Integer.valueOf(String.valueOf(objs[0])));
			beat.setCode(String.valueOf(objs[1]));
			beat.setName(String.valueOf(objs[2]));
			beat.setDescription(String.valueOf(objs[3]));
			beat.setCoverageSchedule(String.valueOf(objs[4]));
			beat.setDistance(Integer.valueOf(String.valueOf(objs[5])));
			beat.setStatusID(Integer.valueOf(String.valueOf(objs[6])));
			beat.setTenantID(Integer.valueOf(String.valueOf(objs[7])));
			if (objs[8] != null) {
				beat.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[8])).getTime()));
			}
			if (objs[9] != null) {
				beat.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[9])).getTime()));
			}
			beats.add(beat);
		}
		return beats;
	}

	private List<SalesExecutive> getManufacturerSalexExecs(Session session, int manufacturerID, int tenantID) throws Exception {
		// Get Sales Executives
		List<SalesExecutive> salesExecs = new ArrayList<SalesExecutive>();
		List<Integer> salesExecIDs = new ArrayList<Integer>();
		SQLQuery salesExecsQry = session.createSQLQuery(
				"SELECT a.* FROM USER a, MANUFACTURER_SALES_EXECUTIVES b WHERE  a.ID = b.SALES_EXEC_ID AND a.TENANT_ID=b.TENANT_ID AND a.TENANT_ID=? AND b.MANUFACTURER_ID = ?");
		salesExecsQry.setParameter(0, manufacturerID);
		salesExecsQry.setParameter(1, manufacturerID);
		List lists = salesExecsQry.list();
		for (Object obj : lists) {
			Object[] objs = (Object[]) obj;
			SalesExecutive salesExec = new SalesExecutive();
			salesExec.setUserID(Integer.valueOf(String.valueOf(objs[0])));
			salesExec.setCode(String.valueOf(objs[1]));
			salesExec.setUserName(String.valueOf(objs[2]));
			salesExec.setDescription(String.valueOf(objs[4]));
			salesExec.setEmailID(String.valueOf(objs[5]));
			salesExec.setMobileNo(String.valueOf(objs[6]));
			salesExec.setFirstName(String.valueOf(objs[7]));
			salesExec.setLastName(String.valueOf(objs[8]));
			salesExec.setStatusID(Integer.valueOf(String.valueOf(objs[9])));
			salesExec.setLoggedIn(Integer.valueOf(String.valueOf(objs[10])));
			salesExec.setTenantID(Integer.valueOf(String.valueOf(objs[11])));
			if (objs[12] != null) {
				salesExec.setDateCreated(new Date(dbFormat.parse(String.valueOf(objs[12])).getTime()));
			}

			if (objs[13] != null) {
				salesExec.setDateModified(new Date(dbFormat.parse(String.valueOf(objs[13])).getTime()));
			}
			salesExecs.add(salesExec);
			// salesExecIDs.add(salesExec.getUserID());

		}
		return salesExecs;
	}

	@Override
	public Manufacturer get(int manufacturerID, int tenantID) {
		Session session = null;
		Manufacturer manufacturer = null;
		try {
			session = sessionFactory.openSession();
			// manufacturer = (Manufacturer)session.get(Manufacturer.class, manufacturerID);
			Query query = session.createQuery("from Manufacturer where manufacturerID = :manufacturerID AND tenantID = :tenantID");
			query.setParameter("manufacturerID", manufacturerID);
			query.setParameter("tenantID", tenantID);
			if (query.list() != null && query.list().size() == 1) {
				manufacturer = (Manufacturer) query.list().get(0);

				// Add address to customer
				manufacturer.setAddress(getManufacturerAddresses(session, manufacturerID, tenantID));

				// get Sales Officer
				List<ManufacturerSalesOfficer> sos = getManufacturerSalesOfficers(session, manufacturerID, tenantID);
				if (sos != null && sos.size() == 1) {
					manufacturer.setSalesOfficer(sos.get(0));

				}
				
				// get Area Manager
				List<ManufacturerAreaManager> ams = getManufacturerAreaManagers(session, manufacturerID, tenantID);
				if (ams != null && ams.size() == 1) {
					manufacturer.setAreaManager(ams.get(0));
				}
				
				// Get beats
				List<Beat> beats = getManufacturerBeats(session, manufacturerID, tenantID);
				if (beats.size() > 0) {
					manufacturer.setBeats(beats);
				}

				// Get Sales Executives
				List<SalesExecutive> salesExecs = getManufacturerSalexExecs(session, manufacturerID, tenantID);
				if(salesExecs.size() > 0) {
					List<Integer> salesExecIDs = new ArrayList();
					for(SalesExecutive salesExec : salesExecs) {
						salesExecIDs.add(salesExec.getUserID());
					}
					manufacturer.setSalesExecs(salesExecs);
					manufacturer.setSalesExecsIDs(salesExecIDs);
				}
			}

		} catch (Exception exception) {
			logger.error("Error while fetching manufacturer details", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return manufacturer;
	}

	@Override
	public void update(Manufacturer manufacturer) throws Exception{

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			manufacturer.setDateModified(new Date());
			for(Address address : manufacturer.getAddress()){
				address.setDateModified(new Date());
			}
			session.update(manufacturer);
			//Save Sales Officer
			if(manufacturer.getSalesOfficer() != null){
				ManufacturerSalesOfficer salesOfficer = manufacturer.getSalesOfficer();
				SQLQuery updateSalesOff = session.createSQLQuery("UPDATE MANUFACTURER_SALES_OFFICER SET NAME= ?, CONTACT_NO = ?, DATE_MODIFIED = ? WHERE ID = ? AND MANUFACTURER_ID = ? AND TENANT_ID = ?");
				updateSalesOff.setParameter(0, salesOfficer.getName());
				updateSalesOff.setParameter(1, salesOfficer.getContactNo());
				updateSalesOff.setDate(2, new Date());
				updateSalesOff.setParameter(3, salesOfficer.getID());
				updateSalesOff.setParameter(4, manufacturer.getManufacturerID());
				updateSalesOff.setParameter(5, manufacturer.getTenantID());
				int result = updateSalesOff.executeUpdate();
				if(result == 0){
					logger.debug("Nothing has been modified :)");
				}
			}
			//Save Area Manager
			if(manufacturer.getAreaManager() != null){
				ManufacturerAreaManager areaMgr = manufacturer.getAreaManager();
				SQLQuery updateAreaMgr = session.createSQLQuery("UPDATE MANUFACTURER_AREA_MANAGER SET NAME= ?, CONTACT_NO = ?, DATE_MODIFIED = ? WHERE ID = ? AND MANUFACTURER_ID = ? AND TENANT_ID = ?");
				updateAreaMgr.setParameter(0, areaMgr.getName());
				updateAreaMgr.setParameter(1, areaMgr.getContactNo());
				updateAreaMgr.setDate(2, new Date());
				updateAreaMgr.setParameter(3, areaMgr.getID());
				updateAreaMgr.setParameter(4, manufacturer.getManufacturerID());
				updateAreaMgr.setParameter(5, manufacturer.getTenantID());
				int result = updateAreaMgr.executeUpdate();
				if(result == 0){
					logger.debug("Nothing has been modified :)");
				}
			}
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating manufacturer", e);
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
	public void delete(int manufacturerID, int tenantID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove Manufacturer
			Query query = session.createQuery("delete from Manufacturer where manufacturerID = :manufacturerID AND tenantID = :tenantID");
			query.setParameter("manufacturerID", manufacturerID);
			query.setParameter("tenantID", tenantID);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting manufacturer", exception);
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
	public List<Manufacturer> getTenantManufacturers(int tenantID) {
		Session session = null;
		List<Manufacturer> manufacturers = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Manufacturer where tenantID = :tenantID order by DATE_CREATED DESC");
			query.setParameter("tenantID", tenantID);
			manufacturers = query.list();
			for (Manufacturer manufacturer : manufacturers) {
				int manufacturerID = manufacturer.getManufacturerID();
				// Add address to customer
				manufacturer.setAddress(getManufacturerAddresses(session, manufacturerID, tenantID));

				// get Sales Officer
				List<ManufacturerSalesOfficer> sos = getManufacturerSalesOfficers(session, manufacturerID, tenantID);
				if (sos != null && sos.size() == 1) {
					manufacturer.setSalesOfficer(sos.get(0));

				}
				
				// get Area Manager
				List<ManufacturerAreaManager> ams = getManufacturerAreaManagers(session, manufacturerID, tenantID);
				if (ams != null && ams.size() == 1) {
					manufacturer.setAreaManager(ams.get(0));
				}
				
				// Get beats
				List<Beat> beats = getManufacturerBeats(session, manufacturerID, tenantID);
				if (beats.size() > 0) {
					manufacturer.setBeats(beats);
				}

				// Get Sales Executives
				List<SalesExecutive> salesExecs = getManufacturerSalexExecs(session, manufacturerID, tenantID);
				if(salesExecs.size() > 0) {
					List<Integer> salesExecIDs = new ArrayList();
					for(SalesExecutive salesExec : salesExecs) {
						salesExecIDs.add(salesExec.getUserID());
					}
					manufacturer.setSalesExecs(salesExecs);
					manufacturer.setSalesExecsIDs(salesExecIDs);
				}
			}
		} catch (Exception exception) {
			logger.error("Error while fetching tenant manufacturers", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return manufacturers;
	}

	@Override
	public int getManufacturersCount(int tenantID){
		Session session = null;
		int counts = 0;
		try{
			session = sessionFactory.openSession();
			SQLQuery count = session.createSQLQuery("SELECT COUNT(*) FROM MANUFACTURER WHERE TENANT_ID= ?");
			count.setParameter(0, tenantID);
			List results = count.list();
			if(results != null && results.size() == 1 ){
				counts = ((BigInteger)results.get(0)).intValue();
			}
		}catch(Exception exception){
			logger.error("Error while fetching number of manufacturers.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return counts;
	}
	
	/**
	@Override
	public List<Manufacturer> getSuppManufacturerList(int tenantID) {
		Session session = null;
		Map<Integer, Manufacturer> suppMap = new HashMap<Integer, Manufacturer>();
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID SUPP_ID, a.NAME SUPP_NAME, b.ID MANF_ID, b.FULL_NAME MANF_NAME FROM MANUFACTURER a, MANUFACTURER b, SUPPLIER_MANUFACTURER c "
					+ "WHERE a.ID = c.SUPPLIER_ID AND b.ID = c.MANUFACTURER_ID AND a.TENANT_ID = ? AND b.TENANT_ID = ?");
			query.setParameter(0, tenantID);
			query.setParameter(1, tenantID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				//Manufacturer
				Manufacturer manufacturer = null;
				if(suppMap.get(Integer.valueOf(String.valueOf(objs[0]))) != null){
					manufacturer = suppMap.get(Integer.valueOf(String.valueOf(objs[0])));
				}else{
					manufacturer = new Manufacturer();
					manufacturer.setManufacturerID(Integer.valueOf(String.valueOf(objs[0])));
					manufacturer.setName(String.valueOf(objs[1]));
					suppMap.put(manufacturer.getManufacturerID(), manufacturer);
				}
				//Manufaucturer
				Manufacturer manufacturer = new Manufacturer();
				manufacturer.setManufacturerID(Integer.valueOf(String.valueOf(objs[2])));
				manufacturer.setFullName(String.valueOf(objs[3]));
				//Set in manufacturer
				if(manufacturer.getManufacturers() == null){
					manufacturer.setManufacturers(new ArrayList<Manufacturer>());
				}
				manufacturer.getManufacturers().add(manufacturer);
			}
		}catch(Exception exception){
			logger.error("Error while fetching manufacturer-manufacturer mapping.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<Manufacturer>(suppMap.values());
	}
	**/
	
	@Override
	public List<Manufacturer> getManufacturerSalesExecsList(int tenantID) {
		Session session = null;
		Map<Integer, Manufacturer> manufMap = new HashMap<Integer, Manufacturer>();
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID, a.NAME, b.ID SALES_EXEC_ID, b.FIRST_NAME, b.LAST_NAME FROM MANUFACTURER a, USER b, "
					+ "MANUFACTURER_SALES_EXECUTIVES c WHERE a.ID=c.MANUFACTURER_ID AND b.ID=c.SALES_EXEC_ID AND c.TENANT_ID= ?");
			query.setParameter(0, tenantID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				//Manufacturer
				Manufacturer manufacturer = null;
				if(manufMap.get(Integer.valueOf(String.valueOf(objs[0]))) != null){
					manufacturer = manufMap.get(Integer.valueOf(String.valueOf(objs[0])));
				}else{
					manufacturer = new Manufacturer();
					manufacturer.setManufacturerID(Integer.valueOf(String.valueOf(objs[0])));
					manufacturer.setName(String.valueOf(objs[1]));
					manufMap.put(manufacturer.getManufacturerID(), manufacturer);
				}
				//Sales Executive
				SalesExecutive salesExecutive = new SalesExecutive();
				salesExecutive.setUserID(Integer.valueOf(String.valueOf(objs[2])));
				salesExecutive.setFirstName(String.valueOf(objs[3]));
				salesExecutive.setLastName(String.valueOf(objs[4]));
				//Set in manufacturer
				if(manufacturer.getSalesExecs() == null){
					manufacturer.setSalesExecs(new ArrayList<SalesExecutive>());
				}
				manufacturer.getSalesExecs().add(salesExecutive);
			}
		}catch(Exception exception){
			logger.error("Error while fetching manufacturer-salesexecutive mapping.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<Manufacturer>(manufMap.values());
	}

	/**
	@Override
	public void assignManufacturer(int manufacturerID, List<Integer> manufacturerIDs, int tenantID) throws Exception{
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
						String sqlInsert = "INSERT INTO SUPPLIER_MANUFACTURER (SUPPLIER_ID, MANUFACTURER_ID, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, CURDATE())";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < manufacturerIDs.size(); i++) {
							pstmt.setInt(1, manufacturerID);
							pstmt.setInt(2, manufacturerIDs.get(i));
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
			logger.error("Error while assigning manufacturer to Manufacturer.", exception);
			if (transaction != null) {
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
	public void updateAssignedManufacturer(int manufacturerID, List<Integer> manufacturerIDs, int tenantID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove existing mapping
			SQLQuery removeMapping = session.createSQLQuery("DELETE FROM SUPPLIER_MANUFACTURER WHERE SUPPLIER_ID = ?");
			removeMapping.setParameter(0, manufacturerID);
			removeMapping.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO SUPPLIER_MANUFACTURER (SUPPLIER_ID, MANUFACTURER_ID, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, CURDATE())";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < manufacturerIDs.size(); i++) {
							pstmt.setInt(1, manufacturerID);
							pstmt.setInt(2, manufacturerIDs.get(i));
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
			logger.error("Error while assigning manufacturer to Manufacturer.", exception);
			if (transaction != null) {
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
	public void deleteAassignedManufacturer(int manufacturerID, int tenantID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove existing mapping
			SQLQuery removeMapping = session.createSQLQuery("DELETE FROM SUPPLIER_MANUFACTURER WHERE SUPPLIER_ID = ? AND TENANT_ID = ?");
			removeMapping.setParameter(0, manufacturerID);
			removeMapping.setParameter(1, tenantID);
			removeMapping.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while removing assigned manufacturer to Manufacturer.", exception);
			if (transaction != null) {
				transaction.rollback();
			}
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	
		
	}
**/
	@Override
	public void assignSalesExecutivesToManufacturer(int tenantID, Manufacturer manufacturer) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			final List<Integer> salesExecIDs = manufacturer.getSalesExecsIDs();
			final int manufacturerID = manufacturer.getManufacturerID();
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO MANUFACTURER_SALES_EXECUTIVES (MANUFACTURER_ID, SALES_EXEC_ID, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, CURDATE())";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int salesExecID : salesExecIDs) {
							pstmt.setInt(1, manufacturerID);
							pstmt.setInt(2, salesExecID);
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
			logger.error("Error while assigning sales executives to Manufacturer.", exception);
			if (transaction != null) {
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
	public void updateAssignedSalesExecs(int manufacturerID, List<Integer> salesExecIDs, int tenantID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove existing mapping
			SQLQuery removeMapping = session.createSQLQuery("DELETE FROM MANUFACTURER_SALES_EXECUTIVES WHERE MANUFACTURER_ID = ?");
			removeMapping.setParameter(0, manufacturerID);
			removeMapping.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO MANUFACTURER_SALES_EXECUTIVES (MANUFACTURER_ID, SALES_EXEC_ID, TENANT_ID, DATE_CREATED) VALUES (?, ?, ?, CURDATE())";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int salesExecID : salesExecIDs) {
							pstmt.setInt(1, manufacturerID);
							pstmt.setInt(2, salesExecID);
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
			logger.error("Error while assigning manufacturer to Manufacturer.", exception);
			if (transaction != null) {
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
	public void deleteAassignedSalesExec(int manufacturerID, int tenantID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove existing mapping
			SQLQuery removeMapping = session.createSQLQuery("DELETE FROM MANUFACTURER_SALES_EXECUTIVES WHERE MANUFACTURER_ID = ? AND TENANT_ID = ?");
			removeMapping.setParameter(0, manufacturerID);
			removeMapping.setParameter(1, tenantID);
			removeMapping.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while removing assigned manufacturer to Manufacturer.", exception);
			if (transaction != null) {
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
	public List<ManufacturerSalesExecBeats> getManufSalesExecBeats(int tenantID) {
		Session session = null;
		Map<String, ManufacturerSalesExecBeats> manufMap = new HashMap<String, ManufacturerSalesExecBeats>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME FIRST_NAME, c.LAST_NAME LAST_NAME, b.ID BEAT_ID, b.NAME BEAT_NAME "
					+ "FROM MANUFACTURER a, BEAT b, USER c, MANUFACTURER_SALES_EXEC_BEATS d WHERE a.ID=d.MANUFACTURER_ID AND b.ID=d.BEAT_ID AND c.ID = d.SALES_EXEC_ID AND d.TENANT_ID= ?");
			query.setParameter(0, tenantID);
			List lists = query.list();
			for (Object obj : lists) {
				Object[] objs = (Object[]) obj;
				
				Manufacturer manufacturer = new Manufacturer();
				manufacturer.setManufacturerID(Integer.valueOf(String.valueOf(objs[0])));
				manufacturer.setName(String.valueOf(objs[1]));
				
				SalesExecutive salesExecutive = new SalesExecutive();
				salesExecutive.setUserID(Integer.valueOf(String.valueOf(objs[2])));
				salesExecutive.setFirstName(String.valueOf(objs[3]));
				salesExecutive.setLastName(String.valueOf(objs[4]));
				
				Beat beat = new Beat();
				beat.setBeatID(Integer.valueOf(String.valueOf(objs[5])));
				beat.setName(String.valueOf(objs[6]));
				
				if(!manufMap.containsKey(manufacturer.getManufacturerID()+"-"+salesExecutive.getUserID())){
					ManufacturerSalesExecBeats manufSalesExecBeats = new ManufacturerSalesExecBeats();
					manufSalesExecBeats.setManufacturer(manufacturer);
					manufSalesExecBeats.setSalesExecutive(salesExecutive);
					List<Beat> beats = new ArrayList<>();
					beats.add(beat);
					manufSalesExecBeats.setBeats(beats);
					manufMap.put(manufacturer.getManufacturerID()+"-"+salesExecutive.getUserID(), manufSalesExecBeats);
				}else{
					manufMap.get(manufacturer.getManufacturerID()+"-"+salesExecutive.getUserID()).getBeats().add(beat);
				}
			}
		} catch (Exception exception) {
			logger.error("Error while removing assigned manufacturer to Manufacturer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return new ArrayList<ManufacturerSalesExecBeats>(manufMap.values());
	}
	
	
	@Override
	public ManufacturerSalesExecBeats getManufSalesExecBeat(int tenantID, int manufID, int salesExecID) {
		Session session = null;
		ManufacturerSalesExecBeats manufSalesExecBeat = null;
		List<Integer> beatIDsList = new ArrayList<Integer>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, a.NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME FIRST_NAME, c.LAST_NAME LAST_NAME, b.ID BEAT_ID, b.NAME BEAT_NAME "
					+ "FROM MANUFACTURER a, BEAT b, USER c, MANUFACTURER_SALES_EXEC_BEATS d WHERE a.ID=d.MANUFACTURER_ID AND b.ID=d.BEAT_ID AND c.ID = d.SALES_EXEC_ID AND d.TENANT_ID= ? "
					+ "AND d.MANUFACTURER_ID=? AND d.SALES_EXEC_ID = ?");
			query.setParameter(0, tenantID);
			query.setParameter(1, manufID);
			query.setParameter(2, salesExecID);
			List lists = query.list();
			boolean added = false;
			for (Object obj : lists) {
				Object[] objs = (Object[]) obj;
				if(!added){
					manufSalesExecBeat = new ManufacturerSalesExecBeats();
					Manufacturer manufacturer = new Manufacturer();
					manufacturer.setManufacturerID(Integer.valueOf(String.valueOf(objs[0])));
					manufacturer.setName(String.valueOf(objs[1]));
					
					SalesExecutive salesExecutive = new SalesExecutive();
					salesExecutive.setUserID(Integer.valueOf(String.valueOf(objs[2])));
					salesExecutive.setFirstName(String.valueOf(objs[3]));
					salesExecutive.setLastName(String.valueOf(objs[4]));
					manufSalesExecBeat.setManufacturer(manufacturer);
					manufSalesExecBeat.setSalesExecutive(salesExecutive);
					Beat beat = new Beat();
					beat.setBeatID(Integer.valueOf(String.valueOf(objs[5])));
					beat.setName(String.valueOf(objs[6]));
					List<Beat> beats = new ArrayList<>();
					beats.add(beat);
					beatIDsList.add(beat.getBeatID());
					manufSalesExecBeat.setBeatIDLists(beatIDsList);
					manufSalesExecBeat.setBeats(beats);
					added = true;
				}else{
					Beat beat = new Beat();
					beat.setBeatID(Integer.valueOf(String.valueOf(objs[5])));
					beat.setName(String.valueOf(objs[6]));
					manufSalesExecBeat.getBeatIDLists().add(beat.getBeatID());
					manufSalesExecBeat.getBeats().add(beat);
				}
			}
		} catch (Exception exception) {
			logger.error("Error while removing assigned manufacturer to Manufacturer.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return manufSalesExecBeat;
	}

}
