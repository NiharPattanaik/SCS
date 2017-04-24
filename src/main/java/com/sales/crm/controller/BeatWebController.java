package com.sales.crm.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Area;
import com.sales.crm.model.Beat;
import com.sales.crm.model.Customer;
import com.sales.crm.model.SalesExecutive;
import com.sales.crm.model.TrimmedCustomer;
import com.sales.crm.service.AreaService;
import com.sales.crm.service.BeatService;
import com.sales.crm.service.CustomerService;
import com.sales.crm.service.UserService;

@Controller
@RequestMapping("/web/beatWeb")
public class BeatWebController {

	@Autowired
	BeatService beatService;
	
	@Autowired
	AreaService areaService;
	
	@Autowired
	HttpSession httpSession;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping(value="/{beatID}")
	public ModelAndView get(@PathVariable int beatID){
		Beat beat = beatService.getBeat(beatID);
		return new ModelAndView("/beat_details", "beat", beat);
		
	}
	
	@RequestMapping(value="/createBeatForm", method = RequestMethod.GET)  
	public ModelAndView createBeatForm(Model model){
		List<Area> areas = areaService.getResellerAreas(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("areas", areas);
		modelMap.put("beat", new Beat());
		return new ModelAndView("/create_beat", modelMap);
	}
	
	@RequestMapping(value="/editBeatForm/{beatID}", method = RequestMethod.GET)  
	public ModelAndView editBeatForm(@PathVariable int beatID){
		List<Area> areas = areaService.getResellerAreas(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		List<Area> beatAreas = areaService.getBeatAreas(beatID);
		Beat beat = beatService.getBeat(beatID);
		beat.setAreas(beatAreas);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("areas", areas);
		modelMap.put("beat", beat);
		return new ModelAndView("/edit_beat", modelMap);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("beat") Beat beat){
		if(beat.getCoverageSchedule().equals("-1")){
			beat.setCoverageSchedule("");
		}
		beat.setResellerID(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		String msg = "";
		try{
			beatService.createBeat(beat);
		}catch(Exception exception){
			msg = "New Beat could not be created successfully, please contact System Administrator. ";
		}
		return new ModelAndView("/create_beat_conf", "msg", msg);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("beat") Beat beat) {
		if (beat.getCoverageSchedule().equals("-1")) {
			beat.setCoverageSchedule("");
		}
		String msg = "";
		try {
			beatService.updateBeat(beat);
		} catch (Exception exception) {
			msg = "Beat details could not be updated successfully, please contact System Administrator. ";
		}
		Map<String, String> modelMap = new HashMap<String, String>();
		modelMap.put("msg", msg);
		modelMap.put("beatID", String.valueOf(beat.getBeatID()));
		return new ModelAndView("/edit_beat_conf", "map", modelMap);
	}
	
	@GetMapping(value="/delete/{beatID}")
	public ModelAndView delete(@PathVariable int beatID){
		String msg = "";
		try{
			beatService.deleteBeat(beatID);
		}catch(Exception exception){
			msg = "Beat could not be successfully removed, please contact System Administrator";
		}
		return new ModelAndView("/delete_beat_conf","msg", msg);  
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable int resellerID){
		List<Beat> beats = beatService.getResellerBeats(resellerID);
		return new ModelAndView("/beat_list","beats", beats);  
	}
	
	@GetMapping(value="/beat-customers/list")
	public ModelAndView getBeatCustomers(){
		List<Beat> beats = beatService.getResellerBeats(Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID"))));
		return new ModelAndView("/beat_customers_list","beats", beats);  
	}
	
	@GetMapping(value="/assignCustomerForm") 
	public ModelAndView getAssignBeatToCustomerForm(){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<Beat> beats = beatService.getResellerBeats(resellerID);
		List<TrimmedCustomer> customers = customerService.getCustomersNotAssignedToAnyBeat(resellerID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("beats", beats);
		modelMap.put("customers", customers);
		modelMap.put("beat", new Beat());
		return new ModelAndView("/assign_beats_to_customer", modelMap);
	}
	
	@PostMapping(value="/assignBeatToCustomers")
	public ModelAndView assignBeatToCustomers(@ModelAttribute("beat") Beat beat){
		String msg = "";
		try{
			beatService.assignBeatToCustomers(beat.getBeatID(), beat.getCustomerIDs());
		}catch(Exception exception){
			msg = "Customers could not be successfully assigned to beat. Please contact System Administrator";
		}
		return new ModelAndView("/assign_beat_customers_conf", "msg", msg);
	}
	
	@GetMapping(value="/assignedBeatCustomerEditForm/{beatID}") 
	public ModelAndView editAssignedBeatToCustomerForm(@PathVariable int beatID){
		int resellerID = Integer.parseInt(String.valueOf(httpSession.getAttribute("resellerID")));
		List<TrimmedCustomer> customers = customerService.getCustomersBeatAssignmentForEdit(beatID, resellerID);
		Beat beat = beatService.getBeat(beatID);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("customers", customers);
		modelMap.put("beat", beat);
		return new ModelAndView("/edit_assigned_beats_to_customer", modelMap);
	}
	
	@PostMapping(value="/updateAssignedBeatToCustomers")
	public ModelAndView updateAssignedBeatToCustomers(@ModelAttribute("beat") Beat beat){
		String msg = "";
		try{
			beatService.updateAssignedBeatToCustomers(beat.getBeatID(), beat.getCustomerIDs());
		}catch(Exception exception){
			msg = "Assigned customers to beat could not be updated successfully. Please contact System Administrator";
		}
		return new ModelAndView("/update_beat_customers_conf", "msg", msg);
	}
	
	@GetMapping(value="/deleteAssignedBeatCustomerLink/{beatID}")
	public ModelAndView deleteAssignedBeatCustomerLink(@PathVariable int beatID){
		String msg = "";
		try{
			beatService.deleteAssignedBeatCustomerLink(beatID);
		}catch(Exception exception){
			msg = "Beats associated to Sales Executive could not be removed successfully. Please contact System Administrator.";
		}
		return new ModelAndView("/remove_beat_customers_conf","msg", msg); 
	}
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

		binder.registerCustomEditor(List.class, "areas", new CustomCollectionEditor(List.class) {
			@Override
			protected Object convertElement(Object element) {
				Area area = new Area();
				area.setAreaID(Integer.valueOf(String.valueOf(element)));

				return area;
			}
		});
	}
}
