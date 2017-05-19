package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;

public interface OrderDAO {
	
	public int create(Order order) throws Exception;
	
	void scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	void unScheduleOrderBooking(List<Integer> customerIDs, Date visitDate) throws Exception;	
	
	List<Order> getOrders(int resellerID) throws Exception;
}
