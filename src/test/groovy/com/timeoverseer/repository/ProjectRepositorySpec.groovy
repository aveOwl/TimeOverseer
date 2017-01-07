package com.timeoverseer.repository

import com.timeoverseer.model.Customer
import com.timeoverseer.model.Project
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static java.time.LocalDate.of

@ContextConfiguration(classes = ProjectRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class ProjectRepositorySpec extends Specification {
    @Autowired
    ProjectRepository projectRepository
    @Autowired
    TestEntityManager entityManager

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def project = ["Apple TV", of(2016, 1, 4), of(2016, 1, 5), customer, null] as Project

    void setup() {
        customer.addProject(project)
        entityManager.persistAndFlush(customer)
    }

    def "should persist project"() {
        when:
        def fetchedProject = projectRepository.findByCustomer(customer)

        then:
        fetchedProject.description.contains("Apple")
    }

    def "should delete project"() {
        given:
        customer.removeProject(project)

        when:
        projectRepository.delete(project)

        then:
        projectRepository.findByCustomer(customer) == null
    }

    def "should update project"() {
        given:
        project.description = "iPod"

        when:
        def updatedProject = projectRepository.save(project)

        then:
        updatedProject.description.contains("iPod")
    }
}
