package com.timeoverseer.repository.nobean;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Employee;
import com.timeoverseer.model.enums.Qualification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends PersonRepository<Employee>,
        CrudRepository<Employee, Long> {

    T findByEmployer(Company employer);

    T findByQualification(Qualification qualification);

    Iterable<T> findAllByEmployer(Company employer);

    Iterable<T> findAllByQualification(Qualification qualification);
}
