package com.timeoverseer.repository;

import com.timeoverseer.model.Customer;
import com.timeoverseer.model.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface ProjectRepository extends CrudRepository<Project, Long> {

    Project findByCustomer(Customer customer);
}
