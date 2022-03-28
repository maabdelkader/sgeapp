package com.sgeapp.service;

import com.sgeapp.service.dto.ApplicationUserDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sgeapp.domain.ApplicationUser}.
 */
public interface ApplicationUserService {
    /**
     * Save a applicationUser.
     *
     * @param applicationUserDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationUserDTO save(ApplicationUserDTO applicationUserDTO);

    /**
     * Partially updates a applicationUser.
     *
     * @param applicationUserDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ApplicationUserDTO> partialUpdate(ApplicationUserDTO applicationUserDTO);

    /**
     * Get all the applicationUsers.
     *
     * @return the list of entities.
     */
    List<ApplicationUserDTO> findAll();

    /**
     * Get the "id" applicationUser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationUserDTO> findOne(Long id);

    /**
     * Delete the "id" applicationUser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Optional<ApplicationUserDTO> findByUserLogin(String userLogin);

    Optional<ApplicationUserDTO> getCurrentApplicationUser();
}
