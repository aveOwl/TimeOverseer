package com.timeoverseer.repository.base;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Employee;
import com.timeoverseer.model.enums.Qualification;
import com.timeoverseer.repository.PersonRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EmployeeBaseRepository<T extends Employee> extends PersonRepository<T>,
        CrudRepository<T, Long> {

    T findByEmployer(Company employer);

    T findByQualification(Qualification qualification);
}
