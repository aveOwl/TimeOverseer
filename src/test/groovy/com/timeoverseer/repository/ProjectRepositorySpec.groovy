package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = ProjectRepository)
class ProjectRepositorySpec extends AbstractRepositorySpec {
    @Autowired
    ProjectRepository projectRepository

    void setup() {
        customer1.addProject(project)
        entityManager.persistAndFlush(customer1)
    }

    def "should persist project"() {
        when:
        def fetchedProject = projectRepository.findByCustomer(customer1)

        then:
        fetchedProject.id != null
        fetchedProject.description.contains(project.description)
    }

    def "should delete project"() {
        given:
        customer1.removeProject(project)

        when:
        projectRepository.delete(project)

        then:
        projectRepository.findByCustomer(customer1) == null
    }

    def "should update project"() {
        given:
        project.description = "iPod"

        when:
        def updatedProject = projectRepository.save(project)

        then:
        updatedProject.description.contains("iPod")
    }
}
