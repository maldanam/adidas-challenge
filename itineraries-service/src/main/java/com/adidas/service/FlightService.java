package com.adidas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.adidas.bean.Flight;

@Service
public class FlightService {

    public List<Flight> getFlights(String from, Optional<LocalDateTime> takeoffTime) {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    ResponseEntity<List<Flight>> response = null;
	    	
	    if (takeoffTime.isPresent()) {
	    	response = restTemplate.exchange(
		    		  "http://data-service:8080/flight/search?from={city}&takeoffAfter={datetime}",
		    		  HttpMethod.GET,
		    		  null,
		    		  new ParameterizedTypeReference<List<Flight>>(){},
		    		  from,
		    		  takeoffTime.get());	    	
	    } else {
	    	response = restTemplate.exchange(
		    		  "http://data-service:8080/flight/search?from={city}",
		    		  HttpMethod.GET,
		    		  null,
		    		  new ParameterizedTypeReference<List<Flight>>(){},
		    		  from);	    	
	    }
	    
	    return response.getBody();
    }

}
