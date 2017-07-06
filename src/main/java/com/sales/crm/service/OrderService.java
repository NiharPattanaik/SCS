package com.sales.crm.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.OrderDAO;
import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderBookingStats;
import com.sales.crm.model.ScheduledOrderSummary;

@Service("orderService")
public class OrderService {
	
	@Autowired
	private OrderDAO orderDAO;
	
	public int create(Order order) throws Exception{
		return orderDAO.create(order);
	}
	
	public int createWithOTP(Order order, String otp) throws Exception{
		return orderDAO.createWithOTP(order, otp);
	}
	
	
	public int scheduleOrderBooking(OrderBookingSchedule orderBookingSchedule) throws Exception{
		if(orderBookingSchedule.getVisitDate() == null){
			orderBookingSchedule.setVisitDate(new Date());
		}
		return orderDAO.scheduleOrderBooking( orderBookingSchedule);
	}
	
	public List<String> alreadyOrderBookingScheduledCustomer(OrderBookingSchedule orderBookingSchedule) throws Exception{
		if(orderBookingSchedule.getVisitDate() == null){
			orderBookingSchedule.setVisitDate(new Date());
		}
		return orderDAO.alreadyOrderBookingScheduledCustomer( orderBookingSchedule);
	}
	
	public void unScheduleOrderBooking(int orderScheduleID, int customerID) throws Exception{
		orderDAO.unScheduleOrderBooking(orderScheduleID, customerID);
	}
	
	public List<Order> getOrders(int resellerID, int orderID) throws Exception{
		return orderDAO.getOrders(resellerID, orderID);
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
	
	public List<OrderBookingSchedule> getOrderScheduleReport(int resellerID, int salesExecID, int beatID, int customerID, int orderScheduleID, int status, Date date) throws Exception{
		return orderDAO.getOrderScheduleReport(resellerID, salesExecID, beatID, customerID, orderScheduleID, status, date);
	}
	
	public List<ScheduledOrderSummary> getScheduledOrderSummary(int resellerID, int salesExecID, Date visitDate){
		return orderDAO.getScheduledOrderSummary(resellerID, salesExecID, visitDate);
	}
	
	public List<Order> getOrderDetails(int resellerID, int orderID) throws Exception{
		return orderDAO.getOrders(resellerID, orderID);
	}
	
	public void editOrder(Order order) throws Exception{
		orderDAO.editOrder(order);
	}

}
