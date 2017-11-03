package com.synisys.demoserver.sketch.model;

/**
 * @author Artush on 11/3/2017.
 */
public class Data {

	private Long id;
	private String name;

	public Data(Long id, String name) {
		this.id = id;
		this.name = name;
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
}
