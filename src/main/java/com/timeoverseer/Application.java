package com.timeoverseer;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Developer;
import com.timeoverseer.model.Employee;
import com.timeoverseer.model.ProjectManager;
import com.timeoverseer.model.enums.Qualification;
import com.timeoverseer.repository.CompanyRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.time.LocalDate;

@SpringBootApplication
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("index");
    }

    @Bean
    InitializingBean seedDatabase(CompanyRepository repository) {
        return () -> {
            Company company1 = new Company("Apple", LocalDate.of(2004, 1, 2), "Software", "Steve", "iPhone");
            Employee e1 = new Developer("Jake", "Ronson", "JK", "asdwqokr12", company1, Qualification.MIDDLE, null);
            Employee e2 = new Developer("Ivy", "Larson", "Ivvvy", "23opkqwe", company1, Qualification.JUNIOR, null);
            company1.addEmployee(e1);
            company1.addEmployee(e2);

            Company company2 = new Company("IBM", LocalDate.of(1994, 2, 15), "Hardware", "Guy", "Machines");
            Employee e3 = new Developer("Rake", "Evens", "Openheim", "12412", company2, Qualification.SENIOR, null);
            Employee e4 = new ProjectManager("Ivan",
                    "Blake",
                    "IvanTheBlake",
                    "oasswper",
                    company2,
                    Qualification.ARCHITECT,
                    null);
            company2.addEmployee(e3);
            company2.addEmployee(e4);

            repository.save(company1);
            repository.save(company2);
        };
    }
}
