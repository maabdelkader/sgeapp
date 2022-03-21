package com.sgeapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sgeapp.domain.SocialOrganization} entity.
 */
public class SocialOrganizationDTO implements Serializable {

    private Long id;

    private String name;

    private Integer adminQuota;

    private Integer commissionQuota;

    private Integer proximityQuota;

    private Integer numberOfAdmins;

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

    public Integer getAdminQuota() {
        return adminQuota;
    }

    public void setAdminQuota(Integer adminQuota) {
        this.adminQuota = adminQuota;
    }

    public Integer getCommissionQuota() {
        return commissionQuota;
    }

    public void setCommissionQuota(Integer commissionQuota) {
        this.commissionQuota = commissionQuota;
    }

    public Integer getProximityQuota() {
        return proximityQuota;
    }

    public void setProximityQuota(Integer proximityQuota) {
        this.proximityQuota = proximityQuota;
    }

    public Integer getNumberOfAdmins() {
        return numberOfAdmins;
    }

    public void setNumberOfAdmins(Integer numberOfAdmins) {
        this.numberOfAdmins = numberOfAdmins;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SocialOrganizationDTO)) {
            return false;
        }

        SocialOrganizationDTO socialOrganizationDTO = (SocialOrganizationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, socialOrganizationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SocialOrganizationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", adminQuota=" + getAdminQuota() +
            ", commissionQuota=" + getCommissionQuota() +
            ", proximityQuota=" + getProximityQuota() +
            ", numberOfAdmins=" + getNumberOfAdmins() +
            "}";
    }
}
