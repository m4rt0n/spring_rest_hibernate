package com.application.hibernate_spring.data;

import org.springframework.data.repository.CrudRepository;

import com.application.hibernate_spring.model.Person;

public interface IRepository extends CrudRepository<Person, Long> {

}
