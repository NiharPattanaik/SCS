package com.sales.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.crm.dao.SalesExecDAO;
import com.sales.crm.model.SalesExec;

@Service("salesExecService")
public class SalesExecService {
	
	@Autowired
	private SalesExecDAO salesExecDAO;
	
	public SalesExec getSalesExec(long salesExecID){
		return salesExecDAO.get(salesExecID);
	}
	
	public void createSalesExec(SalesExec salesExec){
		salesExecDAO.create(salesExec);
	}
	
	public void updateSalesExec(SalesExec salesExec){
		salesExecDAO.update(salesExec);
	}
	
	public void deleteSalesExec(long salesExecID){
		salesExecDAO.delete(salesExecID);
	}
	
	public List<SalesExec> getResellerSalesExecs(long resellerID){
		return salesExecDAO.getResellerSalesExecs(resellerID);
	}
}
