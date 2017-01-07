package com.timeoverseer.repository;

import com.timeoverseer.model.Customer;
import com.timeoverseer.repository.nobean.PersonRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PersonRepository<Customer>,
        CrudRepository<Customer, Long> {
}
