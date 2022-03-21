package com.sgeapp.service.dto;

import com.sgeapp.domain.enumeration.CompanyType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sgeapp.domain.Company} entity.
 */
public class CompanyDTO implements Serializable {

    private Long id;

    private String name;

    private String raisonSocial;

    private CompanyType companyType;

    private SocialOrganizationDTO socialOrganization;

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

    public String getRaisonSocial() {
        return raisonSocial;
    }

    public void setRaisonSocial(String raisonSocial) {
        this.raisonSocial = raisonSocial;
    }

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public SocialOrganizationDTO getSocialOrganization() {
        return socialOrganization;
    }

    public void setSocialOrganization(SocialOrganizationDTO socialOrganization) {
        this.socialOrganization = socialOrganization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyDTO)) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", raisonSocial='" + getRaisonSocial() + "'" +
            ", companyType='" + getCompanyType() + "'" +
            ", socialOrganization=" + getSocialOrganization() +
            "}";
    }
}
