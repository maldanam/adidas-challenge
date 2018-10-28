package com.adidas.bean;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UnderConstructionItinerary implements Itinerary {

    private final String from;
    private final String to;
    private final List<Flight> flights;
    private final List<Flight> targetFlights;
    private final LocalDateTime lastTargetFlightTakeoffTime;
//    private boolean complete;

    public UnderConstructionItinerary(String from, String to, Flight firstFlight, List<Flight> targetFlights) {
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

    public UnderConstructionItinerary(UnderConstructionItinerary incomplete, Flight aFlight) {
        this.from = incomplete.from;
        this.to = incomplete.to;
        this.flights = new ArrayList<>();
        this.flights.addAll(incomplete.flights);
        this.flights.add(aFlight);
        this.targetFlights = new ArrayList<>();
        this.targetFlights.addAll(incomplete.targetFlights);
    	this.lastTargetFlightTakeoffTime = incomplete.lastTargetFlightTakeoffTime;
    }

	/* (non-Javadoc)
	 * @see com.adidas.bean.Itinerary#getFrom()
	 */
	@Override
	public String getFrom() {
		return from;
	}

	/* (non-Javadoc)
	 * @see com.adidas.bean.Itinerary#getTo()
	 */
	@Override
	public String getTo() {
		return to;
	}

	public List<Flight> getFlights() {
		return this.flights;
	}
	
	@Override
	public List<Flight> getConnections() {
		return this.flights;
	}
	
	/* (non-Javadoc)
	 * @see com.adidas.bean.Itinerary#getNumConnections()
	 */
	@Override
	public Integer getNumConnections() {
		return this.getFlights().size();
	}
	
	/* (non-Javadoc)
	 * @see com.adidas.bean.Itinerary#getTotalTime()
	 */
	@Override
	public Duration getTotalTime() {
		Duration result = null;
		
		if (!this.flights.isEmpty()) {
			LocalDateTime firstFlightTakeoffTime = this.getFirstFlight().getTakeoffTime();
			LocalDateTime lastFlightLandingTime = this.getLastFlight().getLandingTime();
			
			result = Duration.between(firstFlightTakeoffTime, lastFlightLandingTime);
		}
		
		return result;
	}
	
	public boolean isComplete() {
		return this.flights.get(this.flights.size()-1).getToCity().equals(this.to);
	}
	
	public boolean isCompletable() {
		return this.isComplete() || this.getLastFlight().getLandingTime().isBefore(lastTargetFlightTakeoffTime);
	}

	public Flight getFirstFlight() {
		return this.flights.get(0);
	}
	
	public Flight getLastFlight() {
		return this.flights.get(this.flights.size()-1);
	}
	
	@Override
	public String toString() {
		return String.format("Itinerary from %s to %s.", this.from, this.to);
	}

	
}
