package com.sgeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A ApplicationUser.
 */
@Entity
@Table(name = "application_user")
public class ApplicationUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User internalUser;

    @OneToMany(mappedBy = "owner")
    @JsonIgnoreProperties(value = { "timeSheets", "owner", "compaign" }, allowSetters = true)
    private Set<Request> requests = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    @JsonIgnoreProperties(value = { "requests", "admin" }, allowSetters = true)
    private Set<Campaign> campaigns = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "socialOrganization", "applicationUsers", "timeSheets" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ApplicationUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getInternalUser() {
        return this.internalUser;
    }

    public void setInternalUser(User user) {
        this.internalUser = user;
    }

    public ApplicationUser internalUser(User user) {
        this.setInternalUser(user);
        return this;
    }

    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        if (this.requests != null) {
            this.requests.forEach(i -> i.setOwner(null));
        }
        if (requests != null) {
            requests.forEach(i -> i.setOwner(this));
        }
        this.requests = requests;
    }

    public ApplicationUser requests(Set<Request> requests) {
        this.setRequests(requests);
        return this;
    }

    public ApplicationUser addRequest(Request request) {
        this.requests.add(request);
        request.setOwner(this);
        return this;
    }

    public ApplicationUser removeRequest(Request request) {
        this.requests.remove(request);
        request.setOwner(null);
        return this;
    }

    public Set<Campaign> getCampaigns() {
        return this.campaigns;
    }

    public void setCampaigns(Set<Campaign> campaigns) {
        if (this.campaigns != null) {
            this.campaigns.forEach(i -> i.setAdmin(null));
        }
        if (campaigns != null) {
            campaigns.forEach(i -> i.setAdmin(this));
        }
        this.campaigns = campaigns;
    }

    public ApplicationUser campaigns(Set<Campaign> campaigns) {
        this.setCampaigns(campaigns);
        return this;
    }

    public ApplicationUser addCampaign(Campaign campaign) {
        this.campaigns.add(campaign);
        campaign.setAdmin(this);
        return this;
    }

    public ApplicationUser removeCampaign(Campaign campaign) {
        this.campaigns.remove(campaign);
        campaign.setAdmin(null);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ApplicationUser company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationUser)) {
            return false;
        }
        return id != null && id.equals(((ApplicationUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ApplicationUser{" +
            "id=" + getId() +
            "}";
    }
}
