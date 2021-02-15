package com.application.hibernate_spring.data;

import java.util.List;

import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

public interface IService {
	public List<Person> findAll();

	public Person findById(long id) throws PersonNotFoundException;

	public Person create(Person newPerson);

	public Person update(long id, Person newDetails) throws PersonNotFoundException;

	public void deleteById(long id);
}
