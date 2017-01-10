package com.timeoverseer.service

import com.timeoverseer.model.Company
import com.timeoverseer.model.Customer
import com.timeoverseer.model.Developer
import com.timeoverseer.repository.CompanyRepository
import com.timeoverseer.repository.EmployeeRepository
import spock.lang.Specification

import javax.persistence.EntityExistsException
import javax.persistence.EntityNotFoundException
import java.time.LocalDate

import static com.timeoverseer.model.enums.Qualification.TRAINEE

class CompanyServiceSpec extends Specification {
    def companyRepository = Mock(CompanyRepository)
    def employeeRepository = Mock(EmployeeRepository)

    def companyService = [companyRepository, employeeRepository] as CompanyServiceImpl

    def company = ["IBM", LocalDate.of(1911, 6, 16), "Cloud computing", "Charles Flint", "Computer Machines"] as Company
    def developer = ["Rob", "Lowe", "Sake", "enuss", company, TRAINEE, null] as Developer

    def "should add company"() {
        when:
        companyService.addCompany(company)

        then:
        1 * companyRepository.save(company) >> company
        0 * companyRepository._
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
        0 * companyRepository._
    }

    def "should update company"() {
        given:
        company.id = 1

        when:
        companyService.updateCompany(company)

        then:
        1 * companyRepository.save(company) >> company
        0 * companyRepository._
    }

    def "should not update company with null id"() {
        when:
        companyService.updateCompany(company)

        then:
        EntityNotFoundException e = thrown()
        e.message.contains("Company id is null")

        and:
        0 * companyRepository._
    }

    def "should find company by id"() {
        given:
        company.id = 15

        when:
        companyService.findById(15)

        then:
        1 * companyRepository.findOne(15) >> company
        0 * companyRepository._
    }

    def "should find company by name"() {
        when:
        companyService.findByName("IBM")

        then:
        1 * companyRepository.findByName("IBM") >> company
        0 * companyRepository._
    }

    def "should not find company by empty name"() {
        when:
        companyService.findByName("")

        then:
        IllegalArgumentException e = thrown()
        e.message.contains("must not be empty")

        and:
        0 * companyRepository._
    }

    def "should delete company"() {
        given:
        company.id = 1

        when:
        companyService.delete(company)

        then:
        1 * companyRepository.delete(company)
        0 * companyRepository._
    }


    def "should delete company by id"() {
        given:
        company.id = 99

        when:
        companyService.delete(99)

        then:
        1 * companyRepository.findOne(99) >> company
        1 * companyRepository.delete(company)
        0 * companyRepository._
    }

    def "should not delete company with null id"() {
        when:
        companyService.delete(company)

        then:
        EntityNotFoundException e = thrown()
        e.message.contains("Company id is null")

        and:
        0 * companyRepository._
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
        1 * companyRepository.delete(company)
        0 * companyRepository._

        and:
        customer.companies.size() == old(customer.companies.size()) - 1
    }

    def "should find all companies"() {
        when:
        companyService.findAll()

        then:
        1 * companyRepository.findAll() >> [company]
        0 * companyRepository._
    }

    def "should add employee"() {
        given:
        company.id = 1
        developer.id = 1

        when:
        companyService.addEmployee(1, developer)

        then:
        company.findEmployeeById(1) != null

        and:
        1 * companyRepository.findOne(1) >> company
        1 * companyRepository.save(company) >> company
        0 * companyRepository._
    }

    def "should find employee by id"() {
        given:
        company.id = 5
        developer.id = 7
        company.addEmployee(developer)

        when:
        def dev = companyService.findEmployeeById(5, 7)

        then:
        dev == developer

        and:
        1 * companyRepository.findOne(5) >> company
        0 * companyRepository._
    }

    def "should delete employee"() {
        given:
        company.id = 13
        developer.id = 9
        company.addEmployee(developer)

        when:
        def dev = companyService.deleteEmployee(13, 9)

        then:
        dev == developer
        company.employees.size() == old(company.employees.size()) - 1

        and:
        1 * companyRepository.findOne(13) >> company
        1 * employeeRepository.delete(developer)
        0 * companyRepository._
        0 * employeeRepository._
    }

    def "should find all employees"() {
        given:
        company.id = 15
        company.addEmployee(developer)

        when:
        def all = companyService.findAllEmployees(15)

        then:
        all == [developer]

        and:
        1 * companyRepository.findOne(15) >> company
        0 * companyRepository._
    }
}
