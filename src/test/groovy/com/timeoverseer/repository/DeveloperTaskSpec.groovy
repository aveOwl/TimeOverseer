package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [DeveloperRepository, TaskRepository])
class DeveloperTaskSpec extends AbstractRepositorySpec {
    @Autowired
    TaskRepository taskRepository
    @Autowired
    DeveloperRepository developerRepository

    void setup() {
        company.addEmployee(developer1)
        developer1.employer = company

        company.addCustomer(customer1)
        customer1.addCompany(company)

        customer1.addProject(project)
        project.customer = customer1

        project.addSprint(sprint)
        sprint.project = project

        sprint.addTask(task1)
        task1.sprint = sprint

        developer1.addTask(task1)
        task1.addDeveloper(developer1)

        entityManager.persistAndFlush(company)
    }

    def "should not remove developer when task removed"() {
        given:
        developer1.removeTask(task1)

        when:
        taskRepository.delete(task1)

        then:
        developerRepository.findByLogin(developer1.login) != null
    }

    def "should not remove task when developer removed"() {
        given:
        task1.removeDeveloper(developer1)

        when:
        developerRepository.delete(developer1)

        then:
        taskRepository.findByName(task1.name) != null
    }
}
