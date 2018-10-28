package com.adidas.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Itinerary {

    private final String from;
    private final String to;
    private final List<Flight> flights;
    private final List<Flight> targetFlights;
    private final LocalDateTime lastTargetFlightTakeoffTime;
//    private boolean complete;

    public Itinerary(String from, String to, Flight firstFlight, List<Flight> targetFlights) {
        this.from = from;
        this.to = to;
        this.flights = new ArrayList<>();
        this.flights.add(firstFlight);
        this.targetFlights = new ArrayList<>();
        this.targetFlights.addAll(targetFlights);
        if (targetFlights.isEmpty()) {
        	this.lastTargetFlightTakeoffTime = LocalDateTime.MIN;
        } else {
            this.lastTargetFlightTakeoffTime = this.targetFlights.stream().map(f -> f.getTakeoffTime()).max(LocalDateTime::compareTo).get();        	
        }
    }

    public Itinerary(Itinerary incomplete, Flight aFlight) {
        this.from = incomplete.from;
        this.to = incomplete.to;
        this.flights = new ArrayList<>();
        this.flights.addAll(incomplete.flights);
        this.flights.add(aFlight);
        this.targetFlights = new ArrayList<>();
        this.targetFlights.addAll(incomplete.targetFlights);
    	this.lastTargetFlightTakeoffTime = incomplete.lastTargetFlightTakeoffTime;
    }

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}
	
	public boolean isComplete() {
		return this.flights.get(this.flights.size()-1).getToCity().equals(this.to);
	}
	
	public boolean isCompletable() {
		return this.isComplete() || this.getLastFlight().getLandingTime().isBefore(lastTargetFlightTakeoffTime);
	}

	public Flight getLastFlight() {
		return this.flights.get(this.flights.size()-1);
	}
	
	@Override
	public String toString() {
		return 	String.format("Itinerary from %s to %s.", this.from, this.to);
	}
	
	
}
