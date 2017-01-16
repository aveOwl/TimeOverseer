package com.timeoverseer.repository;

import com.timeoverseer.model.ProjectManager;
import com.timeoverseer.repository.base.EmployeeBaseRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface ProjectManagerRepository extends EmployeeBaseRepository<ProjectManager> {
}
