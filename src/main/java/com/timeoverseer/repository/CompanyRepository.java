package com.timeoverseer.repository;

import com.timeoverseer.model.Company;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface CompanyRepository extends CrudRepository<Company, Long> {

    Company findByName(String name);
}
