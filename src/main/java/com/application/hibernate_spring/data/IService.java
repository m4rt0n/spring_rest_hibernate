package com.application.hibernate_spring.data;

import java.util.List;

import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

public interface IService {
	public List<Person> findAll();

	public Person findById(long id) throws PersonNotFoundException;

	public Person findByName(String name) throws PersonNotFoundException;

	public List<Person> findAllByOrderByNameAsc();

	public void deleteById(long id);

	public void deleteAll();

	public Person saveOrUpdate(Person person);

	public String hello();
}
