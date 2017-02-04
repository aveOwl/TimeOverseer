package com.timeoverseer.controller;

import com.timeoverseer.model.Person;
import com.timeoverseer.repository.PersonRepository;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkUnique")
public class CheckUniqueController {
    private PersonRepository<Person> personPersonRepository;

    public CheckUniqueController(PersonRepository<Person> personPersonRepository) {
        this.personPersonRepository = personPersonRepository;
    }

    @PostMapping
    OperationStatus checkUniqueLogin(@RequestBody String login) {
        Person person = this.personPersonRepository.findByLogin(login);
        return person == null ? new OperationStatus(true) : new OperationStatus(false);
    }

    @Data
    private class OperationStatus {
        private final boolean status;
    }
}
