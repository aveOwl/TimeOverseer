package com.timeoverseer.repository;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Employee;
import com.timeoverseer.model.enums.Qualification;
import com.timeoverseer.repository.nobean.PersonRepository;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends PersonRepository<Employee>,
        CrudRepository<Employee, Long> {

    Employee findByEmployer(Company employer);

    Employee findByQualification(Qualification qualification);

    Iterable<Employee> findAllByEmployer(Company employer);

    Iterable<Employee> findAllByQualification(Qualification qualification);
}
