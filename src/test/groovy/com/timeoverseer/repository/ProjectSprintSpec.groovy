package com.timeoverseer.repository

import com.timeoverseer.model.Customer
import com.timeoverseer.model.Project
import com.timeoverseer.model.Sprint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.LocalDate.of

@ContextConfiguration(classes = [ProjectRepository, SprintRepository])
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class ProjectSprintSpec extends Specification {
    @Autowired
    ProjectRepository projectRepository
    @Autowired
    SprintRepository sprintRepository
    @Autowired
    TestEntityManager entityManager

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def project = ["Apple", "New Generation TV", of(2016, 1, 4), of(2016, 1, 5), customer, null] as Project

    def sprint = ["First Phase", project] as Sprint

    void setup() {
        customer.addProject(project)
        project.addSprint(sprint)

        project.customer = customer
        sprint.project = project

        entityManager.persistAndFlush(customer)
    }

    def "should save sprint when project saved"() {
        expect:
        sprintRepository.findByName("First Phase") != null
    }

    def "should delete sprint if project deleted"() {
        given:
        project.removeSprint(sprint)
        sprint.project == null

        when:
        projectRepository.delete(project)

        then:
        sprintRepository.findByName("First Phase") == null
    }

    def "should not delete project if sprint deleted"() {
        given:
        project.removeSprint(sprint)

        when:
        sprintRepository.delete(sprint)

        then:
        projectRepository.findByCustomer(customer) != null
    }
}
