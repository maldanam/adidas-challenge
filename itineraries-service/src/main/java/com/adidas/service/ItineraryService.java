package com.adidas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adidas.bean.CompleteItinerary;
import com.adidas.bean.Flight;
import com.adidas.bean.UnderConstructionItinerary;

@Service
public class ItineraryService {
	
	@Autowired 	
	private FlightService flightService;

	public List<CompleteItinerary> retrieveAll(String from, String to) {
		
    	List<CompleteItinerary> result = new ArrayList<>();
    	
    	List<Flight> targetFlights = flightService.getFlightsTo(to, Optional.ofNullable(null));
    	
    	Map<Boolean, List<UnderConstructionItinerary>> itineraries = flightService.getFlightsFrom(from, Optional.ofNullable(null))
    				 		  									 .stream()
    				 		  									 .map(f -> new UnderConstructionItinerary(from, to, f, targetFlights))
    				 		  									 .collect(Collectors.partitioningBy(UnderConstructionItinerary::isComplete));
    	
    	List<UnderConstructionItinerary> completeItineraries = itineraries.get(Boolean.TRUE);
    	List<UnderConstructionItinerary> incompleteItineraries = itineraries.get(Boolean.FALSE);
    	
    	result.addAll(toCompleteItineraries(completeItineraries));
    	if (!incompleteItineraries.isEmpty()) {
    		
        	result.addAll(toCompleteItineraries(completeIfPosible(completableItinerariesOf(incompleteItineraries))));
    	}
    	    	
        return result;
	}
	
	private List<UnderConstructionItinerary> completeIfPosible(List<UnderConstructionItinerary> incompleteItineraries) {
		
		List<UnderConstructionItinerary> result = new ArrayList<>();

		Map<Boolean, List<UnderConstructionItinerary>> itineraries = incompleteItineraries.stream()
				  											  .map(i -> completeIfPosible(i))
				  											  .flatMap(List::stream)
				  											  .collect(Collectors.partitioningBy(UnderConstructionItinerary::isComplete));
		
	   	List<UnderConstructionItinerary> completeItineraries = itineraries.get(Boolean.TRUE);
	   	List<UnderConstructionItinerary> stillIncompleteItineraries = itineraries.get(Boolean.FALSE);
	   	
	   	result.addAll(completeItineraries);
    	if (!stillIncompleteItineraries.isEmpty()) {
    		
        	result.addAll(completeIfPosible(completableItinerariesOf(stillIncompleteItineraries)));
    	}
    	return result;
	}

	private List<UnderConstructionItinerary> completeIfPosible(UnderConstructionItinerary incompleteItinerary) {
    	
		List<UnderConstructionItinerary> result = new ArrayList<>();

    	Flight lastFlight = incompleteItinerary.getLastFlight();
    	
		List<Flight> nextFlights = flightService.getFlightsFrom(lastFlight.getToCity(), Optional.of(lastFlight.getLandingTime()));
		if (!nextFlights.isEmpty()) {
			
	    	Map<Boolean, List<UnderConstructionItinerary>> itineraries = nextFlights.stream()
					 											   .map(f -> new UnderConstructionItinerary(incompleteItinerary, f))
					 											   .collect(Collectors.partitioningBy(UnderConstructionItinerary::isComplete));

		   	List<UnderConstructionItinerary> completeItineraries = itineraries.get(Boolean.TRUE);
		   	List<UnderConstructionItinerary> incompleteItineraries = itineraries.get(Boolean.FALSE);
		   	
		   	result.addAll(completeItineraries);
	    	if (!incompleteItineraries.isEmpty()) {
	    		
	        	result.addAll(completeIfPosible(completableItinerariesOf(incompleteItineraries)));
	    	}
   	    	
		}
        return result;
	}

	private List<UnderConstructionItinerary> completableItinerariesOf(List<UnderConstructionItinerary> incompleteItineraries) {
		
		return incompleteItineraries.stream()
									.collect(Collectors.partitioningBy(UnderConstructionItinerary::isCompletable))
									.get(Boolean.TRUE);
	}

	private List<CompleteItinerary> toCompleteItineraries(List<UnderConstructionItinerary> someCompletedItineraries) {
		return someCompletedItineraries.stream().map(i -> new CompleteItinerary(i)).collect(Collectors.toList());
	}
}
