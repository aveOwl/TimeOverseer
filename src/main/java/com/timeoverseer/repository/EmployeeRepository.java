package com.timeoverseer.repository;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Employee;
import com.timeoverseer.model.enums.Qualification;
import com.timeoverseer.repository.nobean.PersonRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(itemResourceRel = "employee", collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends PersonRepository<Employee>,
        PagingAndSortingRepository<Employee, Long> {

    Employee findByEmployer(Company employer);

    Employee findByQualification(Qualification qualification);

    Iterable<Employee> findAllByEmployer(Company employer);

    Iterable<Employee> findAllByQualification(Qualification qualification);
}
