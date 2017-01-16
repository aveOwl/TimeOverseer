package com.timeoverseer.repository;

import com.timeoverseer.model.Developer;
import com.timeoverseer.repository.base.EmployeeBaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface DeveloperRepository extends EmployeeBaseRepository<Developer> {
}
