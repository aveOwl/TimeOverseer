package com.timeoverseer.repository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration(classes = [ProjectRepository, ProjectManagerRepository])
class ProjectProjectManagerSpec extends AbstractRepositorySpec {
    @Autowired
    ProjectRepository projectRepository
    @Autowired
    ProjectManagerRepository projectManagerRepository

    void setup() {
        company.addCustomer(customer1)
        customer1.addCompany(company)

        entityManager.persistAndFlush(company)

        customer1.addProject(project)
        company.addEmployee(projectManager)

        project.projectManager = projectManager
        projectManager.project = project
    }

    def "should save project manager with project"() {
        given:
        entityManager.persistAndFlush(project)

        when:
        def savedPM = projectManagerRepository.findByLogin(projectManager.login)

        then:
        savedPM.id != null
        savedPM.qualification == projectManager.qualification
    }

    def "should save project with project manager"() {
        given:
        entityManager.persistAndFlush(projectManager)

        when:
        def savedProject = projectRepository.findByCustomer(customer1)

        then:
        savedProject.description.contains(project.description)
    }

    def "should not delete project when project manager removed"() {
        given:
        project.projectManager == null
        entityManager.persistAndFlush(projectManager)

        when:
        projectManagerRepository.delete(projectManager)

        then:
        projectRepository.findByCustomer(customer1) != null
    }

    def "should not delete project manager when project removed"() {
        given:
        projectManager.project == null
        entityManager.persistAndFlush(project)

        when:
        projectRepository.delete(project)

        then:
        projectManagerRepository.findByFirstName(projectManager.firstName) != null
    }
}
