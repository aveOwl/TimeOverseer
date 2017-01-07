package com.timeoverseer.service;

import com.timeoverseer.model.Company;

import java.util.List;

public interface CompanyService {

    Company addCompany(Company company);

    Company updateCompany(Company company);

    Company findById(Long id);

    Company findByName(String name);

    void delete(Company company);

    List<Company> findAll();
}
