package com.sgeapp.service.dto;

import com.sgeapp.domain.enumeration.RequestStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.sgeapp.domain.Request} entity.
 */
public class RequestDTO implements Serializable {

    private Long id;

    private RequestStatus status;

    private ApplicationUserDTO owner;

    private CampaignDTO compaign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public ApplicationUserDTO getOwner() {
        return owner;
    }

    public void setOwner(ApplicationUserDTO owner) {
        this.owner = owner;
    }

    public CampaignDTO getCompaign() {
        return compaign;
    }

    public void setCompaign(CampaignDTO compaign) {
        this.compaign = compaign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequestDTO)) {
            return false;
        }

        RequestDTO requestDTO = (RequestDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, requestDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RequestDTO{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", owner=" + getOwner() +
            ", compaign=" + getCompaign() +
            "}";
    }
}
