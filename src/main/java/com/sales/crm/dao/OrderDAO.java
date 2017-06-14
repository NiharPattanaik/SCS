package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderBookingStats;

public interface OrderDAO {
	
	public int create(Order order) throws Exception;
	
	void scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	void unScheduleOrderBooking(int orderScheduleID) throws Exception;	
	
	List<Order> getOrders(int resellerID) throws Exception;
	
	OrderBookingStats getOrderBookingStats(int salesExecID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getAllOrderBookedForDate(int resellerID, Date date) throws Exception;
	
	List<OrderBookingSchedule> getOrdersBookingSchedules(int resellerID, int salesExecID, int beatID, Date date) throws Exception;
}
