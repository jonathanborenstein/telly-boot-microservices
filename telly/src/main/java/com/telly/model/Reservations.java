package com.telly.model;

import java.io.Serializable;


public class Reservations implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long busId;
	
	private String email;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBusId() {
		return busId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}