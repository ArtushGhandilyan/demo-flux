package com.synisys.demoserver.sketch.model;

import java.util.Date;

/**
 * @author Artush on 11/3/2017.
 */
public class UserInfo {

	String name;
	String role;
	Date startDate;
	String address;

	public UserInfo(String name, String role, Date startDate, String address) {
		this.name = name;
		this.role = role;
		this.startDate = startDate;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
