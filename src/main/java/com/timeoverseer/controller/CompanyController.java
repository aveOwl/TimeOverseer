package com.timeoverseer.controller;

import com.timeoverseer.model.Company;
import com.timeoverseer.service.CompanyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/companies")
public class CompanyController {

    private CompanyService companyService;

    CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping(value = "/company", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company savedCompany = companyService.addCompany(company);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/companies/company/{id}")
                .buildAndExpand(company.getId())
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriOfNewResource);
        return new ResponseEntity<>(savedCompany, headers, HttpStatus.CREATED);
    }
}
