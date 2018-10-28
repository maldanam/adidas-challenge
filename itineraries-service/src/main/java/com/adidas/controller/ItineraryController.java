package com.adidas.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.bean.BestItineraries;
import com.adidas.bean.CompleteItinerary;
import com.adidas.service.ItineraryService;

@RestController
@RequestMapping(path="/itinerary")
public class ItineraryController {

	@Autowired 	
	private ItineraryService itineraryService;

    @GetMapping(path="/")
    public BestItineraries bestItineraries(@RequestParam(value="from", required=true) String from,
    						   		 	   @RequestParam(value="to", required=true) String to) {
    	
    	BestItineraries result = new BestItineraries(from, to);
    	
    	List<CompleteItinerary> allItineraries = itineraryService.retrieveAll(from, to);
    	
    	Optional<CompleteItinerary> lessConnectionsItinerary = allItineraries.stream()
    													   .sorted((i1, i2) -> i1.getNumConnections().compareTo(i2.getNumConnections()))
    													   .findFirst();
    	
		result.setLessConnectionsItinerary(lessConnectionsItinerary);
    	
    	Optional<CompleteItinerary> lessTimeItinerary = allItineraries.stream()
				   											  .sorted((i1, i2) -> i1.getTotalTime().compareTo(i2.getTotalTime()))
				   											  .findFirst();

    	result.setLessTimeItinerary(lessTimeItinerary);

    	return result;
    }
    
}
