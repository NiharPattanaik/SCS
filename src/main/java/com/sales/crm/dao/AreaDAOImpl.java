package com.sales.crm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Area;
import com.sales.crm.model.Beat;

@Repository("areaDAO")
public class AreaDAOImpl implements AreaDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(AreaDAOImpl.class);
	
	
	@Override
	public void create(Area area) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			area.setDateCreated(new Date());
			session.save(area);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating area", e);
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
	public Area get(int areaID) {
		Session session = null;
		Area area = null;
		Beat beat = null;
		try{
			session = sessionFactory.openSession();
			area = (Area)session.get(Area.class, areaID);
			SQLQuery  beatQuery = session.createSQLQuery("SELECT a.* FROM BEAT a, BEAT_AREA b WHERE a.ID = b.BEAT_ID and b.AREA_ID= ?");
			beatQuery.setParameter(0, areaID);
			List beatList = beatQuery.list();
			if(beatList != null && beatList.size() > 0){
				for (Object obj : beatList) {
					Object[] objs = (Object[]) obj;
					beat = new Beat();
					beat.setBeatID(Integer.valueOf(String.valueOf(objs[0])));
					beat.setResellerID(Integer.valueOf(String.valueOf(objs[1])));
					beat.setName(String.valueOf(objs[2]));
					beat.setDescription(String.valueOf(objs[3]));
					beat.setCoverageSchedule(String.valueOf(objs[4]));
					beat.setDistance(Integer.valueOf(String.valueOf(objs[5])));
				}
			}
			
			if(area != null && beat != null){
				area.setBeat(beat);
			}
		}catch(Exception exception){
			logger.error("Error while fetching area details.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return area;
	
	}

	@Override
	public void update(Area area) throws Exception{

		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			area.setDateModified(new Date());
			session.update(area);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while updating area", e);
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
	public void delete(int areaID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			Area area = (Area)session.get(Area.class, areaID);
			transaction = session.beginTransaction();
			//Remove from BEAT_AREA
			SQLQuery beatAreaQuery = session.createSQLQuery("DELETE FROM BEAT_AREA WHERE AREA_ID= ?");
			beatAreaQuery.setParameter(0, areaID);
			beatAreaQuery.executeUpdate();
			//Delete Area
			session.delete(area);
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while deleting area.", exception);
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
	public List<Area> getResellerAreas(int resellerID) {
		Session session = null;
		List<Area> areas = null; 
		try{
			session = sessionFactory.openSession();
			Query query = session.createQuery("from Area where resellerID = :resellerID order by DATE_CREATED DESC");
			query.setParameter("resellerID", resellerID);
			areas = query.list();
		}catch(Exception exception){
			logger.error("Error while getting reseller Areas", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return areas;
	}

	@Override
	public List<Area> getBeatAreas(int beatID) {

		Session session = null;
		List<Area> areaList = new ArrayList<Area>();
		try {
			session = sessionFactory.openSession();
			//Get Areas
			SQLQuery areasQuery = session.createSQLQuery("SELECT b.BEAT_ID, a.* FROM AREA A, BEAT_AREA b WHERE a.ID = b.AREA_ID AND b.BEAT_ID= ?");
			areasQuery.setParameter(0, beatID);
			List areas = areasQuery.list();
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
		} catch (Exception exception) {
			logger.error("Error while getting areas for beat.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return areaList;

	}
	
	@Override
	public List<Area> getResellerAreasNotMappedToBeat(int resellerID) {

		Session session = null;
		List<Area> areaList = new ArrayList<Area>();
		try {
			session = sessionFactory.openSession();
			//Get Areas
			SQLQuery areasQuery = session.createSQLQuery("SELECT * FROM AREA WHERE ID NOT IN (SELECT AREA_ID FROM BEAT_AREA) AND RESELLER_ID= ? ");
			areasQuery.setParameter(0, resellerID);
			List areas = areasQuery.list();
			for (Object obj : areas) {
				Object[] objs = (Object[]) obj;
				Area area = new Area();
				area.setAreaID(Integer.valueOf(String.valueOf(objs[0])));
				area.setResellerID(Integer.valueOf(String.valueOf(objs[1])));
				area.setName(String.valueOf(objs[2]));
				area.setDescription(String.valueOf(objs[3]));
				area.setWordNo(String.valueOf(objs[4]));
				area.setPinCode(String.valueOf(objs[5]));
				areaList.add(area);
			}
		} catch (Exception exception) {
			logger.error("Error while getting areas for beat.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return areaList;

	}
	
	@Override
	public List<Area> getResellerAreasNotMappedToBeatForEdit(int resellerID, int beatID) {

		Session session = null;
		List<Area> areaList = new ArrayList<Area>();
		try {
			session = sessionFactory.openSession();
			//Get Areas
			SQLQuery areasQuery = session.createSQLQuery("SELECT * FROM AREA WHERE ID NOT IN (SELECT AREA_ID FROM BEAT_AREA) AND RESELLER_ID= ? UNION SELECT * FROM AREA WHERE ID IN (SELECT AREA_ID FROM BEAT_AREA WHERE BEAT_ID= ?)");
			areasQuery.setParameter(0, resellerID);
			areasQuery.setParameter(1, beatID);
			List areas = areasQuery.list();
			for (Object obj : areas) {
				Object[] objs = (Object[]) obj;
				Area area = new Area();
				area.setAreaID(Integer.valueOf(String.valueOf(objs[0])));
				area.setResellerID(Integer.valueOf(String.valueOf(objs[1])));
				area.setName(String.valueOf(objs[2]));
				area.setDescription(String.valueOf(objs[3]));
				area.setWordNo(String.valueOf(objs[4]));
				area.setPinCode(String.valueOf(objs[5]));
				areaList.add(area);
			}
		} catch (Exception exception) {
			logger.error("Error while getting areas for beat.", exception);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return areaList;

	}

}
