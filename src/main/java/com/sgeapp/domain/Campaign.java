package com.sgeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Campaign.
 */
@Entity
@Table(name = "campaign")
public class Campaign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "compaign")
    @JsonIgnoreProperties(value = { "timeSheets", "owner", "compaign" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "requests", "campaigns", "company" }, allowSetters = true)
    private ApplicationUser admin;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Campaign id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Campaign name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateFrom() {
        return this.dateFrom;
    }

    public Campaign dateFrom(LocalDate dateFrom) {
        this.setDateFrom(dateFrom);
        return this;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return this.dateTo;
    }

    public Campaign dateTo(LocalDate dateTo) {
        this.setDateTo(dateTo);
        return this;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getDescription() {
        return this.description;
    }

    public Campaign description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setCompaign(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setCompaign(this));
        }
        this.requests = requests;
    }

    public Campaign requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public Campaign addRequest(Request request) {
        this.requests.add(request);
        request.setCompaign(this);
        return this;
    }

    public Campaign removeRequest(Request request) {
        this.requests.remove(request);
        request.setCompaign(null);
        return this;
    }

    public ApplicationUser getAdmin() {
        return this.admin;
    }

    public void setAdmin(ApplicationUser applicationUser) {
        this.admin = applicationUser;
    }

    public Campaign admin(ApplicationUser applicationUser) {
        this.setAdmin(applicationUser);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Campaign)) {
            return false;
        }
        return id != null && id.equals(((Campaign) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Campaign{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
