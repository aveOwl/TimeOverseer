package com.timeoverseer.repository;

import com.timeoverseer.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface PersonRepository<T extends Person> extends CrudRepository<T, Long> {

    T findByFirstName(String firstName);

    T findByLastName(String lastName);

    T findByLogin(@Param("login") String login);
}
