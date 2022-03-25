package com.sgeapp.repository;

import com.sgeapp.domain.Request;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Request entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    @Query("SELECT r FROM Request r WHERE r.compaign.id = :campaignId and r.owner.id = :ownerId")
    Optional<Request> findByCampaignAndOwner(@Param("campaignId") Long campaignId, @Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Request r WHERE r.owner.id = :ownerId")
    List<Request> findAllByOwnerId(@Param("ownerId") Long ownerId);
}
