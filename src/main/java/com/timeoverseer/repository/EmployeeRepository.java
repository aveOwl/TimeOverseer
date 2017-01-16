package com.timeoverseer.repository;

import com.timeoverseer.model.Employee;
import com.timeoverseer.repository.base.EmployeeBaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface EmployeeRepository extends EmployeeBaseRepository<Employee> {
}
