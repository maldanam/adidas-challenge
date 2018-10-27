package com.adidas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.bean.Itinerary;
import com.adidas.service.ItineraryService;

@RestController
@RequestMapping(path="/itinerary")
public class ItineraryController {

	@Autowired 	
	private ItineraryService itineraryService;

    @GetMapping(path="/")
    public List<Itinerary> bestItineraries(@RequestParam(value="from", required=true) String from,
    						   		 	   @RequestParam(value="to", required=true) String to) {
    	
    	return itineraryService.retrieveAll(from, to);
    }
    
}
