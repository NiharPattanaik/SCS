package com.sales.crm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Area;
import com.sales.crm.model.Beat;
import com.sales.crm.service.AreaService;
import com.sales.crm.service.BeatService;

@Controller
@RequestMapping("/beatWeb")
public class BeatWebController {

	@Autowired
	BeatService beatService;
	
	@Autowired
	AreaService areaService;
	
	@GetMapping(value="/{beatID}")
	public ModelAndView get(@PathVariable int beatID){
		Beat beat = beatService.getBeat(beatID);
		return new ModelAndView("/beat_details", "beat", beat);
		
	}
	
	@RequestMapping(value="/createBeatForm", method = RequestMethod.GET)  
	public ModelAndView createBeatForm(Model model){
		List<Area> areas = areaService.getResellerAreas(13);
		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("areas", areas);
		modelMap.put("beat", new Beat());
		return new ModelAndView("/create_beat", modelMap);
	}
	
	@RequestMapping(value="/editBeatForm/{beatID}", method = RequestMethod.GET)  
	public ModelAndView editBeatForm(@PathVariable int beatID){
		List<Area> areas = areaService.getResellerAreas(13);
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
		beatService.createBeat(beat);
		return list(beat.getResellerID());
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("beat") Beat beat){
		if(beat.getCoverageSchedule().equals("-1")){
			beat.setCoverageSchedule("");
		}
		beatService.updateBeat(beat);
		return get(beat.getBeatID());
	}
	
	@DeleteMapping(value="/{beatID}")
	public void delete(@PathVariable int beatID){
		beatService.deleteBeat(beatID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable int resellerID){
		List<Beat> beats = beatService.getResellerBeats(resellerID);
		return new ModelAndView("/beat_list","beats", beats);  
	}
	
	@InitBinder
	  public void initBinder(WebDataBinder  binder){
	    binder.registerCustomEditor(List.class, "areas", new CustomCollectionEditor(List.class)
	    {
	      @Override
	      protected Object convertElement(Object element)
	      {
	         Area area = new Area();
	         area.setAreaID(Integer.valueOf(String.valueOf(element)));

	       
	        return area;
	      }


	    });  
	}
	
}
