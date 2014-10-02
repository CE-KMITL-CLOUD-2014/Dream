package com.dreamService.core.entity;

import java.io.Serializable;

public class CustomerDetails implements Serializable {
	private long id;

	private String name;

	public CustomerDetails() {
		super();
	}

	public CustomerDetails(final String name) {
		super();

		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
