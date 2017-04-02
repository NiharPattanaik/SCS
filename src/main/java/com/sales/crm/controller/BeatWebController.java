package com.sales.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sales.crm.model.Beat;
import com.sales.crm.service.BeatService;

@Controller
@RequestMapping("/beatWeb")
public class BeatWebController {

	@Autowired
	BeatService beatService;
	
	
	@GetMapping(value="/{beatID}")
	public ModelAndView get(@PathVariable long beatID){
		Beat beat = beatService.getBeat(beatID);
		return new ModelAndView("/beat_details", "beat", beat);
		
	}
	
	@RequestMapping(value="/createBeatForm", method = RequestMethod.GET)  
	public ModelAndView createBeatForm(Model model){
		return new ModelAndView("/create_beat", "beat", new Beat());
	}
	
	@RequestMapping(value="/editBeatForm/{beatID}", method = RequestMethod.GET)  
	public ModelAndView editBeatForm(@PathVariable long beatID){
		Beat beat = beatService.getBeat(beatID);
		return new ModelAndView("/edit_beat", "beat", beat);
	}
	
	@RequestMapping(value="/save",method = RequestMethod.POST)  
	public ModelAndView create(@ModelAttribute("beat") Beat beat){
		beatService.createBeat(beat);
		return list(beat.getResellerID());
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST) 
	public ModelAndView update(@ModelAttribute("beat") Beat beat){
		beatService.updateBeat(beat);
		return get(beat.getBeatID());
	}
	
	@DeleteMapping(value="/{beatID}")
	public void delete(@PathVariable long beatID){
		beatService.deleteBeat(beatID);
	}
	
	@GetMapping(value="/list/{resellerID}")
	public ModelAndView list(@PathVariable long resellerID){
		List<Beat> beats = beatService.getResellerBeats(resellerID);
		return new ModelAndView("/beat_list","beats", beats);  
	}
	
	
	
}
