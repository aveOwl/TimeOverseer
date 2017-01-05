package com.timeoverseer.repository;

import com.timeoverseer.model.Sprint;
import com.timeoverseer.model.Task;
import com.timeoverseer.model.enums.Qualification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {

    Task findByName(String name);

    Iterable<Task> findBySprint(Sprint sprint);

    Iterable<Task> findByQualification(Qualification qualification);
}
