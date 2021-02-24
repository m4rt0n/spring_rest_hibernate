package com.application.hibernate_spring.testweb;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.application.hibernate_spring.data.Controller;
import com.application.hibernate_spring.data.IService;
import com.application.hibernate_spring.model.Person;

@WebMvcTest(Controller.class)
public class WebMockTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IService service;

	private static final String PERSON_PATH = "/person/";

	@Test
	public void getHello() throws Exception {
		when(service.hello()).thenReturn("Hello!");
		this.mockMvc.perform(get("/person/hello")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello!")));
	}

	@Test
	public void getAll() throws Exception {
		Person john = new Person("John");
		Person jane = new Person("Jane");
		Person jim = new Person("Jim");
		List<Person> list = new ArrayList<>();
		list.add(john);
		list.add(jane);
		list.add(jim);
		list.forEach(p -> service.saveOrUpdate(p));

		when(service.findAll()).thenReturn(list);
		this.mockMvc.perform(get(PERSON_PATH + "getall")).andExpect(status().isOk());
	}

	@Test
	public void getById() throws Exception {
		Person john = new Person("John");
		service.saveOrUpdate(john);

		when(service.findById(john.getId())).thenReturn(john);
		this.mockMvc.perform(get(PERSON_PATH + "getbyid/" + john.getId())).andExpect(status().isOk());
	}

}
