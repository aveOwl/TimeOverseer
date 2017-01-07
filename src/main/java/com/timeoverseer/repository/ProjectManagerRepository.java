package com.timeoverseer.repository;

import com.timeoverseer.model.ProjectManager;
import com.timeoverseer.repository.nobean.EmployeeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectManagerRepository extends EmployeeRepository<ProjectManager> {

}
