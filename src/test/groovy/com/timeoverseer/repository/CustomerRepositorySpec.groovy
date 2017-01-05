package com.timeoverseer.repository

import com.timeoverseer.model.Customer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

@ContextConfiguration(classes = CustomerRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class CustomerRepositorySpec extends Specification {
    @Autowired
    CustomerRepository customerRepository
    @Autowired
    TestEntityManager entityManager

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer

    void setup() {
        entityManager.persistAndFlush(customer)
    }

    def "should persist customer"() {
        when:
        def cust = customerRepository.findByFirstName("Jake")

        then:
        cust.businessInterests.contains("software")
        cust.lastName == 'Main'
    }

    def "should delete customer"() {
        when:
        customerRepository.delete(customer)

        then:
        customerRepository.findByLastName("Main") == null
    }

    def "should update customer"() {
        given:
        customer.firstName = "Roland"

        when:
        def updatedCustomer = customerRepository.save(customer)

        then:
        updatedCustomer.firstName == "Roland"
        updatedCustomer.businessInterests.contains("software")
    }

    def "should return null if customer not exists"() {
        when:
        customerRepository.delete(customer)

        then:
        customerRepository.findByFirstName("Jake") == null
    }
}
