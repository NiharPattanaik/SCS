package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Beat;
import com.sales.crm.model.Order;
import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.OrderService;
import com.sales.crm.service.SalesExecService;

@Controller
@RequestMapping("/web/orderWeb")
public class OrderWebController {
	
	private static Logger logger = Logger.getLogger(OrderWebController.class);
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	SalesExecService salesExecService;
	
	@Autowired
	BeatService beatService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/{orderID}")
	public ModelAndView getOrderDetails(@PathVariable("orderID") int orderID) throws Exception{
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		Order order = null;
		try{
			List<Order> orders = orderService.getOrders(resellerID, orderID);
			if(orders != null && !orders.isEmpty()){
				order = orders.get(0);
			}
		}catch(Exception exception){
			logger.error("Error while fetching order details.", exception);
		}
		return new ModelAndView("/order_details", "order", order);
	}
	
	@GetMapping(value="/createOrderForm/{orderBookingID}/{customerID}/{customerName}")
	public ModelAndView getCreateOrderForm(@PathVariable("orderBookingID") int orderBookingID,
			@PathVariable("customerID") int customerID, @PathVariable("customerName") String customerName) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("order", new Order());
		modelMap.put("orderBookingID", orderBookingID);
		modelMap.put("customerID", customerID);
		modelMap.put("customerName", customerName);
		return new ModelAndView("/create_order", modelMap);

	}
	
	@GetMapping(value="/scheduledOrderBookings")
	public ModelAndView getScheduledOrderBookingList() throws Exception{
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecMapsBeatsCustomers(resellerID);
		List<OrderBookingSchedule> orderBookingSchedules = orderService.getAllOrderBookedForToday(resellerID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		modelMap.put("orderBookedSchedules", orderBookingSchedules);
		return new ModelAndView("/order_schedule_list", modelMap);
	}

	@PostMapping(value="/scheduleOrderBooking") 
	public ModelAndView scheduleOrderBooking(HttpServletRequest request, @ModelAttribute("orderBookingSchedule") OrderBookingSchedule orderBookingSchedule){
		String errmsg = "";
		String succmsg = "";
		List<String> customerNames = null;
		int orderScheduleID = -1;
		try{
			orderBookingSchedule.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
			customerNames = orderService.alreadyOrderBookingScheduledCustomer(orderBookingSchedule);
			if(customerNames != null && customerNames.size() > 0){
				errmsg = "<br>Customers <br><b>"+ StringUtils.join(customerNames, "<br>") +"</b><br>are already scheduled for a visit for <b>" + new SimpleDateFormat("dd-MM-yyyy").format(orderBookingSchedule.getVisitDate()) + "</b> date.";
			}
		}catch(Exception exception){
			errmsg = "Scheduling of order booking could not be processed successfully, please contact the System Administrator.";
		}
		
		if(errmsg.equals("")){
			try{
				orderBookingSchedule.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
				orderScheduleID = orderService.scheduleOrderBooking(orderBookingSchedule);
				succmsg = "Order booking scheduled for sales exec <b>"+ (String)request.getParameter("salesExecName") +"</b> is successful for date <b>"+ new SimpleDateFormat("dd-MM-yyyy").format(orderBookingSchedule.getVisitDate())+ "</b> with reference number <b>"+ orderScheduleID +"</b>.";
			}catch(Exception exception){
				errmsg = "Scheduling of order booking could not be processed successfully, please contact the System Administrator.";
			}
		}
		Map<String, String> modelMap = new HashMap<String, String>();
		modelMap.put("errmsg", errmsg);
		modelMap.put("succmsg", succmsg);
		return new ModelAndView("/order_booking_schedule_conf", modelMap);
	}
	
	/**
	@GetMapping(value="/unscheduleOrderBooking/{customerID}/{visitDateStr}") 
	public ModelAndView unscheduleOrderBooking(@PathVariable("customerID") int customerID, @PathVariable("visitDateStr") String visitDateStr){
		String msg = "";
		try{
			Date visitDate;
			if(visitDateStr == null || visitDateStr.trim().isEmpty()){
				visitDate = new Date();
			}else{
				visitDate = new SimpleDateFormat("dd-MM-yyyy").parse(visitDateStr);
			}
			ArrayList<Integer> customerIDs = new ArrayList<Integer>();
			customerIDs.add(customerID);
			orderService.unScheduleOrderBooking(customerIDs, visitDate);
		}catch(Exception exception){
			msg = "Customer visits could not be cancelled successfully. Please try after sometine and if error persists contact System Administrator.";
		}
		return new ModelAndView("/scheduled_order_cancel_conf", "msg", msg);
	}
	**/
	
	@GetMapping(value="/scheduleOrderBookingForm")
	public ModelAndView getScheduleOrderBookingForm(){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		return new ModelAndView("/schedule_order_booking", modelMap);
	}
	
	@GetMapping(value="/orderScheduleReport")
	public ModelAndView getOrderScheduleReport(){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<OrderBookingSchedule> orderBookingSchedules = new ArrayList<OrderBookingSchedule>();
		List<SalesExecutive> salesExecs = new ArrayList<SalesExecutive>();
		List<Beat> beats = new ArrayList<Beat>();
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try{
			orderBookingSchedules = orderService.getOrderScheduleReport(resellerID, -1, -1, -1, -1, -1, new Date());
			salesExecs = salesExecService.getSalesExecutives(resellerID);
			beats = beatService.getResellerBeats(resellerID);
			customers = customerService.getResellerTrimmedCustomers(resellerID);
			modelMap.put("orderBookingSchedules", orderBookingSchedules);
			modelMap.put("salesExecs", salesExecs);
			modelMap.put("beats", beats);
			modelMap.put("customers", customers);
			modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		}catch(Exception exception){
			//Don't do anything
		}
		return new ModelAndView("/order_schedule_report", modelMap);
	}
	
	@GetMapping(value="/orderScheduleReport/{scheduleID}")
	public ModelAndView getOrderScheduleReportForASchedule(@PathVariable("scheduleID") int scheduleID){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<OrderBookingSchedule> orderBookingSchedules = new ArrayList<OrderBookingSchedule>();
		List<SalesExecutive> salesExecs = new ArrayList<SalesExecutive>();
		List<Beat> beats = new ArrayList<Beat>();
		List<TrimmedCustomer> customers = new ArrayList<TrimmedCustomer>();
		Map<String, Object> modelMap = new HashMap<String, Object>();
		try{
			orderBookingSchedules = orderService.getOrderScheduleReport(resellerID, -1, -1, -1, scheduleID, -1, null);
			salesExecs = salesExecService.getSalesExecutives(resellerID);
			beats = beatService.getResellerBeats(resellerID);
			customers = customerService.getResellerTrimmedCustomers(resellerID);
			modelMap.put("orderBookingSchedules", orderBookingSchedules);
			modelMap.put("salesExecs", salesExecs);
			modelMap.put("beats", beats);
			modelMap.put("customers", customers);
			modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		}catch(Exception exception){
			//Don't do anything
		}
		return new ModelAndView("/order_schedule_report", modelMap);
	}
	
	
	@GetMapping(value="/list")
	public ModelAndView list(){
		List<Order> orders = new ArrayList<Order>();
		try{
			orders = orderService.getOrders(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))), -1);
		}catch(Exception exception){
			//
		}
		return new ModelAndView("/order_list","orders", orders);  
	}
	
	@GetMapping(value="/list/{orderID}")
	public ModelAndView getOrderList(@PathVariable("orderID") int orderID){
		List<Order> orders = new ArrayList<Order>();
		try{
			orders = orderService.getOrders(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))), orderID);
		}catch(Exception exception){
			//
		}
		return new ModelAndView("/order_list","orders", orders);  
	}
	
	@GetMapping(value="/editOrderForm/{orderID}")
	public ModelAndView getOrderForm(@PathVariable("orderID") int orderID){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		Order order = null;
		try{
			List<Order> orders = orderService.getOrders(resellerID, orderID);
			if(orders != null && !orders.isEmpty()){
				order = orders.get(0);
			}
		}catch(Exception exception){
			logger.error("Error while fetching order details.", exception);
		}
		return new ModelAndView("/edit_order", "order", order);
	}
	
	@PostMapping(value="/update")
	public ModelAndView updateOrder(@ModelAttribute("order") Order order){
		String msg = "";
		try{
			order.setDateCreated(new SimpleDateFormat("dd-MM-yyyy").parse(order.getDateCreatedString()));
			orderService.editOrder(order);
		}catch(Exception exception){
			logger.error("Error while updating the order : "+ order.getOrderID(), exception);
			msg = "Order <b>"+ order.getOrderID() + "</b> could not be updated successfully. Please try again after sometime and if error persists"
					+ ", contact System Administrator. ";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("msg", msg);
		modelMap.put("orderID", order.getOrderID());
		return new ModelAndView("/order_update_conf", modelMap);
	}
	
	@PostMapping(value="/create")
	public ModelAndView createOrder(@ModelAttribute("order") Order order){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		String msg = "";
		try{
			order.setResellerID(resellerID);
			orderService.create(order);
		}catch(Exception exception){
			logger.error("Error while creating the order.", exception);
			msg = "Order could not be created successfully. Please try again after sometime and if error persists"
					+ ", contact System Administrator. ";
		}
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("msg", msg);
		modelMap.put("orderID", order.getOrderID());
		return new ModelAndView("/order_create_conf", modelMap);
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}
}
