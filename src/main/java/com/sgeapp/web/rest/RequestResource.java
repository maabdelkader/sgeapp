package com.sgeapp.web.rest;

import com.sgeapp.domain.enumeration.RequestStatus;
import com.sgeapp.domain.enumeration.TimeSheetStatus;
import com.sgeapp.repository.RequestRepository;
import com.sgeapp.repository.TimeSheetRepository;
import com.sgeapp.security.AuthoritiesConstants;
import com.sgeapp.service.ApplicationUserService;
import com.sgeapp.service.RequestService;
import com.sgeapp.service.TimeSheetService;
import com.sgeapp.service.dto.ApplicationUserDTO;
import com.sgeapp.service.dto.RequestDTO;
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
 * REST controller for managing {@link com.sgeapp.domain.Request}.
 */
@RestController
@RequestMapping("/api")
public class RequestResource {

    private final Logger log = LoggerFactory.getLogger(RequestResource.class);

    private static final String ENTITY_NAME = "request";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RequestService requestService;

    private final RequestRepository requestRepository;

    private final ApplicationUserService applicationUserService;

    private final TimeSheetService timeSheetService;

    public RequestResource(
        RequestService requestService,
        RequestRepository requestRepository,
        ApplicationUserService applicationUserService,
        TimeSheetService timeSheetService
    ) {
        this.requestService = requestService;
        this.requestRepository = requestRepository;
        this.applicationUserService = applicationUserService;
        this.timeSheetService = timeSheetService;
    }

    /**
     * {@code POST  /requests} : Create a new request.
     *
     * @param requestDTO the requestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new requestDTO, or with status {@code 400 (Bad Request)} if the request has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/requests")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.CMCAS + "\")")
    public ResponseEntity<RequestDTO> createRequest(@RequestBody RequestDTO requestDTO) throws URISyntaxException {
        log.debug("REST request to save Request : {}", requestDTO);
        if (requestDTO.getId() != null) {
            throw new BadRequestAlertException("A new request cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationUserDTO currentUser = applicationUserService.getCurrentApplicationUser().get();
        Optional<RequestDTO> optionalRequestDTO = requestService.findByCampaignAndOwner(
            requestDTO.getCompaign().getId(),
            currentUser.getId()
        );
        if (optionalRequestDTO.isPresent()) {
            throw new BadRequestAlertException("A request already exists for this campaign", ENTITY_NAME, "idexists");
        }
        requestDTO.setOwner(currentUser);
        requestDTO.setStatus(RequestStatus.CREATED);
        RequestDTO result = requestService.save(requestDTO);
        return ResponseEntity
            .created(new URI("/api/requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /requests/:id} : Updates an existing request.
     *
     * @param id the id of the requestDTO to save.
     * @param requestDTO the requestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the requestDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> updateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Request : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RequestDTO result = requestService.save(requestDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /requests/:id} : Partial updates given fields of an existing request, field will ignore if it is null
     *
     * @param id the id of the requestDTO to save.
     * @param requestDTO the requestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated requestDTO,
     * or with status {@code 400 (Bad Request)} if the requestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the requestDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the requestDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/requests/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RequestDTO> partialUpdateRequest(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody RequestDTO requestDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Request partially : {}, {}", id, requestDTO);
        if (requestDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, requestDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!requestRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RequestDTO> result = requestService.partialUpdate(requestDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString())
        );
    }

    @PatchMapping("/request/validation/cmcas/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.CMCAS + "\")")
    public ResponseEntity<RequestDTO> validateRequestByCMCAS(@PathVariable(value = "id", required = false) final Long id) {
        Optional<RequestDTO> requestDTOOptional = requestService.findOne(id);
        if (!requestDTOOptional.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RequestDTO requestDTO = requestDTOOptional.get();
        if (!RequestStatus.CREATED.equals(requestDTO.getStatus())) {
            throw new BadRequestAlertException("Request not  ready for validation", ENTITY_NAME, "badstatus");
        }
        RequestDTO result = updateRequestAndTimeSheetsStatus(
            requestDTO,
            RequestStatus.SGE_VALIDATION_PENDING,
            TimeSheetStatus.SGE_VALIDATION_PENDING
        );
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString()))
            .body(result);
    }

    @PatchMapping("/request/validation/sge/{id}")
    @PreAuthorize("hasAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<RequestDTO> validateRequestBySGE(@PathVariable(value = "id", required = false) final Long id) {
        Optional<RequestDTO> requestDTOOptional = requestService.findOne(id);
        if (!requestDTOOptional.isPresent()) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }
        RequestDTO requestDTO = requestDTOOptional.get();
        if (!RequestStatus.SGE_VALIDATION_PENDING.equals(requestDTO.getStatus())) {
            throw new BadRequestAlertException("Request not  ready for validation", ENTITY_NAME, "badstatus");
        }
        RequestDTO result = updateRequestAndTimeSheetsStatus(
            requestDTO,
            RequestStatus.EMPLOYER_VALIDATION_PENDING,
            TimeSheetStatus.EMPLOYER_VALIDATION_PENDING
        );
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, requestDTO.getId().toString()))
            .body(result);
    }

    private RequestDTO updateRequestAndTimeSheetsStatus(
        RequestDTO requestDTO,
        RequestStatus newRequestStatus,
        TimeSheetStatus newTimeSheetStatus
    ) {
        List<TimeSheetDTO> timeSheetDTOList = timeSheetService.findAllByRequestId(requestDTO.getId());
        timeSheetDTOList.forEach(ts -> ts.setStatus(newTimeSheetStatus));
        timeSheetService.saveAll(timeSheetDTOList);

        requestDTO.setStatus(newRequestStatus);
        return requestService.save(requestDTO);
    }

    /**
     * {@code GET  /requests} : get all the requests.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of requests in body.
     */
    @GetMapping("/requests")
    public List<RequestDTO> getAllRequests() {
        log.debug("REST request to get all Requests");
        return requestService.findAll();
    }

    /**
     * {@code GET  /requests/:id} : get the "id" request.
     *
     * @param id the id of the requestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the requestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/requests/{id}")
    public ResponseEntity<RequestDTO> getRequest(@PathVariable Long id) {
        log.debug("REST request to get Request : {}", id);
        Optional<RequestDTO> requestDTO = requestService.findOne(id);
        return ResponseUtil.wrapOrNotFound(requestDTO);
    }

    /**
     * {@code DELETE  /requests/:id} : delete the "id" request.
     *
     * @param id the id of the requestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/requests/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        log.debug("REST request to delete Request : {}", id);
        requestService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
