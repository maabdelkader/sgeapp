package com.sgeapp.service.impl;

import com.sgeapp.domain.SocialOrganization;
import com.sgeapp.repository.SocialOrganizationRepository;
import com.sgeapp.service.SocialOrganizationService;
import com.sgeapp.service.dto.SocialOrganizationDTO;
import com.sgeapp.service.mapper.SocialOrganizationMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SocialOrganization}.
 */
@Service
@Transactional
public class SocialOrganizationServiceImpl implements SocialOrganizationService {

    private final Logger log = LoggerFactory.getLogger(SocialOrganizationServiceImpl.class);

    private final SocialOrganizationRepository socialOrganizationRepository;

    private final SocialOrganizationMapper socialOrganizationMapper;

    public SocialOrganizationServiceImpl(
        SocialOrganizationRepository socialOrganizationRepository,
        SocialOrganizationMapper socialOrganizationMapper
    ) {
        this.socialOrganizationRepository = socialOrganizationRepository;
        this.socialOrganizationMapper = socialOrganizationMapper;
    }

    @Override
    public SocialOrganizationDTO save(SocialOrganizationDTO socialOrganizationDTO) {
        log.debug("Request to save SocialOrganization : {}", socialOrganizationDTO);
        SocialOrganization socialOrganization = socialOrganizationMapper.toEntity(socialOrganizationDTO);
        socialOrganization = socialOrganizationRepository.save(socialOrganization);
        return socialOrganizationMapper.toDto(socialOrganization);
    }

    @Override
    public Optional<SocialOrganizationDTO> partialUpdate(SocialOrganizationDTO socialOrganizationDTO) {
        log.debug("Request to partially update SocialOrganization : {}", socialOrganizationDTO);

        return socialOrganizationRepository
            .findById(socialOrganizationDTO.getId())
            .map(existingSocialOrganization -> {
                socialOrganizationMapper.partialUpdate(existingSocialOrganization, socialOrganizationDTO);

                return existingSocialOrganization;
            })
            .map(socialOrganizationRepository::save)
            .map(socialOrganizationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocialOrganizationDTO> findAll() {
        log.debug("Request to get all SocialOrganizations");
        return socialOrganizationRepository
            .findAll()
            .stream()
            .map(socialOrganizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SocialOrganizationDTO> findOne(Long id) {
        log.debug("Request to get SocialOrganization : {}", id);
        return socialOrganizationRepository.findById(id).map(socialOrganizationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialOrganization : {}", id);
        socialOrganizationRepository.deleteById(id);
    }
}
