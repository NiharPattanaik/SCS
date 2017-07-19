package com.sales.crm.dao;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Address;
import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.SuppAreaManager;
import com.sales.crm.model.SuppSalesOfficer;
import com.sales.crm.model.Supplier;


@Repository("supplierDAO")
public class SupplierDAOImpl implements SupplierDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(SupplierDAOImpl.class);
	
	@Override
	public void create(Supplier supplier) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			supplier.setDateCreated(new Date());
			for(Address address : supplier.getAddress()){
				address.setDateCreated(new Date());
			}
			//supplier.getSalesOfficer().setResellerID(supplier.getResellerID());
			//supplier.getAreaManager().setResellerID(supplier.getResellerID());
			session.save(supplier);
			//Save Sales Officer
			if(supplier.getSalesOfficer() != null){
				SuppSalesOfficer salesOfficer = supplier.getSalesOfficer();
				SQLQuery insertSalesOff = session.createSQLQuery("INSERT INTO SUPPLIER_SALES_OFFICER (NAME, CONTACT_NO, EFFECTIVE_FROM, SUPPLIER_ID, RESELLER_ID, DATE_CREATED, COMPANY_ID ) VALUES (?, ?, ?, ?, ?, ?, ?)");
				insertSalesOff.setParameter(0, salesOfficer.getName());
				insertSalesOff.setParameter(1, salesOfficer.getContactNo());
				insertSalesOff.setDate(2, salesOfficer.getEffectiveFrom());
				insertSalesOff.setParameter(3, supplier.getSupplierID());
				insertSalesOff.setParameter(4, supplier.getResellerID());
				insertSalesOff.setDate(5, new Date());
				insertSalesOff.setInteger(6, 0);
				insertSalesOff.executeUpdate();
			}
			//Save Area Manager
			if(supplier.getAreaManager() != null){
				SuppAreaManager areaMgr = supplier.getAreaManager();
				SQLQuery insertAreaMgr = session.createSQLQuery("INSERT INTO SUPPLIER_AREA_MANAGER (NAME, CONTACT_NO, EFFECTIVE_FROM, SUPPLIER_ID, RESELLER_ID, DATE_CREATED, COMPANY_ID ) VALUES (?, ?, ?, ?, ?, ?, ?)");
				insertAreaMgr.setParameter(0, areaMgr.getName());
				insertAreaMgr.setParameter(1, areaMgr.getContactNo());
				insertAreaMgr.setDate(2, areaMgr.getEffectiveFrom());
				insertAreaMgr.setParameter(3, supplier.getSupplierID());
				insertAreaMgr.setParameter(4, supplier.getResellerID());
				insertAreaMgr.setDate(5, new Date());
				insertAreaMgr.setInteger(6, 0);
				insertAreaMgr.executeUpdate();
			}
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating supplier.", e);
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
	public Supplier get(int supplierID) {
		Session session = null;
		Supplier supplier = null;
		try{
			session = sessionFactory.openSession();
			supplier = (Supplier)session.get(Supplier.class, supplierID);
			//get Sales Officer
			Query soQuery = session.createQuery("FROM SuppSalesOfficer WHERE supplierID =:id");
			soQuery.setParameter("id", supplier.getSupplierID());
			List<SuppSalesOfficer> sos = soQuery.list();
			if(sos != null && sos.size() == 1){
				supplier.setSalesOfficer(sos.get(0));
					
			}
			//get Area Manager
			Query amQuery = session.createQuery("FROM SuppAreaManager WHERE supplierID =:id");
			amQuery.setParameter("id", supplier.getSupplierID());
			List<SuppAreaManager> ams = amQuery.list();
			if(ams != null && ams.size() == 1){
				supplier.setAreaManager(ams.get(0));
			}
			//Get Mapped Manufacturers
			SQLQuery manufQuery = session.createSQLQuery("SELECT MANUFACTURER_ID FROM SUPPLIER_MANUFACTURER WHERE SUPPLIER_ID= ?");
			manufQuery.setParameter(0, supplier.getSupplierID());
			List<Integer> manufacturerIDs = manufQuery.list();
			supplier.setManufacturerIDs(manufacturerIDs);
		}catch(Exception exception){
			logger.error("Error while fetching supplier details", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return supplier;
	}

	@Override
	public void update(Supplier supplier) throws Exception{

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			supplier.setDateModified(new Date());
			for(Address address : supplier.getAddress()){
				address.setDateModified(new Date());
			}
			session.update(supplier);
			//Save Sales Officer
			if(supplier.getSalesOfficer() != null){
				SuppSalesOfficer salesOfficer = supplier.getSalesOfficer();
				SQLQuery updateSalesOff = session.createSQLQuery("UPDATE SUPPLIER_SALES_OFFICER SET NAME= ?, CONTACT_NO = ?, DATE_MODIFIED = ? WHERE ID = ? AND SUPPLIER_ID = ? AND RESELLER_ID = ?");
				updateSalesOff.setParameter(0, salesOfficer.getName());
				updateSalesOff.setParameter(1, salesOfficer.getContactNo());
				updateSalesOff.setDate(2, new Date());
				updateSalesOff.setParameter(3, salesOfficer.getID());
				updateSalesOff.setParameter(4, supplier.getSupplierID());
				updateSalesOff.setParameter(5, supplier.getResellerID());
				int result = updateSalesOff.executeUpdate();
				if(result == 0){
					logger.debug("Nothing has been modified :)");
				}
			}
			//Save Area Manager
			if(supplier.getAreaManager() != null){
				SuppAreaManager areaMgr = supplier.getAreaManager();
				SQLQuery updateAreaMgr = session.createSQLQuery("UPDATE SUPPLIER_AREA_MANAGER SET NAME= ?, CONTACT_NO = ?, DATE_MODIFIED = ? WHERE ID = ? AND SUPPLIER_ID = ? AND RESELLER_ID = ?");
				updateAreaMgr.setParameter(0, areaMgr.getName());
				updateAreaMgr.setParameter(1, areaMgr.getContactNo());
				updateAreaMgr.setDate(2, new Date());
				updateAreaMgr.setParameter(3, areaMgr.getID());
				updateAreaMgr.setParameter(4, supplier.getSupplierID());
				updateAreaMgr.setParameter(5, supplier.getResellerID());
				int result = updateAreaMgr.executeUpdate();
				if(result == 0){
					logger.debug("Nothing has been modified :)");
				}
			}
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating supplier", e);
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
	public void delete(int supplierID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Supplier supplier = (Supplier)session.get(Supplier.class, supplierID);
			transaction = session.beginTransaction();
			//Remove Supplier
			session.delete(supplier);
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting supplier", exception);
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
	public List<Supplier> getResellerSupplier(int resellerID) {
		Session session = null;
		List<Supplier> suppliers = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Supplier where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			suppliers = query.list();
		}catch(Exception exception){
			logger.error("Error while fetching resellers suppliers", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return suppliers;
	}

	@Override
	public int getSuppliersCount(int resellerID){
		Session session = null;
		int counts = 0;
		try{
			session = sessionFactory.openSession();
			SQLQuery count = session.createSQLQuery("SELECT COUNT(*) FROM SUPPLIER WHERE RESELLER_ID= ?");
			count.setParameter(0, resellerID);
			List results = count.list();
			if(results != null && results.size() == 1 ){
				counts = ((BigInteger)results.get(0)).intValue();
			}
		}catch(Exception exception){
			logger.error("Error while fetching number of suppliers.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return counts;
	}
	
	@Override
	public List<Supplier> getSuppManufacturerList(int resellerID) {
		Session session = null;
		Map<Integer, Supplier> suppMap = new HashMap<Integer, Supplier>();
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery("SELECT a.ID SUPP_ID, a.NAME SUPP_NAME, b.ID MANF_ID, b.FULL_NAME MANF_NAME FROM SUPPLIER a, MANUFACTURER b, SUPPLIER_MANUFACTURER c "
					+ "WHERE a.ID = c.SUPPLIER_ID AND b.ID = c.MANUFACTURER_ID AND a.RESELLER_ID = ? AND b.RESELLER_ID = ?");
			query.setParameter(0, resellerID);
			query.setParameter(1, resellerID);
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				//Supplier
				Supplier supplier = null;
				if(suppMap.get(Integer.valueOf(String.valueOf(objs[0]))) != null){
					supplier = suppMap.get(Integer.valueOf(String.valueOf(objs[0])));
				}else{
					supplier = new Supplier();
					supplier.setSupplierID(Integer.valueOf(String.valueOf(objs[0])));
					supplier.setName(String.valueOf(objs[1]));
					suppMap.put(supplier.getSupplierID(), supplier);
				}
				//Manufaucturer
				Manufacturer manufacturer = new Manufacturer();
				manufacturer.setManufacturerID(Integer.valueOf(String.valueOf(objs[2])));
				manufacturer.setFullName(String.valueOf(objs[3]));
				//Set in supplier
				if(supplier.getManufacturers() == null){
					supplier.setManufacturers(new ArrayList<Manufacturer>());
				}
				supplier.getManufacturers().add(manufacturer);
			}
		}catch(Exception exception){
			logger.error("Error while fetching supplier-manufacturer mapping.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return new ArrayList<Supplier>(suppMap.values());
	}

	@Override
	public void assignManufacturer(int supplierID, List<Integer> manufacturerIDs) throws Exception{
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
						String sqlInsert = "INSERT INTO SUPPLIER_MANUFACTURER (SUPPLIER_ID, MANUFACTURER_ID) VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < manufacturerIDs.size(); i++) {
							pstmt.setInt(1, supplierID);
							pstmt.setInt(2, manufacturerIDs.get(i));
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
			logger.error("Error while assigning manufacturer to Supplier.", exception);
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
	public void updateAssignedManufacturer(int supplierID, List<Integer> manufacturerIDs) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove existing mapping
			SQLQuery removeMapping = session.createSQLQuery("DELETE FROM SUPPLIER_MANUFACTURER WHERE SUPPLIER_ID = ?");
			removeMapping.setParameter(0, supplierID);
			removeMapping.executeUpdate();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO SUPPLIER_MANUFACTURER (SUPPLIER_ID, MANUFACTURER_ID) VALUES (?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < manufacturerIDs.size(); i++) {
							pstmt.setInt(1, supplierID);
							pstmt.setInt(2, manufacturerIDs.get(i));
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
			logger.error("Error while assigning manufacturer to Supplier.", exception);
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
	public void deleteAassignedManufacturer(int supplierID) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			//Remove existing mapping
			SQLQuery removeMapping = session.createSQLQuery("DELETE FROM SUPPLIER_MANUFACTURER WHERE SUPPLIER_ID = ?");
			removeMapping.setParameter(0, supplierID);
			removeMapping.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			logger.error("Error while removing assigned manufacturer to Supplier.", exception);
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

}
