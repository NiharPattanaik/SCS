package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderBookingStats;
import com.sales.crm.model.ScheduledOrderSummary;

public interface OrderDAO {
	
	int create(Order order) throws Exception;
	
	int scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	void unScheduleOrderBooking(int orderScheduleID, int customerID, int tenantID) throws Exception;	
	
	List<Order> getOrders(int tenantID, int orderID) throws Exception;
	
	OrderBookingStats getOrderBookingStats(int salesExecID, Date date, int tenantID) throws Exception;
	
	List<OrderBookingSchedule> getAllOrderBookedForDate(int tenantID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getOrdersBookingSchedules(int tenantID, int salesExecID, int beatID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getOrderScheduleReport(int tenantID, int salesExecID, int beatID, int customerID, int orderScheduleID, int status, Date date) throws Exception;
	
	List<ScheduledOrderSummary> getScheduledOrderSummary(int tenantID, int salesExecID, Date visitDate);
	
	void editOrder(Order order) throws Exception;
	
	int createWithOTP(Order order, String otp) throws Exception;
	
	boolean isOrderingProcessInProgressForCustomer(int customerID, int tenantID);

}
