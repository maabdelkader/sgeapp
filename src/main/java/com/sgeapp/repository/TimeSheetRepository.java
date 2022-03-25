package com.sgeapp.repository;

import com.sgeapp.domain.ApplicationUser;
import com.sgeapp.domain.TimeSheet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TimeSheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Long> {
    @Query("SELECT tm FROM TimeSheet tm WHERE tm.request.id = :requestId")
    List<TimeSheet> findAllByRequestId(@Param("requestId") Long requestId);
}
