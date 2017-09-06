package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = CustomerRepository)
class CustomerRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    CustomerRepository customerRepository

    void setup() {
        entityManager.persistAndFlush(customer1)
    }

    def "should persist customer"() {
        when:
        def savedCustomer = customerRepository.findByFirstName(customer1.firstName)

        then:
        savedCustomer.id != null
        savedCustomer.businessInterests.contains(customer1.businessInterests)
        savedCustomer.lastName == customer1.lastName
    }

    def "should delete customer"() {
        when:
        customerRepository.delete(customer1)

        then:
        customerRepository.findByLastName(customer1.lastName) == null
    }

    def "should update customer"() {
        given:
        customer1.firstName = "Roland"

        when:
        def updatedCustomer = customerRepository.save(customer1)

        then:
        updatedCustomer.firstName == "Roland"
        updatedCustomer.businessInterests.contains(customer1.businessInterests)
    }

    def "should return null if customer not exists"() {
        when:
        customerRepository.delete(customer1)

        then:
        customerRepository.findByFirstName("Jake") == null
    }
}
