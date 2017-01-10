package com.timeoverseer.repository

import com.timeoverseer.model.Company
import com.timeoverseer.model.Developer
import com.timeoverseer.model.ProjectManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static com.timeoverseer.model.enums.Qualification.JUNIOR
import static com.timeoverseer.model.enums.Qualification.SENIOR
import static com.timeoverseer.model.enums.Qualification.TRAINEE

@ContextConfiguration(classes = [DeveloperRepository, ProjectManagerRepository])
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class DeveloperProjectManagerSpec extends Specification {
    @Autowired
    DeveloperRepository developerRepository
    @Autowired
    ProjectManagerRepository projectManagerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", LocalDate.of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company
    def projectManager = ["Jake", "Main", "Ross", "glanes", company, SENIOR, null] as ProjectManager

    def developer1 = ["Rob", "Lowe", "Sake", "enuss", company, TRAINEE, projectManager] as Developer
    def developer2 = ["Katy", "Starr", "Wildow", "glass122", company, JUNIOR, projectManager] as Developer

    void setup() {
        projectManager.addDeveloper(developer1, developer2)
        company.addEmployee(projectManager)
        company.addEmployee(developer2)
        company.addEmployee(developer2)

        entityManager.persistAndFlush(company)
    }

    def "should persist developers with project manager"() {
        expect:
        projectManagerRepository.findByQualification(SENIOR) != null
        developerRepository.findByFirstName("Rob") != null
        developerRepository.findByLogin("Wildow") != null
    }
    def "should not remove developer when project manager removed"() {
        when:
        projectManagerRepository.delete(projectManager)

        then:
        developerRepository.findByLogin("Sake") != null
        developerRepository.findByLastName("Starr") != null
    }

    def "should not remove project manager when developer removed"() {
        when:
        projectManagerRepository.delete(developer1)

        then:
        projectManagerRepository.findByLogin("Ross") != null
        developerRepository.findByLastName("Starr") != null
    }
}
