package com.application.hibernate_spring.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

@RestController
@RequestMapping(path = "/person")
public class Controller {
	@Autowired
	private IService service;

	@GetMapping("/hello")
	public @ResponseBody String greeting() {
		return "Hello, World";
	}

	@PostMapping("/addpeople")
	public void dummyAddPerson() {
		service.create(new Person("a"));
		service.create(new Person("b"));
		service.create(new Person("c"));
	}

	@GetMapping("/all")
	public Iterable<Person> getAllPerson() {
		return service.findAll();
	}

	@GetMapping("/{id}")
	public Person getPersonById(@PathVariable("id") Long id) throws PersonNotFoundException {
		return service.findById(id);
	}

	@PostMapping("/create")
	public void createPerson(@RequestBody Person newPerson) {
		service.create(newPerson);
	}

	@PutMapping("/{id}")
	public void updatePerson(@PathVariable("id") Long id, @RequestBody Person newDetails)
			throws PersonNotFoundException {
		service.update(id, newDetails);
	}

	@DeleteMapping("/{id}")
	public void deletePerson(@PathVariable("id") Long id) {
		service.deleteById(id);
	}

}