package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = TaskRepository)
class TaskRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    TaskRepository taskRepository

    void setup() {
        customer1.addProject(project)
        project.customer = customer1

        project.addSprint(sprint)
        sprint.project = project

        sprint.addTask(task1)
        sprint.addTask(task2)

        entityManager.persistAndFlush(customer1)
    }

    def "should persist task"() {
        when:
        def fetchedTask1 = taskRepository.findByName(task1.name)
        def fetchedTask2 = taskRepository.findByName(task2.name)

        then:
        fetchedTask1.qualification == task1.qualification
        fetchedTask2.qualification == task2.qualification
    }

    def "should delete task"() {
        given:
        sprint.removeTask(task1)

        when:
        taskRepository.delete(task1)

        then:
        taskRepository.findByName(task1.name) == null
        taskRepository.findByName(task2.name) != null
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
