package com.timeoverseer.repository

import com.timeoverseer.model.Company
import com.timeoverseer.model.Developer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

import static com.timeoverseer.model.enums.Qualification.MIDDLE
import static com.timeoverseer.model.enums.Qualification.TRAINEE

@ContextConfiguration(classes = DeveloperRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class DeveloperRepositorySpec extends Specification {
    @Autowired
    DeveloperRepository developerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", LocalDate.of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company
    def developer = ["Rob", "Lowe", "Sake", "enuss", company, TRAINEE, null] as Developer

    void setup() {
        entityManager.persistAndFlush(company)
    }

    def "should persist developer"() {
        given:
        entityManager.persistAndFlush(developer)

        when:
        def fetchedDeveloper = developerRepository.findByEmployer(company)

        then:
        fetchedDeveloper.qualification == TRAINEE
        fetchedDeveloper.lastName.contains("Lowe")
    }

    def "should delete developer"() {
        given:
        entityManager.persistAndFlush(developer)

        when:
        developerRepository.delete(developer)

        then:
        developerRepository.findByFirstName("Katy") == null
    }

    def "should update developer"() {
        given:
        entityManager.persistAndFlush(developer)
        developer.qualification = MIDDLE

        when:
        def updatedDeveloper = developerRepository.save(developer)

        then:
        updatedDeveloper.qualification == MIDDLE
        updatedDeveloper.employer == company
    }

    def "should return null if developer not exists"() {
        expect:
        developerRepository.findByLogin("RobStark") == null
    }
}
