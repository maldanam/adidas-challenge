package com.adidas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adidas.bean.Flight;
import com.adidas.bean.Itinerary;

@Service
public class ItineraryService {
	
	@Autowired 	
	private FlightService flightService;

	public List<Itinerary> retrieveAll(String from, String to) {
		
    	List<Itinerary> result = new ArrayList<>();
    	
    	Map<Boolean, List<Itinerary>> itineraries = flightService.getFlights(from, Optional.ofNullable(null))
    				 		  									 .stream()
    				 		  									 .map(f -> new Itinerary(from, to, f))
    				 		  									 .collect(Collectors.partitioningBy(Itinerary::isComplete));
    	
    	List<Itinerary> complete = itineraries.get(Boolean.TRUE);
    	List<Itinerary> incomplete = itineraries.get(Boolean.FALSE);
    	
    	result.addAll(complete);
    	if (!incomplete.isEmpty()) {
        	result.addAll(completeIfPosible(incomplete));
    	}
    	    	
        return result;
	}
	
	private List<Itinerary> completeIfPosible(List<Itinerary> incompleteItineraries) {
		
		List<Itinerary> result = new ArrayList<>();

		Map<Boolean, List<Itinerary>> itineraries = incompleteItineraries.stream()
				  											  .map(i -> completeIfPosible(i))
				  											  .flatMap(List::stream)
				  											  .collect(Collectors.partitioningBy(Itinerary::isComplete));
		
	   	List<Itinerary> complete = itineraries.get(Boolean.TRUE);
	   	List<Itinerary> incomplete = itineraries.get(Boolean.FALSE);
	   	
	   	result.addAll(complete);
    	if (!incomplete.isEmpty()) {
        	result.addAll(completeIfPosible(incomplete));
    	}
    	return result;
	}

	private List<Itinerary> completeIfPosible(Itinerary incompleteItinerary) {
    	
		List<Itinerary> result = new ArrayList<>();

    	Flight lastFlight = incompleteItinerary.getLastFlight();
    	
		List<Flight> nextFlights = flightService.getFlights(lastFlight.getToCity(), Optional.of(lastFlight.getLandingTime()));
		if (!nextFlights.isEmpty()) {
			
	    	Map<Boolean, List<Itinerary>> itineraries = nextFlights.stream()
					 											   .map(f -> new Itinerary(incompleteItinerary, f))
					 											   .collect(Collectors.partitioningBy(Itinerary::isComplete));

		   	List<Itinerary> complete = itineraries.get(Boolean.TRUE);
		   	List<Itinerary> incomplete = itineraries.get(Boolean.FALSE);
		   	
		   	result.addAll(complete);
	    	if (!incomplete.isEmpty()) {
	        	result.addAll(completeIfPosible(incomplete));
	    	}
   	    	
		}
        return result;
	}

}
