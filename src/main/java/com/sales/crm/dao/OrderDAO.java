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
	
	void unScheduleOrderBooking(int orderScheduleID, int customerID) throws Exception;	
	
	List<Order> getOrders(int resellerID, int orderID) throws Exception;
	
	OrderBookingStats getOrderBookingStats(int salesExecID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getAllOrderBookedForDate(int resellerID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getOrdersBookingSchedules(int resellerID, int salesExecID, int beatID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getOrderScheduleReport(int resellerID, int salesExecID, int beatID, int customerID, int orderScheduleID, int status, Date date) throws Exception;
	
	List<ScheduledOrderSummary> getScheduledOrderSummary(int resellerID, int salesExecID, Date visitDate);
}
