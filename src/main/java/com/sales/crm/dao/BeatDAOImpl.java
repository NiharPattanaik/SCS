package com.sales.crm.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.Area;
import com.sales.crm.model.Beat;

@Repository("beatDAO")
public class BeatDAOImpl implements BeatDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void create(Beat beat) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			beat.setDateCreated(new Date());
			beat.setResellerID(13);
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
			e.printStackTrace();
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
			if(areaList.size() > 0){
				beat.setAreas(areaList);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
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
			e.printStackTrace();
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
			session.delete(beat);
			transaction.commit();
		} catch (Exception exception) {
			exception.printStackTrace();
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

			// Get Areas
			Map<Integer, List<Area>> beatAreaMap = new HashMap<Integer, List<Area>>();
			if (beatIDs != null && beatIDs.size() > 0) {
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
			}
			// Add area to Beat
			for (Beat beat : beats) {
				if (beatAreaMap.containsKey(beat.getBeatID())) {
					beat.setAreas(beatAreaMap.get(beat.getBeatID()));
				}
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return beats;
	}

}
