package com.sgeapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

public class RequestTotalsDto implements Serializable {

    private Long requestId;

    private Integer totalAdmin;

    private Integer totalCommission;

    private Integer totalProximity;

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Integer getTotalAdmin() {
        return totalAdmin;
    }

    public void setTotalAdmin(Integer totalAdmin) {
        this.totalAdmin = totalAdmin;
    }

    public Integer getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Integer totalCommission) {
        this.totalCommission = totalCommission;
    }

    public Integer getTotalProximity() {
        return totalProximity;
    }

    public void setTotalProximity(Integer totalProximity) {
        this.totalProximity = totalProximity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestTotalsDto that = (RequestTotalsDto) o;
        return (
            Objects.equals(requestId, that.requestId) &&
            Objects.equals(totalAdmin, that.totalAdmin) &&
            Objects.equals(totalCommission, that.totalCommission) &&
            Objects.equals(totalProximity, that.totalProximity)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId, totalAdmin, totalCommission, totalProximity);
    }
}
