package com.adidas.bean;

import java.util.ArrayList;
import java.util.List;

public class Itinerary {

    private final String from;
    private final String to;
    private final List<Flight> flights;
//    private boolean complete;

    public Itinerary(String from, String to, Flight firstFlight) {
        this.from = from;
        this.to = to;
        this.flights = new ArrayList<>();
        this.flights.add(firstFlight);
    }

    public Itinerary(Itinerary incomplete, Flight aFlight) {
        this.from = incomplete.from;
        this.to = incomplete.to;
        this.flights = new ArrayList<>();
        this.flights.addAll(incomplete.getFlights());
        this.flights.add(aFlight);
    }

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}

	public boolean isComplete() {
		return this.flights.get(this.flights.size()-1).getToCity().equals(this.to);
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void addFlight(Flight aFlight) {
		this.flights.add(aFlight);
	}
	
	public Flight getLastFlight() {
		return this.flights.get(this.flights.size()-1);
	}
	
	@Override
	public String toString() {
		return 	String.format("Itinerary from %s to %s.", this.from, this.to);
	}
	
	
}
