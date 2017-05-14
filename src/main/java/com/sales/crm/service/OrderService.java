package com.sales.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.OrderDAO;
import com.sales.crm.model.OrderBookingSchedule;

@Service("orderService")
public class OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	
	public void scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception{
		if(orderBookingSchedule.getVisitDate() == null){
			orderBookingSchedule.setVisitDate(new Date());
		}
		orderDAO.scheduleOrderBooking( orderBookingSchedule);
	}
	
	public List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception{
		if(orderBookingSchedule.getVisitDate() == null){
			orderBookingSchedule.setVisitDate(new Date());
		}
		return orderDAO.alreadyOrderBookingScheduledCustomer( orderBookingSchedule);
	}
	
	public void unScheduleOrderBooking(List<Integer> customerIDs, Date visitDate) throws Exception{
		orderDAO.unScheduleOrderBooking(customerIDs, visitDate);
	}

}
