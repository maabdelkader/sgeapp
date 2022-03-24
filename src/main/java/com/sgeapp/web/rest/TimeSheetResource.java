package com.sgeapp.web.rest;

import com.sgeapp.domain.SocialOrganization;
import com.sgeapp.domain.enumeration.TimeSheetStatus;
import com.sgeapp.repository.TimeSheetRepository;
import com.sgeapp.security.AuthoritiesConstants;
import com.sgeapp.service.RequestService;
import com.sgeapp.service.TimeSheetService;
import com.sgeapp.service.dto.RequestDTO;
import com.sgeapp.service.dto.RequestTotalsDto;
import com.sgeapp.service.dto.SocialOrganizationDTO;
import com.sgeapp.service.dto.TimeSheetDTO;
import com.sgeapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sgeapp.domain.TimeSheet}.
 */
@RestController
@RequestMapping("/api")
public class TimeSheetResource {

    private final Logger log = LoggerFactory.getLogger(TimeSheetResource.class);

    private static final String ENTITY_NAME = "timeSheet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TimeSheetService timeSheetService;

    private final TimeSheetRepository timeSheetRepository;

    private final RequestService requestService;

    public TimeSheetResource(TimeSheetService timeSheetService, TimeSheetRepository timeSheetRepository, RequestService requestService) {
        this.timeSheetService = timeSheetService;
        this.timeSheetRepository = timeSheetRepository;
        this.requestService = requestService;
    }

    /**
     * {@code POST  /time-sheets} : Create a new timeSheet.
     *
     * @param timeSheetDTO the timeSheetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timeSheetDTO, or with status {@code 400 (Bad Request)} if the timeSheet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/time-sheets")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.CMCAS + "\")")
    public ResponseEntity<TimeSheetDTO> createTimeSheet(@RequestBody TimeSheetDTO timeSheetDTO) throws URISyntaxException {
        log.debug("REST request to save TimeSheet : {}", timeSheetDTO);
        if (timeSheetDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeSheet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        validateContingentForNewTimeSheet(timeSheetDTO, null);
        timeSheetDTO.setStatus(TimeSheetStatus.CREATED);
        TimeSheetDTO result = timeSheetService.save(timeSheetDTO);
        return ResponseEntity
            .created(new URI("/api/time-sheets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /time-sheets/:id} : Updates an existing timeSheet.
     *
     * @param id the id of the timeSheetDTO to save.
     * @param timeSheetDTO the timeSheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeSheetDTO,
     * or with status {@code 400 (Bad Request)} if the timeSheetDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the timeSheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/time-sheets/{id}")
    public ResponseEntity<TimeSheetDTO> updateTimeSheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeSheetDTO timeSheetDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TimeSheet : {}, {}", id, timeSheetDTO);
        if (timeSheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeSheetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!timeSheetRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TimeSheetDTO result = timeSheetService.save(timeSheetDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeSheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /time-sheets/:id} : Partial updates given fields of an existing timeSheet, field will ignore if it is null
     *
     * @param id the id of the timeSheetDTO to save.
     * @param timeSheetDTO the timeSheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timeSheetDTO,
     * or with status {@code 400 (Bad Request)} if the timeSheetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the timeSheetDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the timeSheetDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/time-sheets/{id}", consumes = { "application/json", "application/merge-patch+json" })
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.CMCAS + "\") || hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<TimeSheetDTO> partialUpdateTimeSheet(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody TimeSheetDTO timeSheetDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TimeSheet partially : {}, {}", id, timeSheetDTO);
        if (timeSheetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, timeSheetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }
        Optional<TimeSheetDTO> oldTimeSheetDTO = timeSheetService.findOne(id);
        if (!oldTimeSheetDTO.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        validateContingentForNewTimeSheet(timeSheetDTO, oldTimeSheetDTO.get());
        Optional<TimeSheetDTO> result = timeSheetService.partialUpdate(timeSheetDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeSheetDTO.getId().toString())
        );
    }

    @PatchMapping("/time-sheets/validation/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.EMPLOYER + "\")")
    public ResponseEntity<TimeSheetDTO> validateTimeSheetBySge(@PathVariable(value = "id", required = false) final Long id) {
        Optional<TimeSheetDTO> timeSheetDTOOptional = timeSheetService.findOne(id);
        if (!timeSheetDTOOptional.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        TimeSheetDTO timeSheetDTO = timeSheetDTOOptional.get();
        if (!TimeSheetStatus.EMPLOYER_VALIDATION_PENDING.equals(timeSheetDTO.getStatus())) {
            throw new BadRequestAlertException("TimeSheet not  ready for validation", ENTITY_NAME, "badstatus");
        }
        timeSheetDTO.setStatus(TimeSheetStatus.VALIDATED);
        TimeSheetDTO result = timeSheetService.save(timeSheetDTO);
        // TODO - add request validation if all timesheet are validated
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, timeSheetDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /time-sheets} : get all the timeSheets.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timeSheets in body.
     */
    @GetMapping("/time-sheets")
    public List<TimeSheetDTO> getAllTimeSheets() {
        log.debug("REST request to get all TimeSheets");
        return timeSheetService.findAll();
    }

