package com.timeoverseer.service;

import com.timeoverseer.model.Company;
import com.timeoverseer.repository.CompanyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private static final Logger LOG = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
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
    public void delete(Company company) {
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
    }

    @Override
    public List<Company> findAll() {
        LOG.info("> Fetching all companies...");
        List<Company> companies = (List<Company>) this.companyRepository.findAll();
        LOG.debug("< {} companies found", companies.size());
        return companies;
    }
}
