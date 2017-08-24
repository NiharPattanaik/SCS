package com.sales.crm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sales.crm.model.SalesExecutive;
import com.sales.crm.service.SupplierService;

@RestController
@RequestMapping("/rest/supplierReST")
public class SupplierReSTController {
	
	@Autowired
	SupplierService supplierService;
	
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/salesExecs/{supplierID}")
	public List<SalesExecutive> getSupplierSalesExecs(@PathVariable int supplierID){
		return supplierService.getSupplier(supplierID).getSalesExecs();
	}
	

}
