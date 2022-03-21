package com.sgeapp.service.dto;

import com.sgeapp.domain.enumeration.TimeSheetStatus;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link com.sgeapp.domain.TimeSheet} entity.
 */
public class TimeSheetDTO implements Serializable {

    private Long id;

    private String employeeCivility;

    private String employeeFirstName;

    private String employeeLastName;

    private String registryNumber;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private String direction;

    private String division;

    private String um;

    private TimeSheetStatus status;

    private String ccas;

    private Integer nbHoursCcas;

    private String coordinatingCommittee;

    private Integer nbHoursCdc;

    private Integer nbHoursAdmin;

    private Integer nbHoursPotFdCfdt;

    private Integer nbHoursPotFdCgt;

    private Integer nbHoursPotFdFo;

    private Integer nbHoursPotFdCfeCgc;

    private String commissionType;

    private Integer nbHoursCommision;

    private String proximityType;

    private Integer nbHoursProximity;

    private RequestDTO request;

    private CompanyDTO company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCivility() {
        return employeeCivility;
    }

    public void setEmployeeCivility(String employeeCivility) {
        this.employeeCivility = employeeCivility;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getRegistryNumber() {
        return registryNumber;
    }

    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getUm() {
        return um;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public TimeSheetStatus getStatus() {
        return status;
    }

    public void setStatus(TimeSheetStatus status) {
        this.status = status;
    }

    public String getCcas() {
        return ccas;
    }

    public void setCcas(String ccas) {
        this.ccas = ccas;
    }

    public Integer getNbHoursCcas() {
        return nbHoursCcas;
    }

    public void setNbHoursCcas(Integer nbHoursCcas) {
        this.nbHoursCcas = nbHoursCcas;
    }

    public String getCoordinatingCommittee() {
        return coordinatingCommittee;
    }

    public void setCoordinatingCommittee(String coordinatingCommittee) {
        this.coordinatingCommittee = coordinatingCommittee;
    }

    public Integer getNbHoursCdc() {
        return nbHoursCdc;
    }

    public void setNbHoursCdc(Integer nbHoursCdc) {
        this.nbHoursCdc = nbHoursCdc;
    }

    public Integer getNbHoursAdmin() {
        return nbHoursAdmin;
    }

    public void setNbHoursAdmin(Integer nbHoursAdmin) {
        this.nbHoursAdmin = nbHoursAdmin;
    }

    public Integer getNbHoursPotFdCfdt() {
        return nbHoursPotFdCfdt;
    }

    public void setNbHoursPotFdCfdt(Integer nbHoursPotFdCfdt) {
        this.nbHoursPotFdCfdt = nbHoursPotFdCfdt;
    }

    public Integer getNbHoursPotFdCgt() {
        return nbHoursPotFdCgt;
    }

    public void setNbHoursPotFdCgt(Integer nbHoursPotFdCgt) {
        this.nbHoursPotFdCgt = nbHoursPotFdCgt;
    }

    public Integer getNbHoursPotFdFo() {
        return nbHoursPotFdFo;
    }

    public void setNbHoursPotFdFo(Integer nbHoursPotFdFo) {
        this.nbHoursPotFdFo = nbHoursPotFdFo;
    }

    public Integer getNbHoursPotFdCfeCgc() {
        return nbHoursPotFdCfeCgc;
    }

    public void setNbHoursPotFdCfeCgc(Integer nbHoursPotFdCfeCgc) {
        this.nbHoursPotFdCfeCgc = nbHoursPotFdCfeCgc;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public Integer getNbHoursCommision() {
        return nbHoursCommision;
    }

    public void setNbHoursCommision(Integer nbHoursCommision) {
        this.nbHoursCommision = nbHoursCommision;
    }

    public String getProximityType() {
        return proximityType;
    }

    public void setProximityType(String proximityType) {
        this.proximityType = proximityType;
    }

    public Integer getNbHoursProximity() {
        return nbHoursProximity;
    }

    public void setNbHoursProximity(Integer nbHoursProximity) {
        this.nbHoursProximity = nbHoursProximity;
    }

    public RequestDTO getRequest() {
        return request;
    }

    public void setRequest(RequestDTO request) {
        this.request = request;
    }

    public CompanyDTO getCompany() {
        return company;
    }

    public void setCompany(CompanyDTO company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheetDTO)) {
            return false;
        }

        TimeSheetDTO timeSheetDTO = (TimeSheetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, timeSheetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheetDTO{" +
            "id=" + getId() +
            ", employeeCivility='" + getEmployeeCivility() + "'" +
            ", employeeFirstName='" + getEmployeeFirstName() + "'" +
            ", employeeLastName='" + getEmployeeLastName() + "'" +
            ", registryNumber='" + getRegistryNumber() + "'" +
            ", dateFrom='" + getDateFrom() + "'" +
            ", dateTo='" + getDateTo() + "'" +
            ", direction='" + getDirection() + "'" +
            ", division='" + getDivision() + "'" +
            ", um='" + getUm() + "'" +
            ", status='" + getStatus() + "'" +
            ", ccas='" + getCcas() + "'" +
            ", nbHoursCcas=" + getNbHoursCcas() +
            ", coordinatingCommittee='" + getCoordinatingCommittee() + "'" +
            ", nbHoursCdc=" + getNbHoursCdc() +
            ", nbHoursAdmin=" + getNbHoursAdmin() +
            ", nbHoursPotFdCfdt=" + getNbHoursPotFdCfdt() +
            ", nbHoursPotFdCgt=" + getNbHoursPotFdCgt() +
            ", nbHoursPotFdFo=" + getNbHoursPotFdFo() +
            ", nbHoursPotFdCfeCgc=" + getNbHoursPotFdCfeCgc() +
            ", commissionType='" + getCommissionType() + "'" +
            ", nbHoursCommision=" + getNbHoursCommision() +
            ", proximityType='" + getProximityType() + "'" +
            ", nbHoursProximity=" + getNbHoursProximity() +
            ", request=" + getRequest() +
            ", company=" + getCompany() +
            "}";
    }
}
