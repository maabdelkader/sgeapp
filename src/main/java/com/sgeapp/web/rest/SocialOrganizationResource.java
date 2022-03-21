package com.sgeapp.web.rest;

import com.sgeapp.repository.SocialOrganizationRepository;
import com.sgeapp.service.SocialOrganizationService;
import com.sgeapp.service.dto.SocialOrganizationDTO;
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
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.sgeapp.domain.SocialOrganization}.
 */
@RestController
@RequestMapping("/api")
public class SocialOrganizationResource {

    private final Logger log = LoggerFactory.getLogger(SocialOrganizationResource.class);

    private static final String ENTITY_NAME = "socialOrganization";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SocialOrganizationService socialOrganizationService;

    private final SocialOrganizationRepository socialOrganizationRepository;

    public SocialOrganizationResource(
        SocialOrganizationService socialOrganizationService,
        SocialOrganizationRepository socialOrganizationRepository
    ) {
        this.socialOrganizationService = socialOrganizationService;
        this.socialOrganizationRepository = socialOrganizationRepository;
    }

    /**
     * {@code POST  /social-organizations} : Create a new socialOrganization.
     *
     * @param socialOrganizationDTO the socialOrganizationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new socialOrganizationDTO, or with status {@code 400 (Bad Request)} if the socialOrganization has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/social-organizations")
    public ResponseEntity<SocialOrganizationDTO> createSocialOrganization(@RequestBody SocialOrganizationDTO socialOrganizationDTO)
        throws URISyntaxException {
        log.debug("REST request to save SocialOrganization : {}", socialOrganizationDTO);
        if (socialOrganizationDTO.getId() != null) {
            throw new BadRequestAlertException("A new socialOrganization cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SocialOrganizationDTO result = socialOrganizationService.save(socialOrganizationDTO);
        return ResponseEntity
            .created(new URI("/api/social-organizations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /social-organizations/:id} : Updates an existing socialOrganization.
     *
     * @param id the id of the socialOrganizationDTO to save.
     * @param socialOrganizationDTO the socialOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the socialOrganizationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the socialOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/social-organizations/{id}")
    public ResponseEntity<SocialOrganizationDTO> updateSocialOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocialOrganizationDTO socialOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SocialOrganization : {}, {}", id, socialOrganizationDTO);
        if (socialOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SocialOrganizationDTO result = socialOrganizationService.save(socialOrganizationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, socialOrganizationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /social-organizations/:id} : Partial updates given fields of an existing socialOrganization, field will ignore if it is null
     *
     * @param id the id of the socialOrganizationDTO to save.
     * @param socialOrganizationDTO the socialOrganizationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated socialOrganizationDTO,
     * or with status {@code 400 (Bad Request)} if the socialOrganizationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the socialOrganizationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the socialOrganizationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/social-organizations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SocialOrganizationDTO> partialUpdateSocialOrganization(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SocialOrganizationDTO socialOrganizationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SocialOrganization partially : {}, {}", id, socialOrganizationDTO);
        if (socialOrganizationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, socialOrganizationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!socialOrganizationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SocialOrganizationDTO> result = socialOrganizationService.partialUpdate(socialOrganizationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, socialOrganizationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /social-organizations} : get all the socialOrganizations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of socialOrganizations in body.
     */
    @GetMapping("/social-organizations")
    public List<SocialOrganizationDTO> getAllSocialOrganizations() {
        log.debug("REST request to get all SocialOrganizations");
        return socialOrganizationService.findAll();
    }

    /**
     * {@code GET  /social-organizations/:id} : get the "id" socialOrganization.
     *
     * @param id the id of the socialOrganizationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the socialOrganizationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/social-organizations/{id}")
    public ResponseEntity<SocialOrganizationDTO> getSocialOrganization(@PathVariable Long id) {
        log.debug("REST request to get SocialOrganization : {}", id);
        Optional<SocialOrganizationDTO> socialOrganizationDTO = socialOrganizationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(socialOrganizationDTO);
    }

    /**
     * {@code DELETE  /social-organizations/:id} : delete the "id" socialOrganization.
     *
     * @param id the id of the socialOrganizationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/social-organizations/{id}")
    public ResponseEntity<Void> deleteSocialOrganization(@PathVariable Long id) {
        log.debug("REST request to delete SocialOrganization : {}", id);
        socialOrganizationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
