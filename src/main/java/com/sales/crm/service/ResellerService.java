package com.sales.crm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.CustomerDAO;
import com.sales.crm.dao.ResellerDAO;
import com.sales.crm.model.Reseller;

@Service("resellerService")
public class ResellerService {
	
	@Autowired
	private ResellerDAO resellerDAO;
	
	@Autowired
	private CustomerDAO customerService;
	
	public Reseller getReseller(long resellerID){
		Reseller reseller = resellerDAO.get(resellerID);
		reseller.setCustomers(customerService.getResellerCustomers(resellerID));
		return reseller;
	}
	
	public void createReseller(Reseller reseller){
		resellerDAO.create(reseller);
	}
	
	public void updateReseller(Reseller reseller){
		resellerDAO.update(reseller);
	}
	
	public void deleteReseller(long resellerID){
		resellerDAO.delete(resellerID);
	}

}
