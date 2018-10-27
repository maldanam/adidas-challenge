package com.adidas.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.entity.Flight;
import com.adidas.repository.FlightRepository;

@RestController
@RequestMapping(path="/flight") 
public class FlightController {
	@Autowired 	
	private FlightRepository flightRepository;
	
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Flight> getFlightsFrom(@RequestParam String from, 
														 @RequestParam(required=false) 
														 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime takeoffAfter) {
		
		if (takeoffAfter == null) {
			return flightRepository.findByFromCityIgnoreCase(from);			
		} else {
			return flightRepository.findByFromCityIgnoreCaseAndTakeoffTimeGreaterThan(from, takeoffAfter);			
		}
	}

}