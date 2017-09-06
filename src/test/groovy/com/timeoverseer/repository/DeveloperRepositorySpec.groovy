package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import static com.timeoverseer.model.enums.Qualification.MIDDLE

@ContextConfiguration(classes = DeveloperRepository)
class DeveloperRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    DeveloperRepository developerRepository

    void setup() {
        company.addEmployee(developer1)
        developer1.employer = company
        entityManager.persistAndFlush(company)
    }

    def "should persist developer"() {
        when:
        def fetchedDeveloper = developerRepository.findByEmployer(company)

        then:
        fetchedDeveloper.qualification == developer1.qualification
        fetchedDeveloper.lastName.contains(developer1.lastName)
    }

    def "should delete developer"() {
        given:
        company.removeEmployee(developer1)

        when:
        developerRepository.delete(developer1)

        then:
        developerRepository.findByFirstName(developer1.firstName) == null
    }

    def "should update developer"() {
        given:
        developer1.qualification = MIDDLE

        when:
        def updatedDeveloper = developerRepository.save(developer1)

        then:
        updatedDeveloper.qualification == MIDDLE
        updatedDeveloper.employer == company
    }
}
