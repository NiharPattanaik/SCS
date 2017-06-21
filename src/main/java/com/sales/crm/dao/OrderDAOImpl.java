package com.sales.crm.dao;

import java.math.BigInteger;
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
import com.sales.crm.model.ScheduledOrderSummary;

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
			//Set status in ORDER_BOOKING_SCHEDULE_CUSTOMERS
			SQLQuery updateStatus = session.createSQLQuery("UPDATE ORDER_BOOKING_SCHEDULE_CUSTOMERS SET STATUS = "+OrderStatusEnum.ORDER_CREATED.getOrderStatus() + " WHERE ORDER_BOOKING_SCHEDULE_ID = ? AND CUSTOMER_ID = ?");
			updateStatus.setParameter(0, order.getOrderBookingID());
			updateStatus.setParameter(1, order.getCustomerID());
			updateStatus.executeUpdate();
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
	public int scheduleOrderBooking(final OrderBookingSchedule orderBookingSchedule) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			final List<Integer> custIDList = orderBookingSchedule.getCustomerIDs();
			session = sessionFactory.openSession();
			orderBookingSchedule.setDateCreated(new Date());
			transaction = session.beginTransaction();
			session.save(orderBookingSchedule);
			// get Connction from Session
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					PreparedStatement pstmt = null;
					try {
						String sqlInsert = "INSERT INTO ORDER_BOOKING_SCHEDULE_CUSTOMERS (ORDER_BOOKING_SCHEDULE_ID, CUSTOMER_ID, STATUS) VALUES (?, ?, ?)";
						pstmt = connection.prepareStatement(sqlInsert);
						for (int i = 0; i < custIDList.size(); i++) {
							pstmt.setInt(1, orderBookingSchedule.getBookingScheduleID());
							pstmt.setInt(2, orderBookingSchedule.getCustomerIDs().get(i));
							pstmt.setInt(3, OrderStatusEnum.ORDER_BOOKING_SCHEDULED.getOrderStatus());
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
		return orderBookingSchedule.getBookingScheduleID();
	}
	
	@Override
	public List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception{
		Session session = null;
		List<String> customerNames = new ArrayList<String>();
		try {
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(
					"SELECT a.NAME FROM CUSTOMER a, ORDER_BOOKING_SCHEDULE b, ORDER_BOOKING_SCHEDULE_CUSTOMERS c WHERE a.ID=c.CUSTOMER_ID AND b.ID=c.ORDER_BOOKING_SCHEDULE_ID AND b.SALES_EXEC_ID=? AND b.BEAT_ID=? AND b.VISIT_DATE = ? AND b.RESELLER_ID= ? AND c.CUSTOMER_ID IN ("+ StringUtils.join(orderBookingSchedule.getCustomerIDs(), ",")+") group by a.NAME");
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
	public void unScheduleOrderBooking(int orderScheduleID, int customerID) throws Exception{
		Session session = null;
		Transaction transaction = null;
		try {
			session = sessionFactory.openSession();
			transaction = session.beginTransaction();
			
			//Delete from ORDER_BOOKING_SCHEDULE_CUSTOMERS
			SQLQuery deleteScheduledCust = session.createSQLQuery("DELETE FROM ORDER_BOOKING_SCHEDULE_CUSTOMERS WHERE ORDER_BOOKING_SCHEDULE_ID = ? AND CUSTOMER_ID = ? AND STATUS = 1");
			deleteScheduledCust.setParameter(0, orderScheduleID);
			deleteScheduledCust.setParameter(1, customerID);
			deleteScheduledCust.executeUpdate();
			
			//Delete from ORDER_BOOKING_SCHEDULE if no reference in ORDER_BOOKING_SCHEDULE_CUSTOMERS
			SQLQuery getSchedulesForCust = session.createSQLQuery("SELECT COUNT(*) FROM ORDER_BOOKING_SCHEDULE_CUSTOMERS WHERE ORDER_BOOKING_SCHEDULE_ID = ?");
			getSchedulesForCust.setParameter(0, orderScheduleID);
			List counts = getSchedulesForCust.list();
			if(counts != null && counts.size() == 1 && ((BigInteger)counts.get(0)).intValue() == 0){
				SQLQuery query = session.createSQLQuery(
						"DELETE FROM ORDER_BOOKING_SCHEDULE WHERE ID = ?");
				query.setParameter(0, orderScheduleID);
				query.executeUpdate();
			}
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
	public List<Order> getOrders(int resellerID, int orderID) throws Exception {
		Session session = null;
		List<Order> orders = new ArrayList<Order>();
		SQLQuery orderQuery = null;
		try{
			session = sessionFactory.openSession();
			if(orderID != -1){
				orderQuery = session.createSQLQuery("SELECT a.*, c.NAME FROM ORDER_DETAILS a, ORDER_BOOKING_SCHEDULE b, CUSTOMER c  WHERE a.ORDER_BOOKING_ID = b.ID AND a.CUSTOMER_ID = c.ID AND a.RESELLER_ID= ? AND a.ID= ? ORDER BY a.DATE_CREATED DESC");
				orderQuery.setParameter(0, resellerID);
				orderQuery.setParameter(1, orderID);
			}else{
				orderQuery = session.createSQLQuery("SELECT a.*, c.NAME FROM ORDER_DETAILS a, ORDER_BOOKING_SCHEDULE b, CUSTOMER c  WHERE a.ORDER_BOOKING_ID = b.ID AND a.CUSTOMER_ID = c.ID AND a.RESELLER_ID= ? ORDER BY a.DATE_CREATED DESC");
				orderQuery.setParameter(0, resellerID);
			}
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
				order.setCustomerID(Integer.valueOf(String.valueOf(objs[7])));
				if(objs[8] != null){
					order.setDateCreated(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(objs[8])).getTime()));
				}
				order.setCustomerName(String.valueOf(objs[11]));
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
			SQLQuery allOrderBooking = session.createSQLQuery("SELECT b.NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, ORDER_BOOKING_SCHEDULE_CUSTOMERS c WHERE c.CUSTOMER_ID=b.ID AND a.ID=c.ORDER_BOOKING_SCHEDULE_ID AND a.SALES_EXEC_ID= ? AND a.VISIT_DATE= ? GROUP BY b.ID");
			allOrderBooking.setParameter(0, salesExecID);
			allOrderBooking.setParameter(1, new java.sql.Date(date.getTime()));
			List<String> allOrderBookedCustomers = allOrderBooking.list();
			orderBookingStats.setTotalNoOfVisits(allOrderBookedCustomers.size());
			orderBookingStats.setAllCustomersForVisit(allOrderBookedCustomers);
			
			//Order Booking completed
			SQLQuery completedOrderBookingQuery = session.createSQLQuery("SELECT b.NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, ORDER_DETAILS c WHERE c.CUSTOMER_ID=b.ID AND a.ID=c.ORDER_BOOKING_ID  AND a.SALES_EXEC_ID= ? AND  a.VISIT_DATE= ? GROUP BY b.ID");
			completedOrderBookingQuery.setParameter(0, salesExecID);
			completedOrderBookingQuery.setParameter(1, new java.sql.Date(date.getTime()));
			List<String> allOrderCompletedCustomers = completedOrderBookingQuery.list();
			orderBookingStats.setNoOfVisitsCompleted(allOrderCompletedCustomers.size());
			orderBookingStats.setCompletedCustomers(allOrderCompletedCustomers);
			
			//Order Booking Pending
			SQLQuery pendingOrderBookingQuery = session.createSQLQuery("SELECT b.NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, ORDER_DETAILS c, ORDER_BOOKING_SCHEDULE_CUSTOMERS d WHERE d.CUSTOMER_ID=b.ID AND a.ID=d.ORDER_BOOKING_SCHEDULE_ID AND a.SALES_EXEC_ID= ? AND a.VISIT_DATE= ? AND a.ID NOT IN (SELECT ORDER_BOOKING_ID FROM ORDER_DETAILS WHERE DATE_CREATED = ?)  GROUP BY b.ID");
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
					"SELECT a.ID, b.ID CUST_ID, b.NAME CUST_NAME, d.ID BEAT_ID, d.NAME BEAT_NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME, c.LAST_NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, USER c, BEAT d, ORDER_BOOKING_SCHEDULE_CUSTOMERS e WHERE e.CUSTOMER_ID=b.ID AND e.ORDER_BOOKING_SCHEDULE_ID=a.ID AND a.BEAT_ID = d.ID AND a.SALES_EXEC_ID = c.ID AND a.RESELLER_ID=b.RESELLER_ID AND a.RESELLER_ID=d.RESELLER_ID AND a.RESELLER_ID= ? AND a.VISIT_DATE = ? AND e.STATUS=1");
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
			StringBuilder sqlBuilder = new StringBuilder(
					"SELECT a.ID, b.ID CUST_ID, b.NAME CUST_NAME, d.ID BEAT_ID, d.NAME BEAT_NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME, c.LAST_NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, USER c, BEAT d, ORDER_BOOKING_SCHEDULE_CUSTOMERS e WHERE e.CUSTOMER_ID=b.ID AND e.ORDER_BOOKING_SCHEDULE_ID=a.ID AND a.BEAT_ID = d.ID AND a.SALES_EXEC_ID = c.ID AND a.RESELLER_ID=b.RESELLER_ID AND a.RESELLER_ID=d.RESELLER_ID AND e.STATUS=1");
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
	
	
	@Override
	public List<OrderBookingSchedule> getOrderScheduleReport(int resellerID, int salesExecID, int beatID, int customerID, int orderScheduleID, int status, Date date) throws Exception{
		Session session = null;
		List<OrderBookingSchedule> orderSchedules = new ArrayList<OrderBookingSchedule>();
		boolean whereClauseAdded = false;
		try{
			StringBuilder sqlBuilder = new StringBuilder(
					"SELECT * FROM (SELECT ORDER_SCHEDULE_ID, ORDER_DETAILS.ID ORDER_ID, CUST_ID, BEAT_ID, SALES_EXEC_ID, VISIT_DATE SALES_VISIT_DATE, BEAT_NAME, CUST_NAME, FIRST_NAME, LAST_NAME, ORDER_DETAILS.STATUS STATUS FROM (SELECT a.ID ORDER_SCHEDULE_ID, a.VISIT_DATE, b.ID CUST_ID, b.NAME CUST_NAME, d.ID BEAT_ID, d.NAME BEAT_NAME, c.ID SALES_EXEC_ID, c.FIRST_NAME, c.LAST_NAME FROM ORDER_BOOKING_SCHEDULE a, CUSTOMER b, USER c, BEAT d, ORDER_BOOKING_SCHEDULE_CUSTOMERS e WHERE e.CUSTOMER_ID=b.ID AND e.ORDER_BOOKING_SCHEDULE_ID=a.ID AND a.BEAT_ID = d.ID AND a.SALES_EXEC_ID = c.ID AND a.RESELLER_ID=b.RESELLER_ID AND a.RESELLER_ID=d.RESELLER_ID AND a.RESELLER_ID = "+ resellerID +") ORDER_SCHEDULE LEFT JOIN ORDER_DETAILS ON ORDER_SCHEDULE.ORDER_SCHEDULE_ID = ORDER_DETAILS.ORDER_BOOKING_ID AND ORDER_SCHEDULE.CUST_ID = ORDER_DETAILS.CUSTOMER_ID) FINAL");
			
			if (salesExecID > 0 || beatID > 0 || customerID > 0 || date != null || orderScheduleID > 0) {
				sqlBuilder.append(" WHERE ");
			}
			
			//Sales Exec Id
			if(salesExecID > 0){
				if(whereClauseAdded){
					sqlBuilder.append(" AND ");
				}
				sqlBuilder.append(" SALES_EXEC_ID ="+ salesExecID);
				whereClauseAdded = true;
			}
			
			//Beat Id
			if(beatID > 0){
				if(whereClauseAdded){
					sqlBuilder.append(" AND ");
				}
				sqlBuilder.append(" BEAT_ID ="+ beatID);
				whereClauseAdded = true;
			}
			
			//Customer ID
			if(customerID > 0){
				if(whereClauseAdded){
					sqlBuilder.append(" AND ");
				}
				sqlBuilder.append(" CUST_ID ="+ customerID);
				whereClauseAdded = true;
			}
			
			//Visit Date
			if(date != null){
				if(whereClauseAdded){
					sqlBuilder.append(" AND ");
				}
				sqlBuilder.append(" SALES_VISIT_DATE ='"+new SimpleDateFormat("yyyy-MM-dd").format(date)+"'");
				whereClauseAdded = true;
			}
			
			//Status
			if(status > 0){
				if(whereClauseAdded){
					sqlBuilder.append(" AND ");
				}
				sqlBuilder.append(" STATUS ="+ status);
				whereClauseAdded = true;
			}
			
			if(orderScheduleID > 0){
				if(whereClauseAdded){
					sqlBuilder.append(" AND ");
				}
				sqlBuilder.append(" ORDER_SCHEDULE_ID ="+ orderScheduleID);
				whereClauseAdded = true;
			}
			
			logger.debug(" getOrderScheduleReport sql "+ sqlBuilder.toString());
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(sqlBuilder.toString());
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				OrderBookingSchedule orderBookingSchedule = new OrderBookingSchedule();
				orderBookingSchedule.setBookingScheduleID(Integer.parseInt(String.valueOf(objs[0])));
				if(String.valueOf(objs[1]) != null && !String.valueOf(objs[1]).equals("null")){
					orderBookingSchedule.setOrderID(Integer.parseInt(String.valueOf(objs[1])));
				}
				orderBookingSchedule.setVisitDateAsString(new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(objs[5]))));
				orderBookingSchedule.setBeatName(String.valueOf(objs[6]));
				orderBookingSchedule.setCustomerName(String.valueOf(objs[7]));
				orderBookingSchedule.setSalesExecName(String.valueOf(objs[8]) +" "+ (String.valueOf(objs[9]) != null ? String.valueOf(objs[9]) : ""));
				if(String.valueOf(objs[10]) != null && !String.valueOf(objs[10]).equals("null")){
					switch(Integer.valueOf(String.valueOf(objs[10]))){
					case 1:
						orderBookingSchedule.setStatusAsString("Order Booking Scheduled");
						break;
					case 2:
						orderBookingSchedule.setStatusAsString("Order Created");
						break;
					case 3:
						orderBookingSchedule.setStatusAsString("Delivery Scheduled");
						break;
					case 4:
						orderBookingSchedule.setStatusAsString("Delivery Completed");
						break;
					case 5:
						orderBookingSchedule.setStatusAsString("Delivery Partial");
						break;
					case 6:
						orderBookingSchedule.setStatusAsString("Payment Scheduled");
						break;	
					case 7:
						orderBookingSchedule.setStatusAsString("Payment Completed");
						break;	
					case 8:
						orderBookingSchedule.setStatusAsString("Payment Partial");
						break;		
					}
				}else{
					orderBookingSchedule.setStatusAsString("Order Booking Scheduled");
				}
				orderSchedules.add(orderBookingSchedule);
			}

		}catch(Exception exception){
			logger.error("Error while fetching order schedule report", exception);
			throw exception;
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return orderSchedules;
	}
	
	@Override
	public List<ScheduledOrderSummary> getScheduledOrderSummary(int resellerID, int salesExecID, Date visitDate){
		
		Session session = null;
		List<ScheduledOrderSummary> orderSchedules = new ArrayList<ScheduledOrderSummary>();
		try{
			String visitDateStr = new SimpleDateFormat("yyyy-MM-dd").format(visitDate);
			StringBuilder queryBuilder = new StringBuilder(
					"SELECT a.ORDER_SCHEDULE_ID, a.VISIT_DATE, a.SALES_EXEC_ID, a.FIRST_NAME, a.LAST_NAME, b. TOTAL_SCHEDULED, IFNULL(a.ORDER_COUNT, 0) 'ORDER_COUNT', IFNULL(a.LINES, 0) 'LINES', IFNULL(a.VALUE, 0) 'VALUE' "
					+ "FROM (SELECT * FROM (SELECT a.ID ORDER_SCHEDULE_ID, a.VISIT_DATE, C.ID SALES_EXEC_ID, c.FIRST_NAME, c.LAST_NAME FROM ORDER_BOOKING_SCHEDULE a, USER c WHERE a.SALES_EXEC_ID = c.ID AND a.RESELLER_ID ="+ resellerID +" ) A "
					+ "LEFT JOIN (SELECT DISTINCT ORDER_BOOKING_ID, COUNT(ID) 'ORDER_COUNT', SUM(NO_OF_LINE_ITEMS) 'LINES', SUM(BOOK_VALUE) 'VALUE' FROM ORDER_DETAILS WHERE RESELLER_ID= "+ resellerID +" GROUP BY ORDER_BOOKING_ID) B ON A.ORDER_SCHEDULE_ID = B.ORDER_BOOKING_ID) a, "
					+ "(SELECT ORDER_BOOKING_SCHEDULE_ID, COUNT(CUSTOMER_ID) 'TOTAL_SCHEDULED' FROM ORDER_BOOKING_SCHEDULE_CUSTOMERS GROUP BY ORDER_BOOKING_SCHEDULE_ID) b WHERE a.ORDER_SCHEDULE_ID=b.ORDER_BOOKING_SCHEDULE_ID AND VISIT_DATE= '"+ visitDateStr + "'");
			
			if(salesExecID != -1){
				queryBuilder.append(" AND SALES_EXEC_ID = "+ salesExecID);
			}
			session = sessionFactory.openSession();
			SQLQuery query = session.createSQLQuery(queryBuilder.toString());
			List results = query.list();
			for(Object obj : results){
				Object[] objs = (Object[])obj;
				ScheduledOrderSummary scheduledOrderSummary = new ScheduledOrderSummary();
				scheduledOrderSummary.setScheduleID(Integer.parseInt(String.valueOf(objs[0])));
				scheduledOrderSummary.setVisitDateStr(new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(String.valueOf(objs[1]))));
				scheduledOrderSummary.setSalesExecName(String.valueOf(objs[3]) + " "+ (String.valueOf(objs[4]) != null ? String.valueOf(objs[4]) : ""));
				scheduledOrderSummary.setNumberOfSchedules(Integer.parseInt(String.valueOf(objs[5])));
				scheduledOrderSummary.setNumberOfOrders(Integer.parseInt(String.valueOf(objs[6])));
				scheduledOrderSummary.setNumberOfOrdersPending(scheduledOrderSummary.getNumberOfSchedules() - scheduledOrderSummary.getNumberOfOrders());
				scheduledOrderSummary.setNumberOfLines(Integer.parseInt(String.valueOf(objs[7])));
				scheduledOrderSummary.setTotalBookValue(Double.parseDouble(String.valueOf(objs[8])));
				orderSchedules.add(scheduledOrderSummary);
			}
			
		}catch(Exception exception){
			logger.error("Error while fetching schedule order summary.", exception);
		}finally{
			if(session != null){
				session.close();
			}
		}
		
		return orderSchedules;
	}

}
