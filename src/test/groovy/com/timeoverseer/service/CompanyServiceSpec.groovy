package com.timeoverseer.service

import com.timeoverseer.model.Company
import com.timeoverseer.model.Customer
import com.timeoverseer.repository.CompanyRepository
import spock.lang.Specification

import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import java.time.LocalDate

class CompanyServiceSpec extends Specification {
    def repository = Mock(CompanyRepository)

    def companyService = [repository] as CompanyServiceImpl

    def company = ["IBM", LocalDate.of(1911, 6, 16), "Cloud computing", "Charles Flint", "Computer Machines"] as Company

    def "should add company"() {
        when:
        companyService.addCompany(company)

        then:
        1 * repository.save(company) >> company
        0 * repository._
    }

    def "should not save company with id"() {
        given:
        company.id = 1

        when:
        companyService.addCompany(company)

        then:
        EntityExistsException e = thrown()
        e.message.contains("already exists")

        and:
        0 * repository._
    }

    def "should update company"() {
        given:
        company.id = 1

        when:
        companyService.updateCompany(company)

        then:
        1 * repository.save(company) >> company
        0 * repository._
    }

    def "should not update company with null id"() {
        when:
        companyService.updateCompany(company)

        then:
        EntityNotFoundException e = thrown()
        e.message.contains("Company id is null")

        and:
        0 * repository._
    }

    def "should find company by id"() {
        given:
        company.id = 15

        when:
        companyService.findById(15)

        then:
        1 * repository.findOne(15) >> company
        0 * repository._
    }

    def "should find company by name"() {
        when:
        companyService.findByName("IBM")

        then:
        1 * repository.findByName("IBM") >> company
        0 * repository._
    }

    def "should not find company by empty name"() {
        when:
        companyService.findByName("")

        then:
        IllegalArgumentException e = thrown()
        e.message.contains("must not be empty")

        and:
        0 * repository._
    }

    def "should delete company"() {
        given:
        company.id = 1

        when:
        companyService.delete(company)

        then:
        1 * repository.delete(company)
        0 * repository._
    }

    def "should not delete company with null id"() {
        when:
        companyService.delete(company)

        then:
        EntityNotFoundException e = thrown()
        e.message.contains("Company id is null")

        and:
        0 * repository._
    }

    def "should delete company with customers"() {
        given:
        def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer

        company.id = 11
        company.addCustomer(customer)
        customer.addCompany(company)

        when:
        companyService.delete(company)

        then:
        1 * repository.delete(company)
        0 * repository._

        and:
        customer.companies.size() == old(customer.companies.size()) - 1
    }

    def "should find all companies"() {
        when:
        companyService.findAll()

        then:
        1 * repository.findAll() >> [company]
        0 * repository._
    }
}
