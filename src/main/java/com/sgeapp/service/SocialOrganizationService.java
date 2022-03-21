package com.sgeapp.service;

import com.sgeapp.service.dto.SocialOrganizationDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sgeapp.domain.SocialOrganization}.
 */
public interface SocialOrganizationService {
    /**
     * Save a socialOrganization.
     *
     * @param socialOrganizationDTO the entity to save.
     * @return the persisted entity.
     */
    SocialOrganizationDTO save(SocialOrganizationDTO socialOrganizationDTO);

    /**
     * Partially updates a socialOrganization.
     *
     * @param socialOrganizationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SocialOrganizationDTO> partialUpdate(SocialOrganizationDTO socialOrganizationDTO);

    /**
     * Get all the socialOrganizations.
     *
     * @return the list of entities.
     */
    List<SocialOrganizationDTO> findAll();

    /**
     * Get the "id" socialOrganization.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SocialOrganizationDTO> findOne(Long id);

    /**
     * Delete the "id" socialOrganization.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
