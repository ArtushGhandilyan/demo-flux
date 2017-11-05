package com.synisys.demoserver.sketch.model;

/**
 * @author Artush on 11/3/2017.
 */
public class Address {

	String city;
	String street;
	String building;

	public Address(String city, String street, String building) {
		this.city = city;
		this.street = street;
		this.building = building;
	}

	@Override
	public String toString() {
		return "Address{" +
				"city='" + city + '\'' +
				", street='" + street + '\'' +
				", building='" + building + '\'' +
				'}';
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}
}
