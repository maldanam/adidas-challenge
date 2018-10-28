package com.adidas.bean;

import java.util.Optional;

public class BestItineraries {

	private final String name;
	private final String from;
	private final String to;
	private Optional<CompleteItinerary> lessTimeItinerary;
	private Optional<CompleteItinerary> lessConnectionsItinerary;	
	
	public BestItineraries(String from, String to) {
		this.from = from;
		this.to = to;
		this.name = String.format("Best itineraries from %s to %s.", this.from, this.to);
	}

	public Optional<CompleteItinerary> getLessTimeItinerary() {
		return lessTimeItinerary;
	}

	public void setLessTimeItinerary(Optional<CompleteItinerary> lessTimeItinerary) {
		this.lessTimeItinerary = lessTimeItinerary;
	}

	public Optional<CompleteItinerary> getLessConnectionsItinerary() {
		return lessConnectionsItinerary;
	}

	public void setLessConnectionsItinerary(Optional<CompleteItinerary> lessConnectionsItinerary) {
		this.lessConnectionsItinerary = lessConnectionsItinerary;
	}

	public String getName() {
		return name;
	}

	public String getFrom() {
		return from;
	}

	public String getTo() {
		return to;
	}
	
}
