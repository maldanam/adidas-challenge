package com.adidas.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.adidas.entity.Flight;
import com.adidas.enums.CityRoleEnum;
import com.adidas.repository.FlightRepository;

@RestController
@RequestMapping(path="/flight") 
public class FlightController {
	@Autowired 	
	private FlightRepository flightRepository;
	
	@GetMapping(path="/search")
	public @ResponseBody Iterable<Flight> getFlights(@RequestParam String city, 
													 @RequestParam CityRoleEnum cityRole, 
													 @RequestParam(required=false) 
													 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Optional<LocalDateTime> takeoffAfter) {
		
		List<Flight> result = null;
		
		switch (cityRole) {
		case FROM:
			if (takeoffAfter.isPresent()) {
				result = flightRepository.findByFromCityIgnoreCaseAndTakeoffTimeGreaterThan(city, takeoffAfter.get());			
			} else {
				result = flightRepository.findByFromCityIgnoreCase(city);			
			}
			break;
		case TO:
			if (takeoffAfter.isPresent()) {
				result = flightRepository.findByToCityIgnoreCaseAndTakeoffTimeGreaterThan(city, takeoffAfter.get());			
			} else {
				result = flightRepository.findByToCityIgnoreCase(city);			
			}
			break;			
		}
		return result;
	}

}
