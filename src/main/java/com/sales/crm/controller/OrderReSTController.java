package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.exception.ErrorCodes;
import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.OrderBookingStats;
import com.sales.crm.model.ReSTResponse;
import com.sales.crm.model.ScheduledOrderSummary;
import com.sales.crm.service.OrderService;

@RestController
@RequestMapping("/rest/orderReST")
public class OrderReSTController {
	
	private static Logger logger = Logger.getLogger(OrderReSTController.class);
	
	@Autowired
	HttpSession session;
	
	@Autowired
	OrderService orderService;
	
	@PostMapping(value="/create")
	public ResponseEntity<ReSTResponse> createOrder(@RequestBody Order order){
		ReSTResponse response = new ReSTResponse();
		int resellerID = (Integer) session.getAttribute("resellerID");
		int orderID = -1;
		try{
			order.setResellerID(resellerID);
			orderID = orderService.create(order);
			response.setBusinessEntityID(orderID);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}
	
	
	@PostMapping(value="/createWithOTP/{otp}")
	public ResponseEntity<ReSTResponse> createOrderWithOTP(@RequestBody Order order, @PathVariable("otp") String otp){
		ReSTResponse response = new ReSTResponse();
		int resellerID = (Integer) session.getAttribute("resellerID");
		int orderID = -1;
		try{
			order.setResellerID(resellerID);
			orderID = orderService.createWithOTP(order, otp);
			response.setBusinessEntityID(orderID);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/list")
	public ResponseEntity<ReSTResponse> getOrderList(){
		ReSTResponse response = new ReSTResponse();
		int resellerID = (Integer) session.getAttribute("resellerID");
		try{
			List<Order> orders = orderService.getOrders(resellerID, -1);
			response.setBusinessEntities(orders);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/orderBookingStatsToday/{salesExecID}")
	public ResponseEntity<ReSTResponse> getOrderBookingStatsForToday(@PathVariable int salesExecID){
		ReSTResponse response = new ReSTResponse();
		List<OrderBookingStats> orderBookingStats = new ArrayList<OrderBookingStats>();
		try{
			orderBookingStats.add(orderService.getOrderBookingStats(salesExecID, new Date()));
			response.setBusinessEntities(orderBookingStats);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
		
	}
	
	@GetMapping(value="/unscheduleOrderBooking/{orderScheduleID}/{customerID}") 
	public ResponseEntity<ReSTResponse> unscheduleOrderBooking(@PathVariable("orderScheduleID") int orderScheduleID, @PathVariable("customerID") int customerID){
		ReSTResponse response = new ReSTResponse();
		try{
			orderService.unScheduleOrderBooking(orderScheduleID, customerID);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Scheduled order booking could not be cancelled successfully. Please try after sometime and if error persists, contact System Administrator");
			return new ResponseEntity<ReSTResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/scheduledVisit/{salesExecID}/{visitDate}/{beatID}")
	public ResponseEntity<ReSTResponse> getOrdersBookingSchedules(@PathVariable("salesExecID") int salesExecID, @PathVariable("visitDate") String visitDateStr, @PathVariable("beatID") int beatID){
		List<OrderBookingSchedule> orderBookingSchedules;
		ReSTResponse response = new ReSTResponse();
		try{
			Date visitDate = null;
			//Hack by passing -
			if(visitDateStr != null && !visitDateStr.equals("-")){
				visitDate = new SimpleDateFormat("dd-MM-yyyy").parse(visitDateStr);
			}else{
				visitDate = new Date();
			}
			int resellerID = (Integer) session.getAttribute("resellerID");
			orderBookingSchedules = orderService.getOrdersBookingSchedules(resellerID, salesExecID, beatID, visitDate);
			response.setBusinessEntities(orderBookingSchedules);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			logger.error("Not able to fetch scheduled order booking list.", exception);
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Scheduled order booking could not be fetched successfully. Please try after sometime and if error persists, contact System Administrator");
			return new ResponseEntity<ReSTResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}
	
	@GetMapping(value="/orderScheduleReport/{salesExecID}/{beatID}/{visitDate}/{custID}/{status}")
	public ResponseEntity<ReSTResponse> getOrderScheduleReport(@PathVariable("salesExecID") int salesExecID, @PathVariable("beatID") int beatID, @PathVariable("visitDate") String visitDateStr, @PathVariable("custID") int custID, @PathVariable("status") int status){
		List<OrderBookingSchedule> orderBookingSchedules = new ArrayList<OrderBookingSchedule>();
		ReSTResponse response = new ReSTResponse();
		try{
			Date visitDate = null;
			//Hack by passing -
			if(visitDateStr != null && !visitDateStr.equals("-")){
				visitDate = new SimpleDateFormat("dd-MM-yyyy").parse(visitDateStr);
			}else{
				visitDate = new Date();
			}
			int resellerID = (Integer) session.getAttribute("resellerID");
			orderBookingSchedules = orderService.getOrderScheduleReport(resellerID, salesExecID, beatID, custID, -1, status, visitDate);
			response.setBusinessEntities(orderBookingSchedules);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			logger.error("Not able to fetch order schedule report data.", exception);
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Scheduled order booking could not be fetched successfully. Please try after sometime and if error persists, contact System Administrator");
			return new ResponseEntity<ReSTResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
		
	}
	
	
	@GetMapping(value="/dashboardOrderScheduleSummary/{visitDate}/{salesExecID}")
	public ResponseEntity<ReSTResponse> getDashboardOrderScheduleSummary(@PathVariable("salesExecID") int salesExecID, @PathVariable("visitDate") String visitDateStr){
		List<ScheduledOrderSummary> scheduledOrderSummaries = new ArrayList<ScheduledOrderSummary>();
		ReSTResponse response = new ReSTResponse();
		try{
			Date visitDate = null;
			//Hack by passing -
			if(visitDateStr != null && !visitDateStr.equals("-")){
				visitDate = new SimpleDateFormat("dd-MM-yyyy").parse(visitDateStr);
			}else{
				visitDate = new Date();
			}
			int resellerID = (Integer) session.getAttribute("resellerID");
			scheduledOrderSummaries = orderService.getScheduledOrderSummary(resellerID, salesExecID, visitDate);
			response.setBusinessEntities(scheduledOrderSummaries);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			logger.error("Not able to fetch order schedule report data.", exception);
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Scheduled order booking could not be fetched successfully. Please try after sometime and if error persists, contact System Administrator");
			return new ResponseEntity<ReSTResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
		
	}

}