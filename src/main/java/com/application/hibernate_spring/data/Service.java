package com.application.hibernate_spring.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

//@Service
@Component
public class Service implements IService {
	@Autowired
	private IRepository repo;

	public List<Person> findAll() {
		return (List<Person>) repo.findAll();
	}

	public Person findById(long id) throws PersonNotFoundException {
		return repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}

	public Person create(Person newPerson) {
		return repo.save(newPerson);
	}

	public Person update(long id, Person newDetails) throws PersonNotFoundException {
		Person oldDetails = repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		oldDetails.setName(newDetails.getName());
		return repo.save(oldDetails);
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}
}
