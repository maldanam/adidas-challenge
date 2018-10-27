package com.adidas.bean;

import java.time.LocalDateTime;

public class Flight {

	private Integer id;

    private String fromCity;

	private String toCity;
    
    private LocalDateTime takeoffTime;

    private LocalDateTime landingTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public LocalDateTime getTakeoffTime() {
		return takeoffTime;
	}

	public void setTakeoffTime(LocalDateTime takeoffTime) {
		this.takeoffTime = takeoffTime;
	}

	public LocalDateTime getLandingTime() {
		return landingTime;
	}

	public void setLandingTime(LocalDateTime landingTime) {
		this.landingTime = landingTime;
	}
    
}

