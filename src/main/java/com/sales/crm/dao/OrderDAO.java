package com.sales.crm.dao;

import java.util.Date;
import java.util.List;

import com.sales.crm.model.OrderBookingSchedule;

public interface OrderDAO {
	
	void scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception;
	
	void unScheduleOrderBooking(List<Integer> customerIDs, Date visitDate) throws Exception;	

}
