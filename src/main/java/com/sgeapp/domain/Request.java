package com.sgeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sgeapp.domain.enumeration.RequestStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Request.
 */
@Entity
@Table(name = "request")
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    @OneToMany(mappedBy = "request")
    @JsonIgnoreProperties(value = { "request", "company" }, allowSetters = true)
    private Set<TimeSheet> timeSheets = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "internalUser", "requests", "campaigns", "company" }, allowSetters = true)
    private ApplicationUser owner;

    @ManyToOne
    @JsonIgnoreProperties(value = { "requests", "admin" }, allowSetters = true)
    private Campaign compaign;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Request id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return this.status;
    }

    public Request status(RequestStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Set<TimeSheet> getTimeSheets() {
        return this.timeSheets;
    }

    public void setTimeSheets(Set<TimeSheet> timeSheets) {
        if (this.timeSheets != null) {
            this.timeSheets.forEach(i -> i.setRequest(null));
        }
        if (timeSheets != null) {
            timeSheets.forEach(i -> i.setRequest(this));
        }
        this.timeSheets = timeSheets;
    }

    public Request timeSheets(Set<TimeSheet> timeSheets) {
        this.setTimeSheets(timeSheets);
        return this;
    }

    public Request addTimeSheet(TimeSheet timeSheet) {
        this.timeSheets.add(timeSheet);
        timeSheet.setRequest(this);
        return this;
    }

    public Request removeTimeSheet(TimeSheet timeSheet) {
        this.timeSheets.remove(timeSheet);
        timeSheet.setRequest(null);
        return this;
    }

    public ApplicationUser getOwner() {
        return this.owner;
    }

    public void setOwner(ApplicationUser applicationUser) {
        this.owner = applicationUser;
    }

    public Request owner(ApplicationUser applicationUser) {
        this.setOwner(applicationUser);
        return this;
    }

    public Campaign getCompaign() {
        return this.compaign;
    }

    public void setCompaign(Campaign campaign) {
        this.compaign = campaign;
    }

    public Request compaign(Campaign campaign) {
        this.setCompaign(campaign);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }
        return id != null && id.equals(((Request) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Request{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
