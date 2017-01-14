package com.timeoverseer.repository;

import com.timeoverseer.model.Employee;
import com.timeoverseer.repository.nobean.PersonRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface EmployeeRepository extends PersonRepository<Employee>,
        CrudRepository<Employee, Long> {
}
