package com.timeoverseer.repository.base;

import com.timeoverseer.model.Person;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface PersonBaseRepository<T extends Person> extends Repository<T, Long> {

    T findByFirstName(String firstName);

    T findByLastName(String lastName);

    T findByLogin(String login);
}
