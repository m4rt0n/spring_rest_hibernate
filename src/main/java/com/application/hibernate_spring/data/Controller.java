package com.application.hibernate_spring.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public @ResponseBody String hello() {
		return service.hello();
	}

	@PostMapping("/addpeople")
	public void addPeople() {
		service.saveOrUpdate(new Person("John"));
		service.saveOrUpdate(new Person("Jane"));
		service.saveOrUpdate(new Person("Jim"));
	}

	@GetMapping("/getall")
	public Iterable<Person> getAllPerson() {
		return service.findAll();
	}

	@GetMapping("/getbyid/{id}")
	public Person getPersonById(@PathVariable("id") Long id) throws PersonNotFoundException {
		return service.findById(id);
	}

	@PostMapping("/save")
	public void saveOrUpdatePerson(@RequestBody Person newPerson) {
		service.saveOrUpdate(newPerson);
	}

	@DeleteMapping("/deletebyid/{id}")
	public void deletePerson(@PathVariable("id") Long id) {
		service.deleteById(id);
	}

	@DeleteMapping("/deleteall")
	public void deleteAllPerson() {
		service.deleteAll();
	}

}