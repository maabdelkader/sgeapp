package com.sgeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sgeapp.domain.enumeration.CompanyType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "raison_social")
    private String raisonSocial;

    @Enumerated(EnumType.STRING)
    @Column(name = "company_type")
    private CompanyType companyType;

    @OneToOne
    @JoinColumn(unique = true)
    private SocialOrganization socialOrganization;

    @OneToMany(mappedBy = "company")
    @JsonIgnoreProperties(value = { "internalUser", "requests", "campaigns", "company" }, allowSetters = true)
    private Set<ApplicationUser> applicationUsers = new HashSet<>();

    @OneToMany(mappedBy = "company")
    @JsonIgnoreProperties(value = { "request", "company" }, allowSetters = true)
    private Set<TimeSheet> timeSheets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRaisonSocial() {
        return this.raisonSocial;
    }

    public Company raisonSocial(String raisonSocial) {
        this.setRaisonSocial(raisonSocial);
        return this;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public CompanyType getCompanyType() {
        return this.companyType;
    }

    public Company companyType(CompanyType companyType) {
        this.setCompanyType(companyType);
        return this;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public SocialOrganization getSocialOrganization() {
        return this.socialOrganization;
    }

    public void setSocialOrganization(SocialOrganization socialOrganization) {
        this.socialOrganization = socialOrganization;
    }

    public Company socialOrganization(SocialOrganization socialOrganization) {
        this.setSocialOrganization(socialOrganization);
        return this;
    }

    public Set<ApplicationUser> getApplicationUsers() {
        return this.applicationUsers;
    }

    public void setApplicationUsers(Set<ApplicationUser> applicationUsers) {
        if (this.applicationUsers != null) {
            this.applicationUsers.forEach(i -> i.setCompany(null));
        }
        if (applicationUsers != null) {
            applicationUsers.forEach(i -> i.setCompany(this));
        }
        this.applicationUsers = applicationUsers;
    }

    public Company applicationUsers(Set<ApplicationUser> applicationUsers) {
        this.setApplicationUsers(applicationUsers);
        return this;
    }

    public Company addApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.add(applicationUser);
        applicationUser.setCompany(this);
        return this;
    }

    public Company removeApplicationUser(ApplicationUser applicationUser) {
        this.applicationUsers.remove(applicationUser);
        applicationUser.setCompany(null);
        return this;
    }

    public Set<TimeSheet> getTimeSheets() {
        return this.timeSheets;
    }

    public void setTimeSheets(Set<TimeSheet> timeSheets) {
        if (this.timeSheets != null) {
            this.timeSheets.forEach(i -> i.setCompany(null));
        }
        if (timeSheets != null) {
            timeSheets.forEach(i -> i.setCompany(this));
        }
        this.timeSheets = timeSheets;
    }

    public Company timeSheets(Set<TimeSheet> timeSheets) {
        this.setTimeSheets(timeSheets);
        return this;
    }

    public Company addTimeSheet(TimeSheet timeSheet) {
        this.timeSheets.add(timeSheet);
        timeSheet.setCompany(this);
        return this;
    }

    public Company removeTimeSheet(TimeSheet timeSheet) {
        this.timeSheets.remove(timeSheet);
        timeSheet.setCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", raisonSocial='" + getRaisonSocial() + "'" +
            ", companyType='" + getCompanyType() + "'" +
            "}";
    }
}
