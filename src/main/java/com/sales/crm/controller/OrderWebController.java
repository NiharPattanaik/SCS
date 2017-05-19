package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.sql.ordering.antlr.OrderByFragmentRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.OrderBookingSchedule;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.service.OrderService;
import com.sales.crm.service.SalesExecService;

@Controller
@RequestMapping("/web/orderWeb")
public class OrderWebController {
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	SalesExecService salesExecService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/scheduledOrderBookings")
	public ModelAndView getScheduledOrderBookingList(){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecMapsBeatsCustomers(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		return new ModelAndView("/order_schedule_list", modelMap);
	}

	@PostMapping(value="/scheduleOrderBooking") 
	public ModelAndView scheduleOrderBooking(@ModelAttribute("orderBookingSchedule") OrderBookingSchedule orderBookingSchedule){
		String msg = "";
		List<String> customerNames = null;
		try{
			orderBookingSchedule.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
			customerNames = orderService.alreadyOrderBookingScheduledCustomer(orderBookingSchedule);
			if(customerNames != null && customerNames.size() > 0){
				msg = "<br>Customers <br><b>"+ StringUtils.join(customerNames, "<br>") +"</b><br>are already scheduled for a visit for <b>" + new SimpleDateFormat("dd-MM-yyyy").format(orderBookingSchedule.getVisitDate()) + "</b> date.";
			}
		}catch(Exception exception){
			msg = "Scheduling of order booking could not be processed successfully, please contact the System Administrator.";
		}
		
		if(msg.equals("")){
			try{
				orderBookingSchedule.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
				orderService.scheduleOrderBooking(orderBookingSchedule);
			}catch(Exception exception){
				msg = "Scheduling of order booking could not be processed successfully, please contact the System Administrator.";
			}
		}
		return new ModelAndView("/order_booking_schedule_conf", "msg", msg);
	}
	
	@PostMapping(value="/unscheduleOrderBooking") 
	public ModelAndView unscheduleOrderBooking(@ModelAttribute("orderBookingSchedule") OrderBookingSchedule orderBookingSchedule){
		String msg = "";
		try{
			orderService.unScheduleOrderBooking(orderBookingSchedule.getCustomerIDs(), orderBookingSchedule.getVisitDate());
		}catch(Exception exception){
			msg = "Customer visits could not be cancelled successfully. Please try after sometine and if error persists contact System Administrator.";
		}
		return new ModelAndView("/scheduled_order_cancel_conf", "msg", msg);
	}
	
	@GetMapping(value="/scheduleOrderBookingForm")
	public ModelAndView getScheduleOrderBookingForm(){
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("orderBookingSchedule", new OrderBookingSchedule());
		return new ModelAndView("/schedule_order_booking", modelMap);
	}
	
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		
	}
}
