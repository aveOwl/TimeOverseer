package com.timeoverseer.repository

import com.timeoverseer.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import spock.lang.Specification

import static com.timeoverseer.model.enums.Qualification.*
import static java.time.LocalDate.of

@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
class AbstractRepositorySpec extends Specification {
    @Autowired
    TestEntityManager entityManager

    def company = ["Apple", of(1976, 4, 1), "Computer Software", "Steve Jobs", "iPhone"] as Company

    def customer1 = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def customer2 = ["Rob", "Lowe", "Sake", "enuss", "hardware"] as Customer

    def projectManager = ["Jake", "Main", "Glen", "glanes", company, SENIOR, null] as ProjectManager

    def developer1 = ["Rob", "Lowe", "Sake", "enuss", company, TRAINEE, projectManager] as Developer
    def developer2 = ["Katy", "Starr", "Wildow", "glass122", company, JUNIOR, projectManager] as Developer

    def project = ["Windows", "New Generation TV", of(2016, 1, 4), of(2016, 1, 5), customer1, null] as Project
    def sprint = ["First Phase", project] as Sprint

    def task1 = ["First Task", false, MIDDLE, 20L, sprint] as Task
    def task2 = ["Second Task", false, JUNIOR, 10L, sprint] as Task
}
