package com.application.hibernate_spring.testweb;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.application.hibernate_spring.data.IService;
import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

@SpringBootTest
class TestController {
	@Autowired
	private IService service;

	@Test
	@Order(1)
	public void testCreate() {
		Person createPerson = new Person("createPerson");
		service.create(createPerson);
		Assertions.assertTrue(createPerson.getId() > 0);
	}

	@Test
	@Order(2)
	public void testGet() throws PersonNotFoundException {
		Person p1 = new Person("p1");
		service.create(p1);
		long id = p1.getId();
		Person p2 = service.findById(id);
		assertTrue(p1.getId().equals(p2.getId()));
		assertTrue(p1.getName().equals(p2.getName()));
	}

	@Test
	@Order(3)
	public void testList() {
		List<Person> pList = service.findAll();
		assertFalse(pList.isEmpty());
		assertTrue(pList.size() > 0);
	}

	@Test
	@Order(4)
	public void testupdate() throws PersonNotFoundException {
		Person oldPerson = new Person("oldPerson");
		service.create(oldPerson);
		long oldId = oldPerson.getId();
		Person updatePerson = new Person("updatePerson");
		service.update(oldId, updatePerson);
		assertThat(oldPerson.getName().equals("updatePerson"));
	}

	@Test
	@Order(5)
	public void testDelete() throws PersonNotFoundException {
		Person personToDelete = new Person("personToDelete");
		service.create(personToDelete);
		long id = personToDelete.getId();
		service.deleteById(id);

		Exception exception = assertThrows(PersonNotFoundException.class, () -> {
			service.findById(id);
		});

		String expectedMessage = "person is not found with id: ";
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

}
