package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [SprintRepository, TaskRepository])
class SprintTaskRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    TaskRepository taskRepository
    @Autowired
    SprintRepository sprintRepository

    void setup() {
        customer1.addProject(project)
        project.customer = customer1

        project.addSprint(sprint)
        sprint.project = project

        sprint.addTask(task1)
        sprint.addTask(task2)
        task1.sprint = sprint
        task2.sprint = sprint

        entityManager.persistAndFlush(customer1)
    }

    def "should save tasks with sprint"() {
        expect:
        taskRepository.findByName(task2.name) != null
        taskRepository.findByQualification(task1.qualification) != null
    }

    def "should delete task if sprint removed"() {
        given:
        sprint.removeTask(task1)
        sprint.removeTask(task2)
        task1.sprint == null
        task2.sprint == null

        when:
        sprintRepository.delete(sprint)

        then:
        taskRepository.findByName(task1.name) == null
        taskRepository.findByName(task2.name) == null
    }

    def "should not remove sprint when task removed"() {
        given:
        sprint.removeTask(task1)

        when:
        taskRepository.delete(task1)

        then:
        sprintRepository.findByName(sprint.name) != null
    }
}
