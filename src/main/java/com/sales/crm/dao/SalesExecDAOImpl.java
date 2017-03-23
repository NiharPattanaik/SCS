package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.SalesExec;


@Repository("salesExecDAO")
public class SalesExecDAOImpl implements SalesExecDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void create(SalesExec salesExec) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			salesExec.setDateCreated(new Date());
			session.save(salesExec);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
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
	public SalesExec get(long salesExecID) {
		Session session = null;
		SalesExec salesExec = null;
		try{
			session = sessionFactory.openSession();
			salesExec = (SalesExec)session.get(SalesExec.class, salesExecID);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return salesExec;
	}

	@Override
	public void update(SalesExec salesExec) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			salesExec.setDateModified(new Date());
			session.update(salesExec);
			transaction.commit();
		}catch(Exception e){
			e.printStackTrace();
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
	public void delete(long salesExecID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			SalesExec salesExec = (SalesExec)session.get(SalesExec.class, salesExecID);
			transaction = session.beginTransaction();
			session.delete(salesExec);
			transaction.commit();
		}catch(Exception exception){
			exception.printStackTrace();
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
	public List<SalesExec> getResellerSalesExecs(long resellerID) {
		Session session = null;
		List<SalesExec> salesExecs = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from SalesExec where resellerID = :resellerID ");
			query.setParameter("resellerID", resellerID);
			salesExecs = query.list();
		}catch(Exception exception){
			exception.printStackTrace();
		}
		return salesExecs;
	}

}
