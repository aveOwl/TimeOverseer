package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [CompanyRepository, CustomerRepository])
class CompanyCustomerSpec extends AbstractRepositorySpec {
    @Autowired
    CompanyRepository companyRepository
    @Autowired
    CustomerRepository customerRepository

    void setup() {
        customer1.addCompany(company)
        customer2.addCompany(company)

        company.addCustomer(customer1)
        company.addCustomer(customer2)

        entityManager.persistAndFlush(company)
    }

    def "should persist customers with company"() {
        when:
        def savedCompany = companyRepository.findByName(company.name)
        def savedCust1 = customerRepository.findByFirstName(customer1.firstName)
        def savedCust2 = customerRepository.findByLastName(customer2.lastName)

        then:
        savedCompany.customers.size() == 2
        savedCust1.id != null
        savedCust2.id != null
        savedCust1.businessInterests.contains(customer1.businessInterests)
        savedCust2.businessInterests.contains(customer2.businessInterests)
    }

    def "should not delete customers if company removed"() {
        given:
        customer1.removeCompany(company)
        customer2.removeCompany(company)

        when:
        companyRepository.delete(company)

        def savedCust1 = customerRepository.findByFirstName(customer1.firstName)
        def savedCust2 = customerRepository.findByLastName(customer2.lastName)

        then:
        customer1.companies.isEmpty()
        customer2.companies.isEmpty()
        companyRepository.findByName(company.name) == null
        savedCust1.businessInterests.contains(customer1.businessInterests)
        savedCust2.businessInterests.contains(customer2.businessInterests)
    }

    def "should not delete company if customer removed"() {
        given:
        company.removeCustomer(customer1)

        when:
        customerRepository.delete(customer1)

        then:
        company.customers.size() == 1
        companyRepository.findByName(company.name).industry.contains(company.industry)
        customerRepository.findByFirstName(customer1.firstName) == null
        customerRepository.findByFirstName(customer2.firstName) != null
    }
}
