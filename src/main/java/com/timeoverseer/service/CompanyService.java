package com.timeoverseer.service;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Employee;

import java.util.List;

public interface CompanyService {

    Company addCompany(Company company);

    Company updateCompany(Company company);

    Company findById(Long id);

    Company findByName(String name);

    Employee findEmployeeById(Long companyId, Long employeeId);

    Employee addEmployee(Long companyId, Employee employee);

    Employee deleteEmployee(Long companyId, Long employeeId);

    List<Employee> findAllEmployees(Long companyId);

    Company delete(Company company);

    Company delete(Long id);

    List<Company> findAll();
}
