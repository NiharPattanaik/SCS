package com.sales.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.OTPDAO;
import com.sales.crm.model.CustomerOTP;

@Service("otpService")
public class OTPService {

	@Autowired
	OTPDAO otpDAO;
	
	public boolean generateOTP(CustomerOTP customerOTP){
		boolean status = otpDAO.generateOTP(customerOTP);
		//Call SMS Gateway
		return status;
	}
}
