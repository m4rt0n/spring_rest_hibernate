package com.application.hibernate_spring.model;

public class PersonNotFoundException extends Exception {
	private long id;

	public PersonNotFoundException(long id) {
		super(String.format("person is not found with id: ", id));
	}
}