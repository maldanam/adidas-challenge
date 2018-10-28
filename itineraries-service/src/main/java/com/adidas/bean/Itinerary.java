package com.adidas.bean;

import java.time.Duration;
import java.util.List;

public interface Itinerary {

	String getFrom();

	String getTo();

	List<Flight> getConnections();

	Integer getNumConnections();

	Duration getTotalTime();

}