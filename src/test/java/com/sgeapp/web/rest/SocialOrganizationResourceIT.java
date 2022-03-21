package com.sgeapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.sgeapp.IntegrationTest;
import com.sgeapp.domain.SocialOrganization;
import com.sgeapp.repository.SocialOrganizationRepository;
import com.sgeapp.service.dto.SocialOrganizationDTO;
import com.sgeapp.service.mapper.SocialOrganizationMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SocialOrganizationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SocialOrganizationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ADMIN_QUOTA = 1;
    private static final Integer UPDATED_ADMIN_QUOTA = 2;

    private static final Integer DEFAULT_COMMISSION_QUOTA = 1;
    private static final Integer UPDATED_COMMISSION_QUOTA = 2;

    private static final Integer DEFAULT_PROXIMITY_QUOTA = 1;
    private static final Integer UPDATED_PROXIMITY_QUOTA = 2;

    private static final Integer DEFAULT_NUMBER_OF_ADMINS = 1;
    private static final Integer UPDATED_NUMBER_OF_ADMINS = 2;

    private static final String ENTITY_API_URL = "/api/social-organizations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SocialOrganizationRepository socialOrganizationRepository;

    @Autowired
    private SocialOrganizationMapper socialOrganizationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSocialOrganizationMockMvc;

    private SocialOrganization socialOrganization;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialOrganization createEntity(EntityManager em) {
        SocialOrganization socialOrganization = new SocialOrganization()
            .name(DEFAULT_NAME)
            .adminQuota(DEFAULT_ADMIN_QUOTA)
            .commissionQuota(DEFAULT_COMMISSION_QUOTA)
            .proximityQuota(DEFAULT_PROXIMITY_QUOTA)
            .numberOfAdmins(DEFAULT_NUMBER_OF_ADMINS);
        return socialOrganization;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocialOrganization createUpdatedEntity(EntityManager em) {
        SocialOrganization socialOrganization = new SocialOrganization()
            .name(UPDATED_NAME)
            .adminQuota(UPDATED_ADMIN_QUOTA)
            .commissionQuota(UPDATED_COMMISSION_QUOTA)
            .proximityQuota(UPDATED_PROXIMITY_QUOTA)
            .numberOfAdmins(UPDATED_NUMBER_OF_ADMINS);
        return socialOrganization;
    }

    @BeforeEach
    public void initTest() {
        socialOrganization = createEntity(em);
    }

    @Test
    @Transactional
    void createSocialOrganization() throws Exception {
        int databaseSizeBeforeCreate = socialOrganizationRepository.findAll().size();
        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);
        restSocialOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeCreate + 1);
        SocialOrganization testSocialOrganization = socialOrganizationList.get(socialOrganizationList.size() - 1);
        assertThat(testSocialOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialOrganization.getAdminQuota()).isEqualTo(DEFAULT_ADMIN_QUOTA);
        assertThat(testSocialOrganization.getCommissionQuota()).isEqualTo(DEFAULT_COMMISSION_QUOTA);
        assertThat(testSocialOrganization.getProximityQuota()).isEqualTo(DEFAULT_PROXIMITY_QUOTA);
        assertThat(testSocialOrganization.getNumberOfAdmins()).isEqualTo(DEFAULT_NUMBER_OF_ADMINS);
    }

    @Test
    @Transactional
    void createSocialOrganizationWithExistingId() throws Exception {
        // Create the SocialOrganization with an existing ID
        socialOrganization.setId(1L);
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        int databaseSizeBeforeCreate = socialOrganizationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialOrganizationMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSocialOrganizations() throws Exception {
        // Initialize the database
        socialOrganizationRepository.saveAndFlush(socialOrganization);

        // Get all the socialOrganizationList
        restSocialOrganizationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialOrganization.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].adminQuota").value(hasItem(DEFAULT_ADMIN_QUOTA)))
            .andExpect(jsonPath("$.[*].commissionQuota").value(hasItem(DEFAULT_COMMISSION_QUOTA)))
            .andExpect(jsonPath("$.[*].proximityQuota").value(hasItem(DEFAULT_PROXIMITY_QUOTA)))
            .andExpect(jsonPath("$.[*].numberOfAdmins").value(hasItem(DEFAULT_NUMBER_OF_ADMINS)));
    }

    @Test
    @Transactional
    void getSocialOrganization() throws Exception {
        // Initialize the database
        socialOrganizationRepository.saveAndFlush(socialOrganization);

        // Get the socialOrganization
        restSocialOrganizationMockMvc
            .perform(get(ENTITY_API_URL_ID, socialOrganization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(socialOrganization.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.adminQuota").value(DEFAULT_ADMIN_QUOTA))
            .andExpect(jsonPath("$.commissionQuota").value(DEFAULT_COMMISSION_QUOTA))
            .andExpect(jsonPath("$.proximityQuota").value(DEFAULT_PROXIMITY_QUOTA))
            .andExpect(jsonPath("$.numberOfAdmins").value(DEFAULT_NUMBER_OF_ADMINS));
    }

    @Test
    @Transactional
    void getNonExistingSocialOrganization() throws Exception {
        // Get the socialOrganization
        restSocialOrganizationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSocialOrganization() throws Exception {
        // Initialize the database
        socialOrganizationRepository.saveAndFlush(socialOrganization);

        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();

        // Update the socialOrganization
        SocialOrganization updatedSocialOrganization = socialOrganizationRepository.findById(socialOrganization.getId()).get();
        // Disconnect from session so that the updates on updatedSocialOrganization are not directly saved in db
        em.detach(updatedSocialOrganization);
        updatedSocialOrganization
            .name(UPDATED_NAME)
            .adminQuota(UPDATED_ADMIN_QUOTA)
            .commissionQuota(UPDATED_COMMISSION_QUOTA)
            .proximityQuota(UPDATED_PROXIMITY_QUOTA)
            .numberOfAdmins(UPDATED_NUMBER_OF_ADMINS);
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(updatedSocialOrganization);

        restSocialOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialOrganizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isOk());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
        SocialOrganization testSocialOrganization = socialOrganizationList.get(socialOrganizationList.size() - 1);
        assertThat(testSocialOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialOrganization.getAdminQuota()).isEqualTo(UPDATED_ADMIN_QUOTA);
        assertThat(testSocialOrganization.getCommissionQuota()).isEqualTo(UPDATED_COMMISSION_QUOTA);
        assertThat(testSocialOrganization.getProximityQuota()).isEqualTo(UPDATED_PROXIMITY_QUOTA);
        assertThat(testSocialOrganization.getNumberOfAdmins()).isEqualTo(UPDATED_NUMBER_OF_ADMINS);
    }

    @Test
    @Transactional
    void putNonExistingSocialOrganization() throws Exception {
        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();
        socialOrganization.setId(count.incrementAndGet());

        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, socialOrganizationDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSocialOrganization() throws Exception {
        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();
        socialOrganization.setId(count.incrementAndGet());

        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSocialOrganization() throws Exception {
        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();
        socialOrganization.setId(count.incrementAndGet());

        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialOrganizationMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSocialOrganizationWithPatch() throws Exception {
        // Initialize the database
        socialOrganizationRepository.saveAndFlush(socialOrganization);

        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();

        // Update the socialOrganization using partial update
        SocialOrganization partialUpdatedSocialOrganization = new SocialOrganization();
        partialUpdatedSocialOrganization.setId(socialOrganization.getId());

        partialUpdatedSocialOrganization.proximityQuota(UPDATED_PROXIMITY_QUOTA).numberOfAdmins(UPDATED_NUMBER_OF_ADMINS);

        restSocialOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialOrganization))
            )
            .andExpect(status().isOk());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
        SocialOrganization testSocialOrganization = socialOrganizationList.get(socialOrganizationList.size() - 1);
        assertThat(testSocialOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSocialOrganization.getAdminQuota()).isEqualTo(DEFAULT_ADMIN_QUOTA);
        assertThat(testSocialOrganization.getCommissionQuota()).isEqualTo(DEFAULT_COMMISSION_QUOTA);
        assertThat(testSocialOrganization.getProximityQuota()).isEqualTo(UPDATED_PROXIMITY_QUOTA);
        assertThat(testSocialOrganization.getNumberOfAdmins()).isEqualTo(UPDATED_NUMBER_OF_ADMINS);
    }

    @Test
    @Transactional
    void fullUpdateSocialOrganizationWithPatch() throws Exception {
        // Initialize the database
        socialOrganizationRepository.saveAndFlush(socialOrganization);

        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();

        // Update the socialOrganization using partial update
        SocialOrganization partialUpdatedSocialOrganization = new SocialOrganization();
        partialUpdatedSocialOrganization.setId(socialOrganization.getId());

        partialUpdatedSocialOrganization
            .name(UPDATED_NAME)
            .adminQuota(UPDATED_ADMIN_QUOTA)
            .commissionQuota(UPDATED_COMMISSION_QUOTA)
            .proximityQuota(UPDATED_PROXIMITY_QUOTA)
            .numberOfAdmins(UPDATED_NUMBER_OF_ADMINS);

        restSocialOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSocialOrganization.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSocialOrganization))
            )
            .andExpect(status().isOk());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
        SocialOrganization testSocialOrganization = socialOrganizationList.get(socialOrganizationList.size() - 1);
        assertThat(testSocialOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSocialOrganization.getAdminQuota()).isEqualTo(UPDATED_ADMIN_QUOTA);
        assertThat(testSocialOrganization.getCommissionQuota()).isEqualTo(UPDATED_COMMISSION_QUOTA);
        assertThat(testSocialOrganization.getProximityQuota()).isEqualTo(UPDATED_PROXIMITY_QUOTA);
        assertThat(testSocialOrganization.getNumberOfAdmins()).isEqualTo(UPDATED_NUMBER_OF_ADMINS);
    }

    @Test
    @Transactional
    void patchNonExistingSocialOrganization() throws Exception {
        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();
        socialOrganization.setId(count.incrementAndGet());

        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocialOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, socialOrganizationDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSocialOrganization() throws Exception {
        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();
        socialOrganization.setId(count.incrementAndGet());

        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSocialOrganization() throws Exception {
        int databaseSizeBeforeUpdate = socialOrganizationRepository.findAll().size();
        socialOrganization.setId(count.incrementAndGet());

        // Create the SocialOrganization
        SocialOrganizationDTO socialOrganizationDTO = socialOrganizationMapper.toDto(socialOrganization);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSocialOrganizationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(socialOrganizationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SocialOrganization in the database
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSocialOrganization() throws Exception {
        // Initialize the database
        socialOrganizationRepository.saveAndFlush(socialOrganization);

        int databaseSizeBeforeDelete = socialOrganizationRepository.findAll().size();

        // Delete the socialOrganization
        restSocialOrganizationMockMvc
            .perform(delete(ENTITY_API_URL_ID, socialOrganization.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocialOrganization> socialOrganizationList = socialOrganizationRepository.findAll();
        assertThat(socialOrganizationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
