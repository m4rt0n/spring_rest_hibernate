package com.application.hibernate_spring.model;

public class PersonNotFoundException extends Exception {
	private long id;
	private String name;

	public PersonNotFoundException(long id) {
		super(String.format("person is not found with id: %s", id));
	}

	public PersonNotFoundException(String name) {
		super(String.format("person is not found with name: %s", name));
	}
}