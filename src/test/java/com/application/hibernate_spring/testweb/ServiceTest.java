package com.application.hibernate_spring.testweb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.application.hibernate_spring.data.IService;
import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

@SpringBootTest
class ServiceTest {

	@Autowired
	private IService service;
	private List<Person> list = new ArrayList<>();

	@BeforeEach
	public void setup() {
		service.deleteAll();
		Person john = new Person("John");
		Person jane = new Person("Jane");
		Person jim = new Person("Jim");
		list.add(john);
		list.add(jane);
		list.add(jim);
		list.forEach(p -> service.saveOrUpdate(p));
	}

	@Test
	public void findAll() {
		assertNotNull(list);
		assertEquals(3, list.size());
	}

	@Test
	public void save() {
		Person newPerson = new Person("NewName");
		service.saveOrUpdate(newPerson);
		assertNotNull(newPerson.getId());
	}

	@Test
	public void update() {
		Person oldPerson = list.get(0);
		Person updatePerson = new Person("updateName");
		oldPerson.setName(updatePerson.getName());
		service.saveOrUpdate(oldPerson);
		assertEquals("updateName", oldPerson.getName());
	}

	@Test
	public void getById() throws PersonNotFoundException {
		Person p1 = list.get(0);
		Person p2 = service.findById(p1.getId());
		assertEquals(p1.getId(), p2.getId());
	}

	@Test
	public void deleteById() {
		Person p1 = list.get(0);
		service.deleteById(p1.getId());
		List<Person> newList = service.findAll();
		assertNotEquals(newList.size(), list.size());

		Exception exception = assertThrows(PersonNotFoundException.class, () -> {
			service.findById(p1.getId());
		});

		String expectedMessage = String.format("person is not found with id: %s", p1.getId());
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void findAllOrderedByName() {
		List<Person> orderedByService = service.findAllByOrderByNameAsc();
		assertNotNull(orderedByService);
		assertNotSame(list.get(0).getName(), orderedByService.get(0).getName());

		Comparator<Person> compareByName = (Person p1, Person p2) -> p1.getName().compareTo(p2.getName());
		Collections.sort(list, compareByName);
		assertEquals(list.get(0).getName(), orderedByService.get(0).getName());
		assertEquals(list.get(0).getId(), orderedByService.get(0).getId());
	}

	@Test
	public void findByName() throws PersonNotFoundException {
		Person personToFind = new Person("PersonToFind");
		service.saveOrUpdate(personToFind);
		Person personFound = service.findByName("PersonToFind");
		assertEquals(personToFind.getId(), personFound.getId());
		assertEquals(personToFind.getName(), personFound.getName());

		String randomName = RandomStringUtils.randomAlphabetic(5);
		Person randomPerson = new Person(randomName);
		Exception exception = assertThrows(PersonNotFoundException.class, () -> {
			service.findByName(randomPerson.getName());
		});
		String expectedMessage = String.format("person is not found with name: %s", randomPerson.getName());
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void deleteAll() {
		service.deleteAll();
		List<Person> listAfterDelete = service.findAll();
		assertNotEquals(listAfterDelete.size(), list.size());
		assertEquals(0, listAfterDelete.size());
	}

}
