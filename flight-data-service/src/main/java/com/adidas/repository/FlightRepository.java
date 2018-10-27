package com.adidas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.adidas.entity.Flight;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface FlightRepository extends CrudRepository<Flight, Integer> {
	
	List<Flight> findByFromCityIgnoreCase(String city);
	List<Flight> findByFromCityIgnoreCaseAndTakeoffTimeGreaterThan(String city, LocalDateTime takeoffTime);

}
