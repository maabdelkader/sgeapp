package com.sgeapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sgeapp.domain.enumeration.TimeSheetStatus;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 * A TimeSheet.
 */
@Entity
@Table(name = "time_sheet")
public class TimeSheet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "employee_civility")
    private String employeeCivility;

    @Column(name = "employee_first_name")
    private String employeeFirstName;

    @Column(name = "employee_last_name")
    private String employeeLastName;

    @Column(name = "registry_number")
    private String registryNumber;

    @Column(name = "date_from")
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    @Column(name = "direction")
    private String direction;

    @Column(name = "division")
    private String division;

    @Column(name = "um")
    private String um;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TimeSheetStatus status;

    @Column(name = "ccas")
    private String ccas;

    @Column(name = "nb_hours_ccas")
    private Integer nbHoursCcas;

    @Column(name = "coordinating_committee")
    private String coordinatingCommittee;

    @Column(name = "nb_hours_cdc")
    private Integer nbHoursCdc;

    @Column(name = "nb_hours_admin")
    private Integer nbHoursAdmin;

    @Column(name = "nb_hours_pot_fd_cfdt")
    private Integer nbHoursPotFdCfdt;

    @Column(name = "nb_hours_pot_fd_cgt")
    private Integer nbHoursPotFdCgt;

    @Column(name = "nb_hours_pot_fd_fo")
    private Integer nbHoursPotFdFo;

    @Column(name = "nb_hours_pot_fd_cfe_cgc")
    private Integer nbHoursPotFdCfeCgc;

    @Column(name = "commission_type")
    private String commissionType;

    @Column(name = "nb_hours_commision")
    private Integer nbHoursCommision;

    @Column(name = "proximity_type")
    private String proximityType;

    @Column(name = "nb_hours_proximity")
    private Integer nbHoursProximity;

    @ManyToOne
    @JsonIgnoreProperties(value = { "timeSheets", "owner", "compaign" }, allowSetters = true)
    private Request request;

    @ManyToOne
    @JsonIgnoreProperties(value = { "socialOrganization", "applicationUsers", "timeSheets" }, allowSetters = true)
    private Company company;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TimeSheet id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeCivility() {
        return this.employeeCivility;
    }

    public TimeSheet employeeCivility(String employeeCivility) {
        this.setEmployeeCivility(employeeCivility);
        return this;
    }

    public void setEmployeeCivility(String employeeCivility) {
        this.employeeCivility = employeeCivility;
    }

    public String getEmployeeFirstName() {
        return this.employeeFirstName;
    }

    public TimeSheet employeeFirstName(String employeeFirstName) {
        this.setEmployeeFirstName(employeeFirstName);
        return this;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeLastName() {
        return this.employeeLastName;
    }

    public TimeSheet employeeLastName(String employeeLastName) {
        this.setEmployeeLastName(employeeLastName);
        return this;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getRegistryNumber() {
        return this.registryNumber;
    }

    public TimeSheet registryNumber(String registryNumber) {
        this.setRegistryNumber(registryNumber);
        return this;
    }

    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public LocalDate getDateFrom() {
        return this.dateFrom;
    }

    public TimeSheet dateFrom(LocalDate dateFrom) {
        this.setDateFrom(dateFrom);
        return this;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return this.dateTo;
    }

    public TimeSheet dateTo(LocalDate dateTo) {
        this.setDateTo(dateTo);
        return this;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    public String getDirection() {
        return this.direction;
    }

    public TimeSheet direction(String direction) {
        this.setDirection(direction);
        return this;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDivision() {
        return this.division;
    }

    public TimeSheet division(String division) {
        this.setDivision(division);
        return this;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getUm() {
        return this.um;
    }

    public TimeSheet um(String um) {
        this.setUm(um);
        return this;
    }

    public void setUm(String um) {
        this.um = um;
    }

    public TimeSheetStatus getStatus() {
        return this.status;
    }

    public TimeSheet status(TimeSheetStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(TimeSheetStatus status) {
        this.status = status;
    }

    public String getCcas() {
        return this.ccas;
    }

    public TimeSheet ccas(String ccas) {
        this.setCcas(ccas);
        return this;
    }

    public void setCcas(String ccas) {
        this.ccas = ccas;
    }

    public Integer getNbHoursCcas() {
        return this.nbHoursCcas;
    }

    public TimeSheet nbHoursCcas(Integer nbHoursCcas) {
        this.setNbHoursCcas(nbHoursCcas);
        return this;
    }

    public void setNbHoursCcas(Integer nbHoursCcas) {
        this.nbHoursCcas = nbHoursCcas;
    }

    public String getCoordinatingCommittee() {
        return this.coordinatingCommittee;
    }

    public TimeSheet coordinatingCommittee(String coordinatingCommittee) {
        this.setCoordinatingCommittee(coordinatingCommittee);
        return this;
    }

    public void setCoordinatingCommittee(String coordinatingCommittee) {
        this.coordinatingCommittee = coordinatingCommittee;
    }

    public Integer getNbHoursCdc() {
        return this.nbHoursCdc;
    }

    public TimeSheet nbHoursCdc(Integer nbHoursCdc) {
        this.setNbHoursCdc(nbHoursCdc);
        return this;
    }

    public void setNbHoursCdc(Integer nbHoursCdc) {
        this.nbHoursCdc = nbHoursCdc;
    }

    public Integer getNbHoursAdmin() {
        return this.nbHoursAdmin;
    }

    public TimeSheet nbHoursAdmin(Integer nbHoursAdmin) {
        this.setNbHoursAdmin(nbHoursAdmin);
        return this;
    }

    public void setNbHoursAdmin(Integer nbHoursAdmin) {
        this.nbHoursAdmin = nbHoursAdmin;
    }

    public Integer getNbHoursPotFdCfdt() {
        return this.nbHoursPotFdCfdt;
    }

    public TimeSheet nbHoursPotFdCfdt(Integer nbHoursPotFdCfdt) {
        this.setNbHoursPotFdCfdt(nbHoursPotFdCfdt);
        return this;
    }

    public void setNbHoursPotFdCfdt(Integer nbHoursPotFdCfdt) {
        this.nbHoursPotFdCfdt = nbHoursPotFdCfdt;
    }

    public Integer getNbHoursPotFdCgt() {
        return this.nbHoursPotFdCgt;
    }

    public TimeSheet nbHoursPotFdCgt(Integer nbHoursPotFdCgt) {
        this.setNbHoursPotFdCgt(nbHoursPotFdCgt);
        return this;
    }

    public void setNbHoursPotFdCgt(Integer nbHoursPotFdCgt) {
        this.nbHoursPotFdCgt = nbHoursPotFdCgt;
    }

    public Integer getNbHoursPotFdFo() {
        return this.nbHoursPotFdFo;
    }

    public TimeSheet nbHoursPotFdFo(Integer nbHoursPotFdFo) {
        this.setNbHoursPotFdFo(nbHoursPotFdFo);
        return this;
    }

    public void setNbHoursPotFdFo(Integer nbHoursPotFdFo) {
        this.nbHoursPotFdFo = nbHoursPotFdFo;
    }

    public Integer getNbHoursPotFdCfeCgc() {
        return this.nbHoursPotFdCfeCgc;
    }

    public TimeSheet nbHoursPotFdCfeCgc(Integer nbHoursPotFdCfeCgc) {
        this.setNbHoursPotFdCfeCgc(nbHoursPotFdCfeCgc);
        return this;
    }

    public void setNbHoursPotFdCfeCgc(Integer nbHoursPotFdCfeCgc) {
        this.nbHoursPotFdCfeCgc = nbHoursPotFdCfeCgc;
    }

    public String getCommissionType() {
        return this.commissionType;
    }

    public TimeSheet commissionType(String commissionType) {
        this.setCommissionType(commissionType);
        return this;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public Integer getNbHoursCommision() {
        return this.nbHoursCommision;
    }

    public TimeSheet nbHoursCommision(Integer nbHoursCommision) {
        this.setNbHoursCommision(nbHoursCommision);
        return this;
    }

    public void setNbHoursCommision(Integer nbHoursCommision) {
        this.nbHoursCommision = nbHoursCommision;
    }

    public String getProximityType() {
        return this.proximityType;
    }

    public TimeSheet proximityType(String proximityType) {
        this.setProximityType(proximityType);
        return this;
    }

    public void setProximityType(String proximityType) {
        this.proximityType = proximityType;
    }

    public Integer getNbHoursProximity() {
        return this.nbHoursProximity;
    }

    public TimeSheet nbHoursProximity(Integer nbHoursProximity) {
        this.setNbHoursProximity(nbHoursProximity);
        return this;
    }

    public void setNbHoursProximity(Integer nbHoursProximity) {
        this.nbHoursProximity = nbHoursProximity;
    }

    public Request getRequest() {
        return this.request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public TimeSheet request(Request request) {
        this.setRequest(request);
        return this;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public TimeSheet company(Company company) {
        this.setCompany(company);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeSheet)) {
            return false;
        }
        return id != null && id.equals(((TimeSheet) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TimeSheet{" +
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
            "}";
    }
}
