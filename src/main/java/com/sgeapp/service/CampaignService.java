package com.sgeapp.service;

import com.sgeapp.service.dto.CampaignDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sgeapp.domain.Campaign}.
 */
public interface CampaignService {
    /**
     * Save a campaign.
     *
     * @param campaignDTO the entity to save.
     * @return the persisted entity.
     */
    CampaignDTO save(CampaignDTO campaignDTO);

    /**
     * Partially updates a campaign.
     *
     * @param campaignDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CampaignDTO> partialUpdate(CampaignDTO campaignDTO);

    /**
     * Get all the campaigns.
     *
     * @return the list of entities.
     */
    List<CampaignDTO> findAll();

    /**
     * Get the "id" campaign.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CampaignDTO> findOne(Long id);

    /**
     * Delete the "id" campaign.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
