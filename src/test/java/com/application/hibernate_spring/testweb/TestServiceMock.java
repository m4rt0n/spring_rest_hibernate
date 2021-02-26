package com.application.hibernate_spring.testweb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.application.hibernate_spring.data.IRepository;
import com.application.hibernate_spring.data.Service;
import com.application.hibernate_spring.model.Person;
import com.application.hibernate_spring.model.PersonNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TestServiceMock {

	@InjectMocks
	private Service service;

	@Mock
	IRepository repo;

	@Test
	public void findAll() {
		List<Person> list = new ArrayList<>();
		Person p1 = new Person("P1");
		Person p2 = new Person("P2");
		Person p3 = new Person("P3");
		list.add(p1);
		list.add(p2);
		list.add(p3);
		lenient().when(repo.findAll()).thenReturn(list);

		// test
		List<Person> testlist = service.findAll();
		assertEquals(3, testlist.size());
		verify(repo, times(1)).findAll();
	}

	@Test
	public void findById() throws PersonNotFoundException {
		Person p = new Person("P");
		when(repo.findById(p.getId())).thenReturn(Optional.of(p));
		Person px = service.findById(p.getId());
		assertEquals("P", px.getName());
		verify(repo, times(1)).findById(p.getId());
		assertEquals(Optional.of(p), repo.findById(p.getId()));
	}

	@Test
	public void save() {
		Person p = new Person("P");
		service.saveOrUpdate(p);
		verify(repo, times(1)).save(p);
	}

	@Test
	public void delete() {
		Person p = new Person("P");
		service.deleteById(p.getId());
		verify(repo, times(1)).deleteById(p.getId());
	}

}
