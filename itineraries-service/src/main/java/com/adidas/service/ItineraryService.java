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
    	
    	List<Flight> targetFlights = flightService.getFlightsTo(to, Optional.ofNullable(null));
    	
    	Map<Boolean, List<Itinerary>> itineraries = flightService.getFlightsFrom(from, Optional.ofNullable(null))
    				 		  									 .stream()
    				 		  									 .map(f -> new Itinerary(from, to, f, targetFlights))
    				 		  									 .collect(Collectors.partitioningBy(Itinerary::isComplete));
    	
    	List<Itinerary> completeItineraries = itineraries.get(Boolean.TRUE);
    	List<Itinerary> incompleteItineraries = itineraries.get(Boolean.FALSE);
    	
    	result.addAll(completeItineraries);
    	if (!incompleteItineraries.isEmpty()) {
    		
        	result.addAll(completeIfPosible(completableItinerariesOf(incompleteItineraries)));
    	}
    	    	
        return result;
	}
	
	private List<Itinerary> completeIfPosible(List<Itinerary> incompleteItineraries) {
		
		List<Itinerary> result = new ArrayList<>();

		Map<Boolean, List<Itinerary>> itineraries = incompleteItineraries.stream()
				  											  .map(i -> completeIfPosible(i))
				  											  .flatMap(List::stream)
				  											  .collect(Collectors.partitioningBy(Itinerary::isComplete));
		
	   	List<Itinerary> completeItineraries = itineraries.get(Boolean.TRUE);
	   	List<Itinerary> stillIncompleteItineraries = itineraries.get(Boolean.FALSE);
	   	
	   	result.addAll(completeItineraries);
    	if (!stillIncompleteItineraries.isEmpty()) {
    		
        	result.addAll(completeIfPosible(completableItinerariesOf(stillIncompleteItineraries)));
    	}
    	return result;
	}

	private List<Itinerary> completeIfPosible(Itinerary incompleteItinerary) {
    	
		List<Itinerary> result = new ArrayList<>();

    	Flight lastFlight = incompleteItinerary.getLastFlight();
    	
		List<Flight> nextFlights = flightService.getFlightsFrom(lastFlight.getToCity(), Optional.of(lastFlight.getLandingTime()));
		if (!nextFlights.isEmpty()) {
			
	    	Map<Boolean, List<Itinerary>> itineraries = nextFlights.stream()
					 											   .map(f -> new Itinerary(incompleteItinerary, f))
					 											   .collect(Collectors.partitioningBy(Itinerary::isComplete));

		   	List<Itinerary> completeItineraries = itineraries.get(Boolean.TRUE);
		   	List<Itinerary> incompleteItineraries = itineraries.get(Boolean.FALSE);
		   	
		   	result.addAll(completeItineraries);
	    	if (!incompleteItineraries.isEmpty()) {
	    		
	        	result.addAll(completeIfPosible(completableItinerariesOf(incompleteItineraries)));
	    	}
   	    	
		}
        return result;
	}

	private List<Itinerary> completableItinerariesOf(List<Itinerary> incompleteItineraries) {
		
		return incompleteItineraries.stream()
									.collect(Collectors.partitioningBy(Itinerary::isCompletable))
									.get(Boolean.TRUE);
	}
}
