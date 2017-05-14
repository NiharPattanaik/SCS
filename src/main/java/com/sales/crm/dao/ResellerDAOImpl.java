package com.sales.crm.dao;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import com.sales.crm.model.Reseller;

@Repository("resellerDAO")
public class ResellerDAOImpl implements ResellerDAO{
	
	private static Map<Integer, String> statusMap = new HashMap<Integer, String>();
	static{
		statusMap.put(0, "Inactive");
		statusMap.put(1, "Self Registered");
		statusMap.put(2, "Active");
		statusMap.put(3, "Cancelled");
	}

	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(ResellerDAOImpl.class);

	@Override
	public void create(Reseller reseller) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			reseller.setDateCreated(new Date());
			for(Address address : reseller.getAddress()){
				address.setDateCreated(new Date());
			}
			session.save(reseller);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating reseller.", e);
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
	public Reseller get(int resellerID) {
		Session session = null;
		Reseller reseller = null;
		try{
			session = sessionFactory.openSession();
			reseller = (Reseller)session.get(Reseller.class, resellerID);
		}catch(Exception exception){
			logger.error("Error while fetching reseller details", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return reseller;
	}



	@Override
	public void update(Reseller reseller) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			reseller.setDateModified(new Date());
			for(Address address : reseller.getAddress()){
				address.setDateModified(new Date());
			}
			session.update(reseller);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating reseller", e);
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}



	@Override
	public void delete(int resellerID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Reseller reseller = (Reseller)session.get(Reseller.class, resellerID);
			transaction = session.beginTransaction();
			session.delete(reseller);
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting reseller", exception);
			if(transaction != null){
				transaction.rollback();
			}
		}finally{
			if(session != null){
				session.close();
			}
		}
		
	}

	@Override
	public boolean isEmailIDAlreadyUsed(String emailID) throws Exception{

		Session session = null;
		try{
			session = sessionFactory.openSession();
			SQLQuery emailQuery = session.createSQLQuery(
					"SELECT COUNT(*) COUNT FROM ADDRESS a, RESELLER_ADDRESS b where a.ID = b.ADDRESS_ID AND a.ADDRESS_TYPE=1 AND a.EMAIL_ID= ?");
			emailQuery.setParameter(0, emailID);
			List counts = emailQuery.list();
			if(counts != null && counts.size() == 1 && ((BigInteger)counts.get(0)).intValue() > 0){
				return true;
			}
		}catch(Exception exception){
			logger.error("Error while validating user credential", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return false;
	}

	@Override
	public List<Reseller> getResellers() throws Exception{
		Session session = null;
		List<Reseller> resellers = new ArrayList<Reseller>();
		try{
			session = sessionFactory.openSession();
			Query resellersQuery = session.createQuery("FROM Reseller");
			resellers = resellersQuery.list();
			if(resellers != null){
				for(Iterator<Reseller> itr= resellers.iterator(); itr.hasNext(); ){
					//Skip appowner
					Reseller reseller = itr.next();
					if(reseller.getResellerID() == -1){
						itr.remove();
						continue;
					}
					reseller.setStatusText(statusMap.get(reseller.getStatus()));
				}
			}
		}catch(Exception exception){
			logger.error("Error while fetching resellers", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return resellers;
	}
	
	
	
}
