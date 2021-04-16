package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sales.crm.model.Customer;
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

	@GetMapping(value = "/scheduledCustomers/{salesExecID}/{tenantID}")
	public ResponseEntity<ReSTResponse> scheduledTrimmedCustomerslist(@PathVariable("salesExecID") int salesExecID,
			@PathVariable("tenantID") int tenantID) {
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		ReSTResponse response = new ReSTResponse();
		try {
			customers = customerService.scheduledTrimmedCustomerslist(salesExecID, new Date(), tenantID);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
			response.setBusinessEntities(customers);
		} catch (Exception exception) {
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}

	// Called from mobile APP to list customers for delivery
	@GetMapping(value = "/scheduledCustomersForDelivery/{delivExecID}/{tenantID}")
	public ResponseEntity<ReSTResponse> scheduledTrimmedCustomerslistForDeliveryToday(
			@PathVariable("delivExecID") int delivExecID, @PathVariable("tenantID") int tenantID) {
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		ReSTResponse response = new ReSTResponse();
		try {
			customers = customerService.scheduledTrimmedCustomerslistForDeliveryToday(delivExecID, new Date(),
					tenantID);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
			response.setBusinessEntities(customers);
		} catch (Exception exception) {
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/toSchedule/{beatID}/{visitDate}/{tenantID}")
	public List<TrimmedCustomer> getCustomersToSchedule(@PathVariable("beatID") int beatID,
			@PathVariable("visitDate") String visitDate, @PathVariable("tenantID") int tenantID) {
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		try {
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
			customers = customerService.getCustomersToSchedule(beatID, date, tenantID);
		} catch (Exception exception) {
			logger.error("Error while fetching customers to schedule visit.", exception);
		}
		return customers;
	}

	@GetMapping(value = "/customersToScheduleDelivery/{beatID}/{visitDate}/{tenantID}")
	public List<CustomerOrder> getCustomersToScheduleDelivery(@PathVariable("beatID") int beatID,
			@PathVariable("visitDate") String visitDate, @PathVariable("tenantID") int tenantID) {
		List<CustomerOrder> customerOrders = new ArrayList<CustomerOrder>();
		try {
			Date date = new SimpleDateFormat("dd-MM-yyyy").parse(visitDate);
			customerOrders = customerService.getCustomersToScheduleDelivery(beatID, date, tenantID);
		} catch (Exception exception) {
			logger.error("Error while fetching customers to schedule delivery.", exception);
		}
		return customerOrders;
	}

	@GetMapping(value = "/customersForOTPVerification/{fieldExecID}/{otpType}/{tenantID}")
	public ResponseEntity<ReSTResponse> getCustomerForOTPVerification(@PathVariable("fieldExecID") int fieldExecID,
			@PathVariable("otpType") int otpType, @PathVariable("tenantID") int tenantID) {
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		ReSTResponse response = new ReSTResponse();
		try {
			customers = customerService.getCustomerForOTPVerification(fieldExecID, otpType, tenantID);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
			response.setBusinessEntities(customers);
		} catch (Exception exception) {
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/search/{filterParam}/{filterValue}")
	public ResponseEntity<ReSTResponse> search(@PathVariable("filterParam") String filterParam,
			@PathVariable("filterValue") Object filterValue) {
		List<Customer> customers = new ArrayList<Customer>();
		ReSTResponse response = new ReSTResponse();
		try {
			Map<String, Object> filterMap = new HashMap<String, Object>();
			filterMap.put(filterParam, filterValue);
			customers = customerService.search(Integer.parseInt(String.valueOf(httpSession.getAttribute("tenantID"))),
					filterMap);
			response.setStatus(ReSTResponse.STATUS_SUCCESS);
			response.setBusinessEntities(customers);
		} catch (Exception exception) {
			response.setStatus(ReSTResponse.STATUS_FAILURE);
			response.setErrorCode(ErrorCodes.SYSTEM_ERROR);
			response.setErrorMsg("Something is not right ! Please contact System Administrator");
			return new ResponseEntity<ReSTResponse>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ReSTResponse>(response, HttpStatus.OK);
	}

}
