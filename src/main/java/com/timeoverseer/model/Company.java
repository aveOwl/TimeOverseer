package com.timeoverseer.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * The <code>Company</code> class represents a business entity that focuses
 * on the development and manufacturing of technology.
 * <p>
 * <code>Company</code> provides business solutions for its
 * customers {@link Customer}.
 */
@Entity
@Table(name = "company", schema = "overseer")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "founded", nullable = false)
    private LocalDate founded;

    @Column(name = "industry", nullable = false)
    private String industry;

    @Column(name = "founders", nullable = false)
    private String founders;

    @Column(name = "products", nullable = false)
    private String products;

    // if company removed -> customers stays
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinTable(name = "company_customer",
            joinColumns = {@JoinColumn(name = "company_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")})
    private Set<Customer> customers;

    // if company removed -> all employees removed as well
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "employer", orphanRemoval = true)
    private Set<Employee> employees;

    protected Company() {
    }

    public Company(String name, LocalDate founded, String industry, String founders, String products) {
        this.name = name;
        this.founded = founded;
        this.industry = industry;
        this.founders = founders;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getFounded() {
        return founded;
    }

    public void setFounded(LocalDate founded) {
        this.founded = founded;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getFounders() {
        return founders;
    }

    public void setFounders(String founders) {
        this.founders = founders;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void addCustomer(Customer customer) {
        if (this.customers == null) {
            this.customers = new HashSet<>();
        }
        this.customers.add(customer);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        if (this.employees == null) {
            this.employees = new HashSet<>();
        }
        this.employees.add(employee);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Company company = (Company) o;

        if (id != null ? !id.equals(company.id) : company.id != null) return false;
        if (!name.equals(company.name)) return false;
        if (!founded.equals(company.founded)) return false;
        if (!industry.equals(company.industry)) return false;
        if (!founders.equals(company.founders)) return false;
        return products.equals(company.products);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + name.hashCode();
        result = 31 * result + founded.hashCode();
        result = 31 * result + industry.hashCode();
        result = 31 * result + founders.hashCode();
        result = 31 * result + products.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founded=" + founded +
                ", industry='" + industry + '\'' +
                ", founders='" + founders + '\'' +
                ", products='" + products + '\'' +
                ", customers=" + customers +
                ", employees=" + employees +
                '}';
    }
}
