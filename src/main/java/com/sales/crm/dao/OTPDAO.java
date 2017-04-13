package com.sales.crm.dao;

import com.sales.crm.model.CustomerOTP;

public interface OTPDAO {
	
	boolean generateOTP(CustomerOTP customerOTP);
	
	boolean updateOTP(CustomerOTP customerOTP);

}
