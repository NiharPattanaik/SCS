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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.exception.ErrorCodes;
import com.sales.crm.model.CustomerOrder;
import com.sales.crm.model.ReSTResponse;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.CustomerService;

@RestController
@RequestMapping("/rest/customer")
public class CustomerReSTController {

	private static Logger logger = Logger.getLogger(CustomerReSTController.class);
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	HttpSession httpSession;
	
	
	@GetMapping(value="/scheduledCustomers/{salesExecID}")
	public ResponseEntity<ReSTResponse> scheduledTrimmedCustomerslist(@PathVariable int salesExecID){
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		ReSTResponse response = new ReSTResponse();
		try{
			customers = customerService.scheduledTrimmedCustomerslist(salesExecID, new Date());
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
			response.setBusinessEntities(customers);
		}catch(Exception exception){
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/toSchedule/{beatID}/{visitDate}")
	public List<TrimmedCustomer> getCustomersToSchedule(@PathVariable("beatID") int beatID, @PathVariable("visitDate") String visitDate){
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		try{
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
			customers = customerService.getCustomersToSchedule(beatID, date);
		}catch(Exception exception){
			logger.error("Error while fetching customers to schedule visit.", exception);
		}
		return customers;
	}
	
	@GetMapping(value="/customersToScheduleDelivery/{beatID}/{visitDate}")
	public List<CustomerOrder> getCustomersToScheduleDelivery(@PathVariable("beatID") int beatID, @PathVariable("visitDate") String visitDate){
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		try{
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
			customerOrders = customerService.getCustomersToScheduleDelivery(beatID, date, Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		}catch(Exception exception){
			logger.error("Error while fetching customers to schedule delivery.", exception);
		}
		return customerOrders;
	}
}
