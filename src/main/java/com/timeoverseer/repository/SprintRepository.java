package com.timeoverseer.repository;

import com.timeoverseer.model.Sprint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface SprintRepository extends CrudRepository<Sprint, Long> {

    Sprint findByName(String name);
}
