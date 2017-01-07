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

@ContextConfiguration(classes = SprintRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class SprintRepositorySpec extends Specification {
    @Autowired
    SprintRepository sprintRepository
    @Autowired
    TestEntityManager entityManager

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def project = ["Apple TV", of(2016, 1, 4), of(2016, 1, 5), customer, null] as Project
    def sprint = ["First Phase", project] as Sprint

    void setup() {
        customer.addProject(project)
        project.customer = customer

        project.addSprint(sprint)
        sprint.project = project

        entityManager.persistAndFlush(customer)
    }

    def "should persist sprint"() {
        when:
        def fetchedSprint = sprintRepository.findByName("First Phase")

        then:
        fetchedSprint.project == project
    }

    def "should delete sprint"() {
        given:
        project.removeSprint(sprint)

        when:
        sprintRepository.delete(sprint)

        then:
        sprintRepository.findByName("First Phase") == null
    }

    def "should update sprint"() {
        given:
        sprint.name = "Best Phase"

        when:
        def updatedSprint = sprintRepository.save(sprint)

        then:
        updatedSprint.name.contains("Best")
    }
}
