package com.sales.crm.dao;

import com.sales.crm.model.CustomerOTP;

public interface OTPDAO {
	
	int generateOTP(CustomerOTP customerOTP) throws Exception;
	
	void removeGeneratedOTP(int otpID) throws Exception;
	
	void verifyOTP(int customerID, int otpType, String otp) throws Exception;

}
