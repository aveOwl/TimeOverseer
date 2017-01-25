package com.timeoverseer.config;

import com.timeoverseer.model.Company;
import com.timeoverseer.model.Customer;
import com.timeoverseer.model.Project;
import com.timeoverseer.model.Sprint;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Project.class, Customer.class, Sprint.class, Company.class);
    }
}
