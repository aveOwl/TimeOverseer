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

import static com.timeoverseer.model.enums.Qualification.SENIOR
import static com.timeoverseer.model.enums.Qualification.TRAINEE

@ContextConfiguration(classes = [CompanyRepository, DeveloperRepository, ProjectManagerRepository])
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class CompanyEmployeeSpec extends Specification {
    @Autowired
    CompanyRepository companyRepository
    @Autowired
    DeveloperRepository developerRepository
    @Autowired
    ProjectManagerRepository projectManagerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["IBM", LocalDate.of(1911, 6, 16), "Cloud computing", "Charles Flint", "Computer Machines"] as Company

    def projectManager = ["Jake", "Main", "Ross", "glanes", company, SENIOR, null] as ProjectManager
    def developer = ["Rob", "Lowe", "Sake", "enuss", company, TRAINEE, projectManager] as Developer

    void setup() {
        projectManager.employer = company
        developer.employer = company

        company.addEmployee(projectManager)
        company.addEmployee(developer)
    }

    def "should persist employees with company"() {
        given:
        entityManager.persistAndFlush(company)

        when:
        def savedCompany = companyRepository.findByName("Apple")
        def savedPM = projectManagerRepository.findByQualification(SENIOR)
        def savedDev = developerRepository.findByLogin("Sake")

        then:
//        savedCompany.employees.size() == 2 // TODO fix this
        savedPM.firstName.contains("Jake")
        savedDev.lastName.contains("Lowe")
    }

    def "should delete employees if company removed"() {
        given:
        entityManager.persistAndFlush(company)

        when:
        companyRepository.delete(company)

        then:
        companyRepository.findByName("Apple") == null
        projectManagerRepository.findByFirstName("Jake") == null
        developerRepository.findByLogin("Sake") == null
    }

    def "should not delete company if employee removed"() {
        given:
        entityManager.persistAndFlush(company)

        when:
        developerRepository.delete(developer)

        then:
        companyRepository.findByName("IBM") != null
    }
}
