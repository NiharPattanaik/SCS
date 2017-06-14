package com.sales.crm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderBookingStats;
import com.sales.crm.model.OrderStatusEnum;

@Repository("orderDAO")
public class OrderDAOImpl implements OrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(OrderDAOImpl.class);
	
	@Override
	public int create(Order order) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try{
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			order.setDateCreated(new Date());
			order.setStatus(OrderStatusEnum.ORDER_CREATED.getOrderStatus());
			session.save(order);
			transaction.commit();
		}catch(Exception e){
			logger.error("Error while creating order", e);
			if(transaction != null){
				transaction.rollback();
			}
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return order.getOrderID();
	}

	@Override
	public void scheduleOrderBooking(final OrderBookingSchedule orderBookingSchedule) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			final List<Integer> custIDList = orderBookingSchedule.getCustomerIDs();
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO ORDER_BOOKING_SCHEDULE (SALES_EXEC_ID, BEAT_ID, CUSTOMER_ID, VISIT_DATE, STATUS, RESELLER_ID) VALUES (?, ?, ?, ?, ?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < custIDList.size(); i++) {
							pstmt.setInt(1, orderBookingSchedule.getSalesExecutiveID());
							pstmt.setInt(2, orderBookingSchedule.getBeatID());
							pstmt.setInt(3, orderBookingSchedule.getCustomerIDs().get(i));
							pstmt.setDate(4, new java.sql.Date(orderBookingSchedule.getVisitDate().getTime()));
							pstmt.setInt(5, OrderStatusEnum.ORDER_BOOKING_SCHEDULED.getOrderStatus());
							pstmt.setInt(6, orderBookingSchedule.getResellerID());
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
					"SELECT a.NAME FROM CUSTOMER a, ORDER_BOOKING_SCHEDULE b WHERE a.ID=b.CUSTOMER_ID AND b.SALES_EXEC_ID=? AND b.BEAT_ID=? AND b.VISIT_DATE = ? AND b.RESELLER_ID= ? AND b.CUSTOMER_ID IN ("+ StringUtils.join(orderBookingSchedule.getCustomerIDs(), ",")+") group by a.NAME");
			query.setParameter(0, orderBookingSchedule.getSalesExecutiveID());
			query.setParameter(1, orderBookingSchedule.getBeatID());
			query.setParameter(2, orderBookingSchedule.getVisitDate());
			query.setParameter(3, orderBookingSchedule.getResellerID());
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
	public void unScheduleOrderBooking(int orderScheduleID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"DELETE FROM ORDER_BOOKING_SCHEDULE WHERE ID = ?");
			query.setParameter(0, orderScheduleID);
			transaction = session.beginTransaction();
			query.executeUpdate();
			transaction.commit();
		} catch (Exception exception) {
			if(transaction != null){
				transaction.rollback();
			}
			logger.error("Error unscheduling order booking.", exception);
			throw exception;
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	
	@Override
	public List<Order> getOrders(int resellerID) throws Exception {
		Session session = null;
		List<Order> orders = new ArrayList<Order>();
		try{
			session = sessionFactory.openSession();
			SQLQuery orderQuery = session.createSQLQuery("SELECT a.*, c.NAME FROM ORDER_DETAILS a, ORDER_BOOKING_SCHEDULE b, CUSTOMER c WHERE a.ORDER_BOOKING_ID = b.ID AND b.CUSTOMER_ID = c.ID AND a.RESELLER_ID= ? ORDER BY a.DATE_CREATED DESC");
			orderQuery.setParameter(0, resellerID);
			List results = orderQuery.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				Order order = new Order();
				order.setOrderID(Integer.valueOf(String.valueOf(objs[0])));
				order.setOrderBookingID(Integer.valueOf(String.valueOf(objs[1])));
				order.setNoOfLineItems(Integer.valueOf(String.valueOf(objs[2])));
				order.setBookValue(Double.valueOf(String.valueOf(objs[3])));
				order.setRemark(String.valueOf(objs[4]));
				switch(Integer.valueOf(String.valueOf(objs[5]))){
					case 1:
						order.setStatusAsString("Order Booking Scheduled");
						break;
					case 2:
						order.setStatusAsString("Order Created");
						break;
					case 3:
						order.setStatusAsString("Delivery Scheduled");
						break;
					case 4:
						order.setStatusAsString("Delivery Completed");
						break;
					case 5:
						order.setStatusAsString("Delivery Partial");
						break;
					case 6:
						order.setStatusAsString("Payment Scheduled");
						break;	
					case 7:
						order.setStatusAsString("Payment Completed");
						break;	
					case 8:
						order.setStatusAsString("Payment Partial");
						break;		
				}
				order.setResellerID(Integer.valueOf(String.valueOf(objs[6])));
				if(objs[7] != null){
					order.setDateCreated(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(objs[7])).getTime()));
				}
				order.setCustomerName(String.valueOf(objs[10]));
				orders.add(order);
			}
		}catch(Exception e){
			logger.error("Error while fetching order list", e);
			throw e;
		}finally{
			if(session != null){
				session.close();
			}
		}
		return orders;
	}

	@Override
	public OrderBookingStats getOrderBookingStats(int salesExecID, Date date) throws Exception{
		Session session = null;
		OrderBookingStats orderBookingStats = new OrderBookingStats();
		try{
			session = sessionFactory.openSession();
			//all orders booked
			SQLQuery allOrderBooking = session.createSQLQuery("SELECT b.NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b WHERE a.CUSTOMER_ID=b.ID AND a.SALES_EXEC_ID= ? AND a.VISIT_DATE= ? GROUP BY b.ID");
			allOrderBooking.setParameter(0, salesExecID);
			allOrderBooking.setParameter(1, new java.sql.Date(date.getTime()));
			List<String> allOrderBookedCustomers = allOrderBooking.list();
			orderBookingStats.setTotalNoOfVisits(allOrderBookedCustomers.size());
			orderBookingStats.setAllCustomersForVisit(allOrderBookedCustomers);
			
			//Order Booking completed
			SQLQuery completedOrderBookingQuery = session.createSQLQuery("SELECT b.NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, ORDER_DETAILS c WHERE a.CUSTOMER_ID=b.ID AND a.ID=c.ORDER_BOOKING_ID  AND a.SALES_EXEC_ID= ? AND  a.VISIT_DATE= ? GROUP BY b.ID");
			completedOrderBookingQuery.setParameter(0, salesExecID);
			completedOrderBookingQuery.setParameter(1, new java.sql.Date(date.getTime()));
			List<String> allOrderCompletedCustomers = completedOrderBookingQuery.list();
			orderBookingStats.setNoOfVisitsCompleted(allOrderCompletedCustomers.size());
			orderBookingStats.setCompletedCustomers(allOrderCompletedCustomers);
			
			//Order Booking Pending
			SQLQuery pendingOrderBookingQuery = session.createSQLQuery("SELECT b.NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, ORDER_DETAILS c WHERE a.CUSTOMER_ID=b.ID AND a.SALES_EXEC_ID= ? AND a.VISIT_DATE= ? AND a.ID NOT IN (SELECT ORDER_BOOKING_ID FROM ORDER_DETAILS WHERE DATE_CREATED = ?)  GROUP BY b.ID");
			pendingOrderBookingQuery.setParameter(0, salesExecID);
			pendingOrderBookingQuery.setParameter(1, new java.sql.Date(date.getTime()));
			pendingOrderBookingQuery.setParameter(2, new java.sql.Date(date.getTime()));
			List<String> pendingOrderCustomers = pendingOrderBookingQuery.list();
			orderBookingStats.setNoOfVisitsPending(pendingOrderCustomers.size());
			orderBookingStats.setPendingCustomers(pendingOrderCustomers);
					
		}catch(Exception exception){
			logger.error("Error while fetching order booking stats.", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return orderBookingStats;
	}

	@Override
	public List<OrderBookingSchedule> getAllOrderBookedForDate(int resellerID, Date date) throws Exception{
		Session session = null;
		List<OrderBookingSchedule> orderSchedules = new ArrayList<OrderBookingSchedule>();
		try{
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.ID, b.ID CUST_ID, b.NAME CUST_NAME, d.ID BEAT_ID, d.NAME BEAT_NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME, c.LAST_NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, USER c, BEAT d WHERE a.CUSTOMER_ID=b.ID AND a.BEAT_ID = d.ID AND a.SALES_EXEC_ID = c.ID AND a.RESELLER_ID=b.RESELLER_ID AND a.RESELLER_ID=d.RESELLER_ID AND a.RESELLER_ID= ? AND a.VISIT_DATE = ? AND a.STATUS=1");
			query.setParameter(0, resellerID);
			query.setParameter(1, new java.sql.Date(date.getTime()));
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				OrderBookingSchedule orderBookingSchedule = new OrderBookingSchedule();
				orderBookingSchedule.setBookingScheduleID(Integer.parseInt(String.valueOf(objs[0])));
				orderBookingSchedule.setCustomerID(Integer.parseInt(String.valueOf(objs[1])));
				orderBookingSchedule.setCustomerName(String.valueOf(objs[2]));
				orderBookingSchedule.setBeatID(Integer.parseInt(String.valueOf(objs[3])));
				orderBookingSchedule.setBeatName(String.valueOf(objs[4]));
				orderBookingSchedule.setSalesExecutiveID(Integer.parseInt(String.valueOf(objs[5])));
				orderBookingSchedule.setSalesExecName(String.valueOf(objs[6]) +" "+ String.valueOf(objs[7]));
				orderSchedules.add(orderBookingSchedule);
			}

		}catch(Exception exception){
			logger.error("Error while fetching scheduled order bookings for " + new SimpleDateFormat("dd-MM-yyyy").format(date) +".");
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return orderSchedules;
	}
	
	
	@Override
	public List<OrderBookingSchedule> getOrdersBookingSchedules(int resellerID, int salesExecID, int beatID, Date date) throws Exception{
		Session session = null;
		List<OrderBookingSchedule> orderSchedules = new ArrayList<OrderBookingSchedule>();
		try{
			session = sessionFactory.openSession();
			StringBuilder sqlBuilder = new StringBuilder("SELECT a.ID, b.ID CUST_ID, b.NAME CUST_NAME, d.ID BEAT_ID, d.NAME BEAT_NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME, c.LAST_NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, USER c, BEAT d WHERE a.CUSTOMER_ID=b.ID AND a.BEAT_ID = d.ID AND a.SALES_EXEC_ID = c.ID AND a.RESELLER_ID=b.RESELLER_ID AND a.RESELLER_ID=d.RESELLER_ID AND a.STATUS=1");
			//Add Reseller ID
			sqlBuilder.append(" AND a.RESELLER_ID ="+ resellerID);
			//Sales Exec Id
			if(salesExecID > 0){
				sqlBuilder.append(" AND a.SALES_EXEC_ID ="+ salesExecID);
			}
			//Beat Id
			if(beatID > 0){
				sqlBuilder.append(" AND a.BEAT_ID ="+ beatID);
			}
			//Visit Date
			if(date != null){
				sqlBuilder.append(" AND a.VISIT_DATE ='"+new SimpleDateFormat("yyyy-MM-dd").format(date)+"'");
			}
			
			logger.debug(" getOrdersBookingSchedule sql "+ sqlBuilder.toString());
			
			SQLQuery query = session.createSQLQuery(sqlBuilder.toString());
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				OrderBookingSchedule orderBookingSchedule = new OrderBookingSchedule();
				orderBookingSchedule.setBookingScheduleID(Integer.parseInt(String.valueOf(objs[0])));
				orderBookingSchedule.setCustomerID(Integer.parseInt(String.valueOf(objs[1])));
				orderBookingSchedule.setCustomerName(String.valueOf(objs[2]));
				orderBookingSchedule.setBeatID(Integer.parseInt(String.valueOf(objs[3])));
				orderBookingSchedule.setBeatName(String.valueOf(objs[4]));
				orderBookingSchedule.setSalesExecutiveID(Integer.parseInt(String.valueOf(objs[5])));
				orderBookingSchedule.setSalesExecName(String.valueOf(objs[6]) +" "+ String.valueOf(objs[7]));
				orderSchedules.add(orderBookingSchedule);
			}

		}catch(Exception exception){
			logger.error("Error while fetching scheduled order bookings for " + new SimpleDateFormat("dd-MM-yyyy").format(date) +".");
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return orderSchedules;
	}
	

}
