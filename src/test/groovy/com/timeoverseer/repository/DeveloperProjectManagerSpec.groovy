package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import static com.timeoverseer.model.enums.Qualification.SENIOR

@ContextConfiguration(classes = [DeveloperRepository, ProjectManagerRepository])
class DeveloperProjectManagerSpec extends AbstractRepositorySpec {
    @Autowired
    DeveloperRepository developerRepository
    @Autowired
    ProjectManagerRepository projectManagerRepository

    void setup() {
        projectManager.addDeveloper(developer1)
        projectManager.addDeveloper(developer2)
        company.addEmployee(projectManager)
        company.addEmployee(developer2)
        company.addEmployee(developer2)

        entityManager.persistAndFlush(company)
    }

    def "should persist developers with project manager"() {
        expect:
        projectManagerRepository.findByQualification(SENIOR) != null
        developerRepository.findByFirstName(developer1.firstName) != null
        developerRepository.findByLogin(developer2.login) != null
    }

    def "should not remove developer when project manager removed"() {
        when:
        projectManagerRepository.delete(projectManager)

        then:
        developerRepository.findByLogin(developer1.login) != null
        developerRepository.findByLastName(developer2.lastName) != null
    }

    def "should not remove project manager when developer removed"() {
        when:
        developerRepository.delete(developer1)

        then:
        projectManagerRepository.findByLogin(projectManager.login) != null
        developerRepository.findByLastName(developer2.lastName) != null
    }
}
