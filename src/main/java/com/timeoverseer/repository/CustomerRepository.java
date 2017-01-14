package com.timeoverseer.repository;

import com.timeoverseer.model.Customer;
import com.timeoverseer.repository.nobean.PersonRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(itemResourceRel = "customer", collectionResourceRel = "customers", path = "customers")
public interface CustomerRepository extends PersonRepository<Customer>,
        CrudRepository<Customer, Long> {
}
