package com.application.hibernate_spring.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.hibernate_spring.model.Person;

@Repository
public interface IRepository extends CrudRepository<Person, Long> {

}
