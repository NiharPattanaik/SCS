package com.sales.crm.dao;

import java.text.SimpleDateFormat;
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

import com.sales.crm.exception.CRMException;
import com.sales.crm.exception.ErrorCodes;
import com.sales.crm.model.CustomerOTP;

@Repository("otpDAO")
public class OTPDAOImpl implements OTPDAO{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(OTPDAOImpl.class);
	
	private static SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");
	

	@Override
	public int generateOTP(CustomerOTP customerOTP) throws Exception{
		Session session = null;
		Transaction transaction = null;
		List<CustomerOTP> dbCustomerOTPList = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			Query query = session.createQuery("from CustomerOTP where customerID = :customerID and otpType = :otpType and DATE(otpGeneratedDateTime) = current_date()");
			query.setParameter("customerID", customerOTP.getCustomerID());
			query.setParameter("otpType", customerOTP.getOtpType());
			dbCustomerOTPList = query.list();
			if(dbCustomerOTPList != null && dbCustomerOTPList.size() > 0){
				for(CustomerOTP dbCustomerOTP : dbCustomerOTPList){
					if(dbCustomerOTP.getSubmiitedOTP() != null ||
							dbCustomerOTP.getOtpSubmitedDateTime() != null){
						logger.error("OTP is already used for customer "+ customerOTP.getCompanyID() + " of OTP Type "+customerOTP.getOtpType() );	
						throw new CRMException(ErrorCodes.OTP_ALREADY_VERIFIED, "OTP for this customer is already used");
					}else{
						logger.warn("OTP generated earlier but not used and regenerating again.");
						Query deleteQuery = session.createQuery("delete from CustomerOTP where otpID = :otpID");
						deleteQuery.setParameter("otpID", dbCustomerOTP.getOtpID());
						deleteQuery.executeUpdate();
					}
				}
			}
			customerOTP.setOtpGeneratedDateTime(new Date());
			customerOTP.setDateCreated(new Date());
			session.save(customerOTP);
			transaction.commit();
			return customerOTP.getOtpID();
		} catch (Exception exception) {
			logger.error("Error while creating user", exception);
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
	public void removeGeneratedOTP(int otpID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery("DELETE FROM CUSTOMER_OTP WHERE ID=? ");
			query.setParameter( 0, otpID);
			query.executeUpdate();
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while getting Trimmed customer", exception);
			if (transaction != null) {
				transaction.rollback();
			}
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}
	
	/**
	 * Verify passed OTP is same, if same update the row or return false;
	 * 
	 * @param customerID
	 * @param otpType
	 * @param otp
	 * @return
	 * @throws Exception
	 */
	@Override
	public void verifyOTP(int customerID, int otpType, String otp) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			SQLQuery query = session.createSQLQuery(" SELECT ID FROM CUSTOMER_OTP WHERE CUSTOMER_ID= ? AND OTP_TYPE= ? AND GENERTAED_OTP= ? AND DATE(GENERATED_DATE_TIME)= CURDATE() AND SUBMITTED_DATE_TIME IS NULL AND SUBMITTED_OTP IS NULL");
			query.setParameter(0, customerID);
			query.setParameter(1, otpType);
			query.setParameter(2, otp);
			List results = query.list();
			if(results != null && results.size() == 1){
				for(Object obj : results){
					int otpID = Integer.valueOf(String.valueOf(obj));
					SQLQuery updateQuery = session.createSQLQuery("UPDATE CUSTOMER_OTP SET SUBMITTED_OTP=?, SUBMITTED_DATE_TIME=NOW(), DATE_MODIFIED=CURDATE() WHERE ID=? ");
					updateQuery.setParameter(0, otp);
					updateQuery.setParameter(1, otpID);
					updateQuery.executeUpdate();
				}
			}else{
				logger.error("OTP "+ otp +" not found or expired for customer "+ customerID + ", otp type "+ otpType + ".");
				throw new CRMException(ErrorCodes.OTP_MISMATCH, "OTP supplied could not match with the OTP generated.");
			}
			transaction.commit();
		}catch(Exception exception){
			logger.error("Error while verifying OTP", exception);
			if (transaction != null) {
				transaction.rollback();
			}
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
	}

}
