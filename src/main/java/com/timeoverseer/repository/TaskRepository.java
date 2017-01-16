package com.timeoverseer.repository;

import com.timeoverseer.model.Task;
import com.timeoverseer.model.enums.Qualification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@Transactional
@RepositoryRestResource
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task findByName(String name);

    Iterable<Task> findByQualification(Qualification qualification);
}
