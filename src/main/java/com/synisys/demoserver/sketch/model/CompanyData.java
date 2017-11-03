package com.synisys.demoserver.sketch.model;

/**
 * @author Artush on 11/3/2017.
 */
public class CompanyData {

	Long id;
	String name;
	Long addressId;

	public CompanyData(Long id, String name, Long addressId) {
		this.id = id;
		this.name = name;
		this.addressId = addressId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
}
