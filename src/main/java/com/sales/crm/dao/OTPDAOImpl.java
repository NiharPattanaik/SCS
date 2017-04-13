package com.sales.crm.dao;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.CustomerOTP;

@Repository("otpDAO")
public class OTPDAOImpl implements OTPDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(OTPDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	

	@Override
	public boolean generateOTP(CustomerOTP customerOTP) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			session.save(customerOTP);
			transaction.commit();
		} catch (Exception e) {
			logger.error("Error while creating user", e);
			if (transaction != null) {
				transaction.rollback();
			}
			return false;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return true;
	}

	@Override
	public boolean updateOTP(CustomerOTP customerOTP) {
		// TODO Auto-generated method stub
		return false;
	}

}
