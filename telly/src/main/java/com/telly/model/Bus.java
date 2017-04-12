package com.telly.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Size;


public class Bus implements Serializable {

	static final long serialVersionUID = 1L;

	private Long id;

	private Date date;

	@Size(min=3, max=15, message="Leave From field must be at least size 3")
	private String leaveFrom;

	@Size(min=3, max=15, message="Going To field must be at least size 3")
	private String goingTo;


	public Bus() {

	}


	public Bus(Date date, String leaveFrom, String goingTo) {
		this.date = date;
		this.leaveFrom = leaveFrom;
		this.goingTo = goingTo;

	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getLeaveFrom() {
		return leaveFrom;
	}


	public void setLeaveFrom(String leaveFrom) {
		this.leaveFrom = leaveFrom;
	}


	public String getGoingTo() {
		return goingTo;
	}


	public void setGoingTo(String goingTo) {
		this.goingTo = goingTo;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	@Override
	public String toString() {
		return "Bus [id=" + id + ", date=" + date + ", leaveFrom=" + leaveFrom + ", goingTo=" + goingTo + "]";
	}



}