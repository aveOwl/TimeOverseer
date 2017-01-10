package com.timeoverseer.service;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Employee;
import com.timeoverseer.repository.CompanyRepository;
import com.timeoverseer.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyRepository companyRepository;
    private EmployeeRepository employeeRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              EmployeeRepository employeeRepository) {
        this.companyRepository = companyRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Company addCompany(Company company) {
        Assert.notNull(company, "Company must not be null");
        LOG.info("> Saving company...");
        if (company.getId() != null) {
            LOG.error("Company already exists with id: {}", company.getId());
            throw new EntityExistsException("Company already exists with id: " + company.getId());
        }
        Company savedCompany = this.companyRepository.save(company);
        LOG.debug("< Saved {}", savedCompany);
        return savedCompany;
    }

    @Override
    public Company updateCompany(Company company) {
        Assert.notNull(company, "Company must not be null");
        LOG.info("> Updating company...");
        if (company.getId() == null) {
            LOG.error("No such company exists. Company id is null.");
            throw new EntityNotFoundException("No such company exists. Company id is null.");
        }
        Company updatedCompany = this.companyRepository.save(company);
        LOG.debug("< Updated {}", updatedCompany);
        return null;
    }

    @Override
    public Company findById(Long id) {
        Assert.notNull(id, "Id must not be null");
        LOG.debug("> Searching for company with id: {}", id);
        Company company = this.companyRepository.findOne(id);
        LOG.debug("< Returning {}", company);
        return company;
    }

    @Override
    public Company findByName(String name) {
        Assert.notNull(name, "Name must not be null");
        Assert.hasLength(name, "Name must not be empty");
        LOG.debug("> Searching for company with name: {}", name);
        Company company = this.companyRepository.findByName(name);
        LOG.debug("< Returning {}", company);
        return company;
    }

    @Override
    public Company delete(Company company) {
        Assert.notNull(company, "Company must not be null");
        LOG.info("> Deleting company...");
        if (company.getId() == null) {
            throw new EntityNotFoundException("No such company exists. Company id is null.");
        }
        if (company.getCustomers() != null) {
            company.getCustomers()
                    .forEach(c -> c.removeCompany(company));
        }
        this.companyRepository.delete(company);
        LOG.debug("< Deleted {}", company);
        return company;
    }

    @Override
    public Company delete(Long id) {
        Company company = this.findById(id);
        return this.delete(company);
    }

    @Override
    public List<Company> findAll() {
        LOG.info("> Fetching all companies...");
        List<Company> companies = (List<Company>) this.companyRepository.findAll();
        LOG.debug("< {} companies found", companies.size());
        return companies;
    }

    @Override
    public Employee addEmployee(Long companyId, Employee employee) {
        Assert.notNull(companyId, "Company id must not be null");
        Assert.notNull(employee, "Employee must not be null");
        Company company = this.findById(companyId);

        company.addEmployee(employee);

        this.updateCompany(company);
        return employee;
    }

    @Override
    public Employee findEmployeeById(Long companyId, Long employeeId) {
        Assert.notNull(companyId, "Company id must not be null");
        Assert.notNull(employeeId, "Employee id must not be null");
        Company company = this.findById(companyId);

        return company.findEmployeeById(employeeId);
    }

    @Override
    public Employee deleteEmployee(Long companyId, Long employeeId) {
        Assert.notNull(companyId, "Company id must not be null");
        Assert.notNull(employeeId, "Employee id must not be null");
        LOG.info("> Deleting employee...");
        Company company = this.findById(companyId);
        Employee employee = company.findEmployeeById(employeeId);
        company.removeEmployee(employee);
        this.employeeRepository.delete(employee);
        LOG.debug("< Deleted {}", employee);
        return employee;
    }

    @Override
    public List<Employee> findAllEmployees(Long companyId) {
        Assert.notNull(companyId, "Company id must not be null");
        Company company = this.findById(companyId);

        return new ArrayList<>(company.getEmployees());
    }
}
