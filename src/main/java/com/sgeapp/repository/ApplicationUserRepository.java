package com.sgeapp.repository;

import com.sgeapp.domain.ApplicationUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ApplicationUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    @Query("SELECT au FROM ApplicationUser au WHERE au.internalUser.login = :login")
    Optional<ApplicationUser> findByUserLogin(@Param("login") String userLogin);
}
