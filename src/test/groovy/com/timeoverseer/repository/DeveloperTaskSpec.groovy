package com.timeoverseer.repository

import com.timeoverseer.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static com.timeoverseer.model.enums.Qualification.MIDDLE
import static com.timeoverseer.model.enums.Qualification.TRAINEE
import static java.time.LocalDate.of

@ContextConfiguration(classes = [DeveloperRepository, TaskRepository])
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class DeveloperTaskSpec extends Specification {
    @Autowired
    TaskRepository taskRepository
    @Autowired
    DeveloperRepository developerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company
    def developer = ["Rob", "Lowe", "Sake", "enuss", company, TRAINEE, null] as Developer

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def project = ["Apple", "New Generation TV", of(2016, 1, 4), of(2016, 1, 5), customer, null] as Project
    def sprint = ["First Phase", project] as Sprint

    def task = ["First Task", false, MIDDLE, 20L, sprint] as Task

    void setup() {
        company.addEmployee(developer)
        developer.employer = company

        company.addCustomer(customer)
        customer.addCompany(company)

        customer.addProject(project)
        project.customer = customer

        project.addSprint(sprint)
        sprint.project = project

        sprint.addTask(task)
        task.sprint = sprint

        developer.addTask(task)
        task.addDeveloper(developer)

        entityManager.persistAndFlush(company)
    }

    def "should not remove developer when task removed"() {
        given:
        developer.removeTask(task)

        when:
        taskRepository.delete(task)

        then:
        developerRepository.findByLogin("Sake") != null
    }

    def "should not remove task when developer removed"() {
        given:
        task.removeDeveloper(developer)

        when:
        developerRepository.delete(developer)

        then:
        taskRepository.findByName("First Task") != null
    }
}
