package com.sgeapp.service;

import com.sgeapp.service.dto.TimeSheetDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.sgeapp.domain.TimeSheet}.
 */
public interface TimeSheetService {
    /**
     * Save a timeSheet.
     *
     * @param timeSheetDTO the entity to save.
     * @return the persisted entity.
     */
    TimeSheetDTO save(TimeSheetDTO timeSheetDTO);

    /**
     * Partially updates a timeSheet.
     *
     * @param timeSheetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimeSheetDTO> partialUpdate(TimeSheetDTO timeSheetDTO);

    /**
     * Get all the timeSheets.
     *
     * @return the list of entities.
     */
    List<TimeSheetDTO> findAll();

    /**
     * Get the "id" timeSheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimeSheetDTO> findOne(Long id);

    /**
     * Delete the "id" timeSheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<TimeSheetDTO> findAllByRequestId(Long requestId);

    void saveAll(List<TimeSheetDTO> timeSheetDTOList);
}
