package com.sgeapp.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A SocialOrganization.
 */
@Entity
@Table(name = "social_organization")
public class SocialOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "admin_quota")
    private Integer adminQuota;

    @Column(name = "commission_quota")
    private Integer commissionQuota;

    @Column(name = "proximity_quota")
    private Integer proximityQuota;

    @Column(name = "number_of_admins")
    private Integer numberOfAdmins;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SocialOrganization id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SocialOrganization name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAdminQuota() {
        return this.adminQuota;
    }

    public SocialOrganization adminQuota(Integer adminQuota) {
        this.setAdminQuota(adminQuota);
        return this;
    }

    public void setAdminQuota(Integer adminQuota) {
        this.adminQuota = adminQuota;
    }

    public Integer getCommissionQuota() {
        return this.commissionQuota;
    }

    public SocialOrganization commissionQuota(Integer commissionQuota) {
        this.setCommissionQuota(commissionQuota);
        return this;
    }

    public void setCommissionQuota(Integer commissionQuota) {
        this.commissionQuota = commissionQuota;
    }

    public Integer getProximityQuota() {
        return this.proximityQuota;
    }

    public SocialOrganization proximityQuota(Integer proximityQuota) {
        this.setProximityQuota(proximityQuota);
        return this;
    }

    public void setProximityQuota(Integer proximityQuota) {
        this.proximityQuota = proximityQuota;
    }

    public Integer getNumberOfAdmins() {
        return this.numberOfAdmins;
    }

    public SocialOrganization numberOfAdmins(Integer numberOfAdmins) {
        this.setNumberOfAdmins(numberOfAdmins);
        return this;
    }

    public void setNumberOfAdmins(Integer numberOfAdmins) {
        this.numberOfAdmins = numberOfAdmins;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialOrganization)) {
            return false;
        }
        return id != null && id.equals(((SocialOrganization) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialOrganization{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", adminQuota=" + getAdminQuota() +
            ", commissionQuota=" + getCommissionQuota() +
            ", proximityQuota=" + getProximityQuota() +
            ", numberOfAdmins=" + getNumberOfAdmins() +
            "}";
    }
}
