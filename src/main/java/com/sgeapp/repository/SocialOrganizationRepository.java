package com.sgeapp.repository;

import com.sgeapp.domain.SocialOrganization;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SocialOrganization entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialOrganizationRepository extends JpaRepository<SocialOrganization, Long> {}
