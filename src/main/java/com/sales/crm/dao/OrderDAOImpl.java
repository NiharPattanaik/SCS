package com.sales.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderStatusEnum;

@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(OrderDAOImpl.class);

	@Override
	public void scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			final int salesExecID = orderBookingSchedule.getSalesExecutiveID();
			final int beatID = orderBookingSchedule.getBeatID();
			final List<Integer> custIDList = orderBookingSchedule.getCustomerIDs();
			final Date visitDate = orderBookingSchedule.getVisitDate();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO ORDER_BOOKING_SCHEDULE (SALES_EXEC_ID, BEAT_ID, CUSTOMER_ID, VISIT_DATE, STATUS) VALUES (?, ?, ?, ?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < custIDList.size(); i++) {
							pstmt.setInt(1, salesExecID);
							pstmt.setInt(2, beatID);
							pstmt.setInt(3, custIDList.get(i));
							pstmt.setDate(4, new java.sql.Date(visitDate.getTime()));
							pstmt.setInt(5, OrderStatusEnum.ORDER_BOOKING_SCHEDULED.getOrderStatus());
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
			logger.error("Error while scheduling sales executives visti.", exception);
			if(transaction != null){
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
	public List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception{
		Session session = null;
		List<String> customerNames = new ArrayList<String>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.NAME FROM CUSTOMER a, SALES_EXEC_BEATS_CUSTOMERS b WHERE a.ID=b.CUSTOMER_ID AND b.SALES_EXEC_ID=? AND b.BEAT_ID=? AND b.VISIT_DATE = ? AND b.CUSTOMER_ID IN ("+ StringUtils.join(orderBookingSchedule.getCustomerIDs(), ",")+") group by a.NAME");
			query.setParameter(0, orderBookingSchedule.getSalesExecutiveID());
			query.setParameter(1, orderBookingSchedule.getBeatID());
			query.setParameter(2, orderBookingSchedule.getVisitDate());
			List lists = query.list();
			for(Object obj : lists){
				customerNames.add(String.valueOf(obj));
			}
		} catch (Exception exception) {
			logger.error("Error fetching sales executives mapped to beat and customer.", exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return customerNames;
	}
	

	@Override
	public void unScheduleOrderBooking(List<Integer> customerID, Date visitDate) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"DELETE FROM ORDER_BOOKING_SCHEDULE WHERE CUSTOMER_ID IN (" +StringUtils.join(customerID, ",") +") AND VISIT_DATE = ?");
			query.setParameter(0, visitDate);
			transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			if(transaction != null){
				transaction.rollback();
			}
			logger.error("Error fetching sales executives mapped to beat and customer.", exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

}
