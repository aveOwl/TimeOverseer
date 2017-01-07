package com.timeoverseer.repository;

import com.timeoverseer.model.Customer;
import com.timeoverseer.model.Project;
import com.timeoverseer.model.ProjectManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByProjectManager(ProjectManager projectManager);

    Project findByCustomer(Customer customer);
}
