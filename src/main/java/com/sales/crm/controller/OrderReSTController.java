package com.sales.crm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.exception.ErrorCodes;
import com.sales.crm.model.Order;
import com.sales.crm.model.ReSTResponse;
import com.sales.crm.service.OrderService;

@RestController
@RequestMapping("/rest/orderReST")
public class OrderReSTController {
	
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
	
	@GetMapping(value="/list")
	public ResponseEntity<ReSTResponse> getOrderList(){
		ReSTResponse response = new ReSTResponse();
		int resellerID = (Integer) session.getAttribute("resellerID");
		try{
			List<Order> orders = orderService.getOrders(resellerID);
			response.setBusinessEntities(orders);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}

}
