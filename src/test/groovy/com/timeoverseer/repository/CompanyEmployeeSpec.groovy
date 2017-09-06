package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import static com.timeoverseer.model.enums.Qualification.SENIOR

@ContextConfiguration(classes = [CompanyRepository, DeveloperRepository, ProjectManagerRepository])
class CompanyEmployeeSpec extends AbstractRepositorySpec {
    @Autowired
    CompanyRepository companyRepository
    @Autowired
    DeveloperRepository developerRepository
    @Autowired
    ProjectManagerRepository projectManagerRepository

    void setup() {
        projectManager.employer = company
        developer1.employer = company

        company.addEmployee(projectManager)
        company.addEmployee(developer1)

        entityManager.persistAndFlush(company)
    }

    def "should persist employees with company"() {
        when:
        def savedCompany = companyRepository.findByName(company.name)
        def savedPM = projectManagerRepository.findByQualification(SENIOR)
        def savedDev = developerRepository.findByLogin(developer1.login)

        then:
        savedCompany.employees.size() == 2
        savedPM.id != null
        savedDev.id != null
        savedPM.firstName.contains(projectManager.firstName)
        savedDev.lastName.contains(developer1.lastName)
    }

    def "should delete employees if company removed"() {
        when:
        companyRepository.delete(company)

        then:
        companyRepository.findByName(company.name) == null
        projectManagerRepository.findByFirstName(projectManager.firstName) == null
        developerRepository.findByLogin(developer1.login) == null
    }

    def "should not delete company if employee removed"() {
        given:
        company.removeEmployee(developer1)

        when:
        developerRepository.delete(developer1)

        then:
        company.employees.size() == 1
        companyRepository.findByName(company.name) != null
        developerRepository.findByLogin(developer1.login) == null
    }
}
