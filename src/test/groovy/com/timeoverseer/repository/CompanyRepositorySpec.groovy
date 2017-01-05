package com.timeoverseer.repository

import com.timeoverseer.model.Company
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@ContextConfiguration(classes = CompanyRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class CompanyRepositorySpec extends Specification {
    @Autowired
    CompanyRepository companyRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", LocalDate.of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company

    void setup() {
        entityManager.persistAndFlush(company)
    }

    def "should persist company"() {
        when:
        def fetchedCompany = companyRepository.findByName("Apple")

        then:
        fetchedCompany.products.contains("iPhone")
        fetchedCompany.founders.contains("Steve Jobs")
    }

    def "should delete company"() {
        when:
        companyRepository.delete(company)

        then:
        companyRepository.findByName("Apple") == null
    }

    def "should update company"() {
        given:
        company.name = "Apple Inc."

        when:
        def updatedCompany = companyRepository.save(company)

        then:
        updatedCompany.name == "Apple Inc."
        updatedCompany.founders.contains("Steve Jobs")
    }
}
