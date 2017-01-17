package com.timeoverseer.repository

import com.timeoverseer.model.Customer
import com.timeoverseer.model.Project
import com.timeoverseer.model.Sprint
import com.timeoverseer.model.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Unroll

import static com.timeoverseer.model.enums.Qualification.JUNIOR
import static com.timeoverseer.model.enums.Qualification.MIDDLE
import static java.time.LocalDate.of

@ContextConfiguration(classes = TaskRepository)
@EntityScan(basePackages = "com.timeoverseer.model")
@DataJpaTest
@Unroll
class TaskRepositorySpec extends Specification {
    @Autowired
    TaskRepository taskRepository
    @Autowired
    TestEntityManager entityManager

    def customer = ["Jake", "Main", "Ross", "glanes", "software"] as Customer
    def project = ["Apple", "New Generation TV", of(2016, 1, 4), of(2016, 1, 5), customer, null] as Project
    def sprint = ["First Phase", project] as Sprint

    def task1 = ["First Task", false, MIDDLE, 20L, sprint] as Task
    def task2 = ["Second Task", false, JUNIOR, 10L, sprint] as Task

    void setup() {
        customer.addProject(project)
        project.customer = customer

        project.addSprint(sprint)
        sprint.project = project

        sprint.addTask(task1)
        sprint.addTask(task2)

        entityManager.persistAndFlush(customer)
    }

    def "should persist task"() {
        when:
        def fetchedTask1 = taskRepository.findByName("First Task")
        def fetchedTask2 = taskRepository.findByName("Second Task")

        then:
        fetchedTask1.qualification == MIDDLE
        fetchedTask2.qualification == JUNIOR
    }

    def "should delete task"() {
        given:
        sprint.removeTask(task1)

        when:
        taskRepository.delete(task1)

        then:
        taskRepository.findByName("First Task") == null
        taskRepository.findByName("Second Task") != null
    }

    def "should update task"() {
        given:
        task1.name = "Best Task"

        when:
        def updatedTask = taskRepository.save(task1)

        then:
        updatedTask.name.contains("Best")
    }
}
