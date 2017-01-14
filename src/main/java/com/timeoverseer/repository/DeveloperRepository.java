package com.timeoverseer.repository;

import com.timeoverseer.model.Developer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface DeveloperRepository extends CrudRepository<Developer, Long> {
}