    /**
     * {@code GET  /time-sheets/:id} : get the "id" timeSheet.
     *
     * @param id the id of the timeSheetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timeSheetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/time-sheets/{id}")
    public ResponseEntity<TimeSheetDTO> getTimeSheet(@PathVariable Long id) {
        log.debug("REST request to get TimeSheet : {}", id);
        Optional<TimeSheetDTO> timeSheetDTO = timeSheetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(timeSheetDTO);
    }

    /**
     * {@code DELETE  /time-sheets/:id} : delete the "id" timeSheet.
     *
     * @param id the id of the timeSheetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/time-sheets/{id}")
    public ResponseEntity<Void> deleteTimeSheet(@PathVariable Long id) {
        log.debug("REST request to delete TimeSheet : {}", id);
        timeSheetService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    private void validateContingentForNewTimeSheet(TimeSheetDTO newTimeSheetDTO, TimeSheetDTO oldTimeSheetDTO) {
        RequestDTO requestDTO = requestService.findOne(newTimeSheetDTO.getRequest().getId()).get();
        if (requestDTO.getOwner() != null && requestDTO.getOwner().getCompany() != null) {
            SocialOrganizationDTO socialOrganization = requestDTO.getOwner().getCompany().getSocialOrganization();
            RequestTotalsDto requestTotalsDto = requestService.calculateRequestTotals(newTimeSheetDTO.getRequest().getId());
            int oldTimeSheetAdminHours, oldTimeSheetProximityHours, oldTimeSheetCommissionHours;
            oldTimeSheetAdminHours = oldTimeSheetCommissionHours = oldTimeSheetProximityHours = 0;
            if (oldTimeSheetDTO != null) {
                oldTimeSheetAdminHours = Optional.ofNullable(oldTimeSheetDTO.getNbHoursAdmin()).orElse(0);
                oldTimeSheetProximityHours = Optional.ofNullable(oldTimeSheetDTO.getNbHoursProximity()).orElse(0);
                oldTimeSheetCommissionHours = Optional.ofNullable(oldTimeSheetDTO.getNbHoursCommision()).orElse(0);
            }
            if (newTimeSheetDTO.getNbHoursAdmin() != null) {
                int newTotalAdmin = newTimeSheetDTO.getNbHoursAdmin() + (requestTotalsDto.getTotalAdmin() - oldTimeSheetAdminHours);
                if (newTotalAdmin > socialOrganization.getAdminQuota()) {
                    throw new BadRequestAlertException("Exceeding quota admin", ENTITY_NAME, "exceedquota");
                }
            }
            if (newTimeSheetDTO.getNbHoursProximity() != null) {
                int newTotalProximity =
                    newTimeSheetDTO.getNbHoursProximity() + (requestTotalsDto.getTotalProximity() - oldTimeSheetProximityHours);
                if (newTotalProximity > socialOrganization.getProximityQuota()) {
                    throw new BadRequestAlertException("Exceeding quota proximity", ENTITY_NAME, "exceedquota");
                }
            }
            if (newTimeSheetDTO.getNbHoursCommision() != null) {
                int newTotalCommission =
                    newTimeSheetDTO.getNbHoursCommision() + (requestTotalsDto.getTotalCommission() - oldTimeSheetCommissionHours);
                if (newTotalCommission > socialOrganization.getCommissionQuota()) {
                    throw new BadRequestAlertException("Exceeding quota commission", ENTITY_NAME, "exceedquota");
                }
            }
        }
    }
}
