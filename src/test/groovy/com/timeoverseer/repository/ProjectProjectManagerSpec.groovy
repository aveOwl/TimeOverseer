package com.timeoverseer.repository

import com.timeoverseer.model.Company
import com.timeoverseer.model.Customer
import com.timeoverseer.model.Project
import com.timeoverseer.model.ProjectManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static com.timeoverseer.model.enums.Qualification.SENIOR
import static java.time.LocalDate.of

@ContextConfiguration(classes = [ProjectRepository, ProjectManagerRepository])
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class ProjectProjectManagerSpec extends Specification {
    @Autowired
    ProjectRepository projectRepository
    @Autowired
    ProjectManagerRepository projectManagerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company
    def projectManager = ["Jake", "Main", "Ross", "glanes", company, SENIOR, null] as ProjectManager

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def project = ["Apple TV", of(2016, 1, 4), of(2016, 1, 5), customer, null] as Project

    void setup() {
        company.addCustomer(customer)
        customer.addCompany(company)

        entityManager.persistAndFlush(company)

        customer.addProject(project)
        company.addEmployee(projectManager)

        project.projectManager = projectManager
        projectManager.project = project
    }

    def "should save project manager with project"() {
        given:
        entityManager.persistAndFlush(project)

        when:
        def savedPM = projectManagerRepository.findByLogin("Ross")

        then:
        savedPM.qualification == SENIOR
    }

    def "should save project with project manager"() {
        given:
        entityManager.persistAndFlush(projectManager)

        when:
        def savedProject = projectRepository.findByCustomer(customer)

        then:
        savedProject.description.contains("Apple")
    }

    def "should not delete project when project manager removed"() {
        given:
        project.projectManager == null
        entityManager.persistAndFlush(projectManager)

        when:
        projectManagerRepository.delete(projectManager)

        then:
        projectRepository.findByCustomer(customer) != null
    }

    def "should not delete project manager when project removed"() {
        given:
        projectManager.project == null
        entityManager.persistAndFlush(project)

        when:
        projectRepository.delete(project)

        then:
        projectManagerRepository.findByFirstName("Jake") != null
    }
}
