package com.adidas.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class Flight {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    @Column(name="from_city")
    private String fromCity;

    @Column(name="to_city")
	private String toCity;
    
    @Column(name="takeoff_time")
    private LocalDateTime takeoffTime;

    @Column(name="landing_time")
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

