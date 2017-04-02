package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Area;

@Repository("areaDAO")
public class AreaDAOImpl implements AreaDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public void create(Area area) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			area.setDateCreated(new Date());
			area.setResellerID(13);
			session.save(area);
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
	public Area get(long areaID) {

		Session session = null;
		Area area = null;
		try{
			session = sessionFactory.openSession();
			area = (Area)session.get(Area.class, areaID);
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return area;
	
	}

	@Override
	public void update(Area area) {

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			area.setDateModified(new Date());
			session.update(area);
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
	public void delete(long areaID) {
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Area area = (Area)session.get(Area.class, areaID);
			transaction = session.beginTransaction();
			session.delete(area);
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
	public List<Area> getResellerAreas(long resellerID) {
		Session session = null;
		List<Area> areas = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Area where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			areas = query.list();
		}catch(Exception exception){
			exception.printStackTrace();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return areas;
	}

}
