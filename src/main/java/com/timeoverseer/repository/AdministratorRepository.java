package com.timeoverseer.repository;

import com.timeoverseer.model.Administrator;
import com.timeoverseer.repository.nobean.PersonRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends PersonRepository<Administrator> {
}
