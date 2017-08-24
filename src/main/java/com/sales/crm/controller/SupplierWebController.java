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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Manufacturer;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.Supplier;
import com.sales.crm.service.ManufacturerService;
import com.sales.crm.service.SalesExecService;
import com.sales.crm.service.SupplierService;

@Controller
@RequestMapping("/web/supplierWeb")
public class SupplierWebController {

	@Autowired
	SupplierService supplierService;
	
	
	@Autowired
	ManufacturerService manufacturerService;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	SalesExecService salesExecService;
	
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
	
	@GetMapping(value="/assignManufacturerForm") 
	public ModelAndView getAssignManufacturerToSupplierForm(){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<Supplier> suppliers = supplierService.getResellerSuppliers(resellerID);
		List<Manufacturer> manufacturers = manufacturerService.getResellerManufacturers(resellerID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("suppliers", suppliers);
		modelMap.put("manufacturers", manufacturers);
		modelMap.put("supplier", new Supplier());
		return new ModelAndView("/assign_manufacturer_to_supplier", modelMap);
	}
	
	@PostMapping(value="/assignManufacturer")
	public ModelAndView assignManufacturer(@ModelAttribute("supplier") Supplier supplier){
		String msg = "";
		try{
			supplierService.assignManufacturer(supplier.getSupplierID(), supplier.getManufacturerIDs());
		}catch(Exception exception){
			msg = "Manufacturers could not be successfully mapped to supplier. Please try after sometime and if error persists contact System Administrator";
		}
		return new ModelAndView("/assign_manufacturer_to_supplier_conf", "msg", msg);
	}
	
	@GetMapping(value="/assignSalesExecutiveForm") 
	public ModelAndView getAssignSalesExecutivesToSupplierForm(){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<Supplier> suppliers = supplierService.getResellerSuppliers(resellerID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("suppliers", suppliers);
		modelMap.put("supplier", new Supplier());
		return new ModelAndView("/assign_salesexecs_to_supplier", modelMap);
	}
	
	@PostMapping(value="/assignSalesExecutive") 
	public ModelAndView assignSalesExecutivesToSupplier(@ModelAttribute("supplier") Supplier supplier){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		String msg = "";
		try{
			supplierService.assignSalesExecutivesToSupplier(resellerID, supplier);
		}catch(Exception exception){
			msg = "Sales Executives could not be successfully mapped to supplier. Please try after sometime and if error persists contact System Administrator";
		}
		return new ModelAndView("/assign_salesexec_to_supplier_conf", "msg", msg);
	}
	
	@GetMapping(value="/supp-manufacturer/list")
	public ModelAndView suppManufacturerList(){
		List<Supplier> suppliers = supplierService.getSuppManufacturerList(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/supp_manufacturer_list","suppliers", suppliers);  
	}

	@GetMapping(value="/supp-salesexecs/list")
	public ModelAndView suppSuppSalesExecList(){
		List<Supplier> suppliers = supplierService.getSuppSalesExecsList(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/supplier_sales_execs_list","suppliers", suppliers);  
	}
	
	@GetMapping(value="/assignManufacturerEditForm/{supplierID}") 
	public ModelAndView editAssignManufacturerForm(@PathVariable int supplierID){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<Manufacturer> manufacturers = manufacturerService.getResellerManufacturers(resellerID);
		Supplier supplier = supplierService.getSupplier(supplierID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("manufacturers", manufacturers);
		modelMap.put("supplier", supplier);
		return new ModelAndView("/edit_assign_manufacturer_to_supplier", modelMap);
	}
	
	@PostMapping(value="/updateAassignedManufacturer")
	public ModelAndView updateAssignedManufacturer(@ModelAttribute("supplier") Supplier supplier){
		String msg = "";
		try{
			supplierService.updateAssignedManufacturer(supplier.getSupplierID(), supplier.getManufacturerIDs());
		}catch(Exception exception){
			msg = "Manufacturers mapped to supplier could not be successfully updated. Please try after sometime and if error persists contact System Administrator";
		}
		return new ModelAndView("/edit_manufacturer_to_supplier_conf", "msg", msg);
	}
	
	@GetMapping(value="/deleteAassignedManufacturer/{supplierID}")
	public ModelAndView deleteAassignedManufacturer(@PathVariable int supplierID){
		String msg = "";
		try{
			supplierService.deleteAassignedManufacturer(supplierID);
		}catch(Exception exception){
			msg = "Manufacturers mapped to supplier could not be removed successfully. Please try after sometime and if error persists contact System Administrator";
		}
		return new ModelAndView("/remove_supplier_manufacturer_conf","msg", msg); 
	}
	
	@GetMapping(value="/assignSalesExecEditForm/{supplierID}") 
	public ModelAndView editAssignSalesExecForm(@PathVariable int supplierID){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<SalesExecutive> salesExecs = salesExecService.getSalesExecutives(resellerID);
		Supplier supplier = supplierService.getSupplier(supplierID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("salesExecs", salesExecs);
		modelMap.put("supplier", supplier);
		return new ModelAndView("/edit_assign_salesexec_to_supplier", modelMap);
	}
	
	@PostMapping(value="/updateAassignedSalesexecs")
	public ModelAndView updateAssignedSalesExecutives(@ModelAttribute("supplier") Supplier supplier){
		String msg = "";
		try{
			int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
			supplierService.updateAssignedSalesExecs(supplier.getSupplierID(), supplier.getSalesExecsIDs(), resellerID);
		}catch(Exception exception){
			msg = "Sales Executives mapped to supplier could not be successfully updated. Please try after sometime and if error persists contact System Administrator";
		}
		return new ModelAndView("/edit_salesexec_to_supplier_conf", "msg", msg);
	}
	
	@GetMapping(value="/deleteAassignedSalesexec/{supplierID}")
	public ModelAndView deleteAassignedSalesexec(@PathVariable int supplierID){
		String msg = "";
		try{
			supplierService.deleteAassignedSalesExec(supplierID);
		}catch(Exception exception){
			msg = "Sales Executives mapped to supplier could not be removed successfully. Please try after sometime and if error persists contact System Administrator";
		}
		return new ModelAndView("/remove_supplier_salesexec_conf","msg", msg); 
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
}
