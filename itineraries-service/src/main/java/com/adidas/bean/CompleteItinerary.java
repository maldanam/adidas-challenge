package com.adidas.bean;

import java.time.Duration;
import java.util.List;

public class CompleteItinerary implements Itinerary {

	private final Itinerary instance;
	
	public CompleteItinerary(Itinerary aCompleteItinerary) {
		this.instance = aCompleteItinerary;
	}
	
	@Override
	public String getFrom() {
		return this.instance.getFrom();
	}

	@Override
	public String getTo() {
		return this.instance.getTo();
	}

	@Override
	public List<Flight> getConnections() {
		return this.instance.getConnections();
	}

	@Override
	public Integer getNumConnections() {
		return this.instance.getNumConnections();
	}

	@Override
	public Duration getTotalTime() {
		return this.instance.getTotalTime();
	}

}
