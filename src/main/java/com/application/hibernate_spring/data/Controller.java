package com.application.hibernate_spring.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

@RestController
@RequestMapping(path = "/person")
public class Controller {
	@Autowired
	private IRepository repo;

	@PostMapping("/addpeople")
	public void dummyAddPerson() {
		repo.save(new Person("a"));
		repo.save(new Person("b"));
		repo.save(new Person("c"));
	}

	@GetMapping("/all")
	public Iterable<Person> getAllPerson() {
		return repo.findAll();
	}

	@GetMapping("/{id}")
	public Person getPersonById(@PathVariable("id") Long id) throws PersonNotFoundException {
		return repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}

	@PostMapping("/create")
	public void createPerson(@RequestBody Person newPerson) {
		repo.save(newPerson);
	}

	@PutMapping("/{id}")
	public void updatePerson(@PathVariable("id") Long id, @RequestBody Person newDetails)
			throws PersonNotFoundException {
		Person p = repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		p.setName(newDetails.getName());
		repo.save(p);
	}

	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable("id") Long id) throws PersonNotFoundException {
		Person p = repo.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		repo.delete(p);
	}

}