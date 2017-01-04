package com.timeoverseer.repository;

import com.timeoverseer.model.Sprint;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepository extends CrudRepository<Sprint, Long> {

}
