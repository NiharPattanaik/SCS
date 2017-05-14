package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

import com.sales.crm.model.Supplier;
import com.sales.crm.service.SupplierService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/web/supplierWeb")
public class SupplierWebController {

	@Autowired
	SupplierService supplierService;
	
	
	@Autowired
	UserService userService;
	
	@Autowired
	HttpSession httpSession;
	
	@GetMapping(value="/{supplierID}")
	public ModelAndView get(@PathVariable int supplierID){
		Supplier supplier = supplierService.getSupplier(supplierID);
		return new ModelAndView("/supplier_details", "supplier", supplier);
		
	}
	
	@RequestMapping(value="/createSupplierForm", method = RequestMethod.GET)  
	public ModelAndView createSupplierForm(Model model){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("supplier", new Supplier());
		return new ModelAndView("/create_supplier", modelMap);
	}
	
	@RequestMapping(value="/editSupplierForm/{supplierID}", method = RequestMethod.GET)  
	public ModelAndView editSupplierForm(@PathVariable int supplierID){
		Supplier supplier = supplierService.getSupplier(supplierID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("supplier", supplier);
		return new ModelAndView("/edit_supplier", modelMap);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("supplier") Supplier supplier){
		supplier.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		String msg = "";
		try{
			supplierService.createSupplier(supplier);
		}catch(Exception exception){
			msg = "Creation of new supplier is not successfull, please contact System Administrator";
		}
		return new ModelAndView("/create_supplier_conf","msg", msg); 
	}
	
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("supplier") Supplier supplier){
		String msg = "";
		try{
			supplierService.updateSupplier(supplier);
		}catch(Exception exception){
			msg = "Supplier details could not be updated successfully, please contact System Administrator. ";
		}
		Map<String, String> modelMap = new HashMap<String, String>();
		modelMap.put("msg", msg);
		modelMap.put("supplierID", String.valueOf(supplier.getSupplierID()));
		return new ModelAndView("/edit_supplier_conf", "map", modelMap);
	}
	
	@GetMapping(value="/delete/{supplierID}")
	public ModelAndView delete(@PathVariable int supplierID){
		String msg = "";
		try{
			supplierService.deleteSupplier(supplierID);
		}catch(Exception exception){
			msg = "Supplier could not be successfully removed, please contact System Administrator";
		}
		return new ModelAndView("/delete_supplier_conf","msg", msg); 
	}
	
	@GetMapping(value="/list")
	public ModelAndView list(){
		List<Supplier> suppliers = supplierService.getResellerSuppliers(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/supplier_list","suppliers", suppliers);  
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
