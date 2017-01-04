package com.timeoverseer.repository.nobean;

import com.timeoverseer.model.Person;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface PersonRepository<T extends Person> extends Repository<T, Long> {

    T findByFirstName(String firstName);

    T findByLastName(String lastName);

    T findByLogin(String login);
}
