package com.timeoverseer.repository;

import com.timeoverseer.model.Developer;
import com.timeoverseer.repository.nobean.EmployeeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends EmployeeRepository<Developer> {
}
