package com.application.hibernate_spring.data;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

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

	public List<Person> findAllByOrderByNameAsc() {
		List<Person> list = (List<Person>) repo.findAll();
		Comparator<Person> compareByName = (Person p1, Person p2) -> p1.getName().compareTo(p2.getName());
		Collections.sort(list, compareByName);
		return list;
	}

	public Person findByName(String name) throws PersonNotFoundException {
		List<Person> list = (List<Person>) repo.findAll();
		Optional<Person> p = list.stream().filter(e -> e.getName().equals(name)).findFirst();
		return p.orElseThrow(() -> new PersonNotFoundException(name));
	}

	public Person findById(long id) throws PersonNotFoundException {
		return repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}

	public Person saveOrUpdate(Person person) {
		return repo.save(person);
	}

	public void deleteById(long id) {
		repo.deleteById(id);
	}

	public void deleteAll() {
		repo.deleteAll();
	}

}
