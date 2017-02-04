package com.timeoverseer.repository;

import com.timeoverseer.model.Administrator;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface AdministratorRepository extends PersonRepository<Administrator>,
        CrudRepository<Administrator, Long> {
}
