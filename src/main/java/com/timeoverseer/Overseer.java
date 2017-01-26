package com.timeoverseer;

import com.timeoverseer.model.Company;
import com.timeoverseer.repository.CompanyRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static java.time.LocalDate.of;

@SpringBootApplication
public class Overseer extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Overseer.class, args);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("/templates/index.html");
    }

//    @Bean
    InitializingBean setup(CompanyRepository companyRepository) {
        return () -> {
            Company apple = new Company("Apple", of(1984, 1, 4), "Software", "Steven Bills", "iPhone");
            companyRepository.save(apple);
        };
    }
}
