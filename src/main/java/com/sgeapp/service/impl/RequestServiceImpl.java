package com.sgeapp.service.impl;

import com.sgeapp.domain.Request;
import com.sgeapp.repository.RequestRepository;
import com.sgeapp.service.RequestService;
import com.sgeapp.service.TimeSheetService;
import com.sgeapp.service.dto.RequestDTO;
import com.sgeapp.service.dto.RequestTotalsDto;
import com.sgeapp.service.dto.TimeSheetDTO;
import com.sgeapp.service.mapper.RequestMapper;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * Service Implementation for managing {@link Request}.
 */
@Service
@Transactional
public class RequestServiceImpl implements RequestService {

    private final Logger log = LoggerFactory.getLogger(RequestServiceImpl.class);

    private final RequestRepository requestRepository;

    private final RequestMapper requestMapper;

    private final TimeSheetService timeSheetService;

    public RequestServiceImpl(RequestRepository requestRepository, RequestMapper requestMapper, TimeSheetService timeSheetService) {
        this.requestRepository = requestRepository;
        this.requestMapper = requestMapper;
        this.timeSheetService = timeSheetService;
    }

    @Override
    public RequestDTO save(RequestDTO requestDTO) {
        log.debug("Request to save Request : {}", requestDTO);
        Request request = requestMapper.toEntity(requestDTO);
        request = requestRepository.save(request);
        return requestMapper.toDto(request);
    }

    @Override
    public Optional<RequestDTO> partialUpdate(RequestDTO requestDTO) {
        log.debug("Request to partially update Request : {}", requestDTO);

        return requestRepository
            .findById(requestDTO.getId())
            .map(existingRequest -> {
                requestMapper.partialUpdate(existingRequest, requestDTO);

                return existingRequest;
            })
            .map(requestRepository::save)
            .map(requestMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RequestDTO> findAll() {
        log.debug("Request to get all Requests");
        return requestRepository.findAll().stream().map(requestMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RequestDTO> findOne(Long id) {
        log.debug("Request to get Request : {}", id);
        return requestRepository.findById(id).map(requestMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Request : {}", id);
        requestRepository.deleteById(id);
    }

    @Override
    public RequestTotalsDto calculateRequestTotals(Long requestId) {
        List<TimeSheetDTO> timeSheetDTOList = timeSheetService.findAllByRequestId(requestId);
        RequestTotalsDto result = new RequestTotalsDto();
        result.setRequestId(requestId);
        if (!CollectionUtils.isEmpty(timeSheetDTOList)) {
            int totalAdmin = 0;
            int totalProximity = 0;
            int totalCommission = 0;
            for (TimeSheetDTO timeSheetDTO : timeSheetDTOList) {
                if (timeSheetDTO.getNbHoursCommision() != null) {
                    totalCommission += timeSheetDTO.getNbHoursCommision();
                }
                if (timeSheetDTO.getNbHoursAdmin() != null) {
                    totalAdmin += timeSheetDTO.getNbHoursAdmin();
                }
                if (timeSheetDTO.getNbHoursProximity() != null) {
                    totalProximity += timeSheetDTO.getNbHoursProximity();
                }
            }
            result.setTotalAdmin(totalAdmin);
            result.setTotalCommission(totalCommission);
            result.setTotalProximity(totalProximity);
        }
        return result;
    }

    @Override
    public Optional<RequestDTO> findByCampaignAndOwner(Long campaignId, Long ownerId) {
        return requestRepository.findByCampaignAndOwner(campaignId, ownerId).map(requestMapper::toDto);
    }
}
