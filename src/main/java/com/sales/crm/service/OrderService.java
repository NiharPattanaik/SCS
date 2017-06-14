package com.sales.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.OrderDAO;
import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderBookingStats;

@Service("orderService")
public class OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	
	public int create(Order order) throws Exception{
		return orderDAO.create(order);
	}
	
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
	
	public void unScheduleOrderBooking(int orderScheduleID) throws Exception{
		orderDAO.unScheduleOrderBooking(orderScheduleID);
	}
	
	public List<Order> getOrders(int resellerID) throws Exception{
		return orderDAO.getOrders(resellerID);
	}
	
	public OrderBookingStats getOrderBookingStats(int salesExecID, Date date) throws Exception{
		return orderDAO.getOrderBookingStats(salesExecID, date);
	}
	
	public List<OrderBookingSchedule> getAllOrderBookedForToday(int resellerID) throws Exception{
		return orderDAO.getAllOrderBookedForDate(resellerID, new Date());
	}
	
	public List<OrderBookingSchedule> getOrdersBookingSchedules(int resellerID, int salesExecID, int beatID, Date date) throws Exception{
		return orderDAO.getOrdersBookingSchedules(resellerID, salesExecID, beatID, date);
	}

}
