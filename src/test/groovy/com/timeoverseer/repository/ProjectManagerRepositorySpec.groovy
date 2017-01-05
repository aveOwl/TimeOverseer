package com.timeoverseer.repository

import com.timeoverseer.model.Company
import com.timeoverseer.model.ProjectManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static com.timeoverseer.model.enums.Qualification.MIDDLE
import static com.timeoverseer.model.enums.Qualification.SENIOR
import static java.time.LocalDate.of

@ContextConfiguration(classes = ProjectManagerRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest(showSql = true)
@Unroll
class ProjectManagerRepositorySpec extends Specification {
    @Autowired
    ProjectManagerRepository projectManagerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company
    def projectManager = ["Jake", "Main", "Ross", "glanes", company, SENIOR, null] as ProjectManager

    void setup() {
        company.addEmployee(projectManager)

        entityManager.persistAndFlush(company)
    }

    def "should persist project manager"() {
        when:
        def fetchedProjectManager = projectManagerRepository.findByEmployer(company)

        then:
        fetchedProjectManager.qualification == SENIOR
        fetchedProjectManager.firstName.contains("Jake")
    }

    def "should delete project manager"() {
        given:
        company.removeEmployee(projectManager)

        when:
        projectManagerRepository.delete(projectManager)

        then:
        projectManagerRepository.findByFirstName("Jake") == null
    }

    def "should update project manager"() {
        given:
        projectManager.qualification = MIDDLE

        when:
        def updatedProjectManager = projectManagerRepository.save(projectManager)

        then:
        updatedProjectManager.qualification == MIDDLE
        updatedProjectManager.employer == company
    }
}
