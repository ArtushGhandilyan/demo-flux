package com.synisys.demoserver.sketch.model;

import java.util.List;

/**
 * @author Artush on 11/3/2017.
 */
public class CompanyInfo {

	String name;
	List<UserInfo> users;
	Address address;

	public CompanyInfo(String name, List<UserInfo> users, Address address) {
		this.name = name;
		this.users = users;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserInfo> getUsers() {
		return users;
	}

	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}
