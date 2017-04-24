package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Customer;
import com.sales.crm.model.CustomerOTP;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.User;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.OTPService;
import com.sales.crm.service.SalesExecService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/web/otpWeb")
public class OTPWebController {

	@Autowired
	OTPService otpService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/report")
	public ModelAndView getOTPReport(){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<CustomerOTP> customerOTPs = otpService.getOTPReport(resellerID);
		return new ModelAndView("/otp_report", "customerOTPs", customerOTPs);
		
	}
	
	
}
