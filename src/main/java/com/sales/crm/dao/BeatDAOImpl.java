package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Beat;

@Repository("beatDAO")
public class BeatDAOImpl implements BeatDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void create(Beat beat) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			beat.setDateCreated(new Date());
			beat.setResellerID(13);
			session.save(beat);
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
	public Beat get(long beatID) {

		Session session = null;
		Beat beat = null;
		try{
			session = sessionFactory.openSession();
			beat = (Beat)session.get(Beat.class, beatID);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return beat;
	
	}

	@Override
	public void update(Beat beat) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			beat.setDateModified(new Date());
			session.update(beat);
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
	public void delete(long beatID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Beat beat = (Beat)session.get(Beat.class, beatID);
			transaction = session.beginTransaction();
			session.delete(beat);
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
	public List<Beat> getResellerBeats(long resellerID) {
		Session session = null;
		List<Beat> beats = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Beat where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			beats = query.list();
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return beats;
	}

}
