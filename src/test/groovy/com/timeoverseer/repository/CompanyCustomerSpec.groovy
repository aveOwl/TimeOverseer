package com.timeoverseer.repository

import com.timeoverseer.model.Company
import com.timeoverseer.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

@ContextConfiguration(classes = [CompanyRepository, CustomerRepository])
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class CompanyCustomerSpec extends Specification {
    @Autowired
    CompanyRepository companyRepository
    @Autowired
    CustomerRepository customerRepository
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", LocalDate.of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company

    def cust1 = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def cust2 = ["Rob", "Lowe", "Sake", "enuss", "hardware"] as Customer

    void setup() {
        cust1.addCompany(company)
        cust2.addCompany(company)

        company.addCustomer(cust1)
        company.addCustomer(cust2)

        entityManager.persistAndFlush(company)
    }

    def "should persist customers with company"() {
        when:
        def savedCompany = companyRepository.findByName("Apple")
        def savedCust1 = customerRepository.findByFirstName("Jake")
        def savedCust2 = customerRepository.findByLastName("Lowe")

        then:
        savedCompany.customers.size() == 2
        savedCust1.businessInterests.contains("software")
        savedCust2.businessInterests.contains("hardware")
    }

    def "should not delete customers if company removed"() {
        given:
        cust1.removeCompany(company)
        cust2.removeCompany(company)

        when:
        companyRepository.delete(company)

        def savedCust1 = customerRepository.findByFirstName("Jake")
        def savedCust2 = customerRepository.findByLastName("Lowe")

        then:
        cust1.companies.isEmpty()
        cust2.companies.isEmpty()
        companyRepository.findByName("Apple") == null
        savedCust1.businessInterests.contains("software")
        savedCust2.businessInterests.contains("hardware")
    }

    def "should not delete company if customer removed"() {
        given:
        company.removeCustomer(cust1)

        when:
        customerRepository.delete(cust1)

        then:
        company.customers.size() == 1
        companyRepository.findByName("Apple").industry.contains("Software")
        customerRepository.findByFirstName("Jake") == null
        customerRepository.findByFirstName("Rob") != null
    }
}
