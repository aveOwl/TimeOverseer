package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = CompanyRepository)
class CompanyRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    CompanyRepository companyRepository

    void setup() {
        entityManager.persistAndFlush(company)
    }

    def "should persist company"() {
        when:
        def fetchedCompany = companyRepository.findByName(company.name)

        then:
        fetchedCompany.products.contains(company.products)
        fetchedCompany.founders.contains(company.founders)
    }

    def "should delete company"() {
        when:
        companyRepository.delete(company)

        then:
        companyRepository.findByName(company.name) == null
    }

    def "should update company"() {
        given:
        company.name = "Apple Inc."

        when:
        def updatedCompany = companyRepository.save(company)

        then:
        updatedCompany.name == "Apple Inc."
        updatedCompany.founders.contains(company.founders)
    }
}
