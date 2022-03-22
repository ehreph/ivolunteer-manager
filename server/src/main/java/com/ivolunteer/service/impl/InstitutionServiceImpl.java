package com.ivolunteer.service.impl;

import com.ivolunteer.service.InstitutionService;
import com.ivolunteer.domain.Institution;
import com.ivolunteer.repository.InstitutionRepository;
import com.ivolunteer.service.dto.InstitutionDTO;
import com.ivolunteer.service.mapper.InstitutionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Institution}.
 */
@Service
@Transactional
public class InstitutionServiceImpl implements InstitutionService {

    private final Logger log = LoggerFactory.getLogger(InstitutionServiceImpl.class);

    private final InstitutionRepository institutionRepository;

    private final InstitutionMapper institutionMapper;

    public InstitutionServiceImpl(InstitutionRepository institutionRepository, InstitutionMapper institutionMapper) {
        this.institutionRepository = institutionRepository;
        this.institutionMapper = institutionMapper;
    }

    @Override
    public InstitutionDTO save(InstitutionDTO institutionDTO) {
        log.debug("Request to save Institution : {}", institutionDTO);
        Institution institution = institutionMapper.toEntity(institutionDTO);
        institution = institutionRepository.save(institution);
        return institutionMapper.toDto(institution);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InstitutionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Institutions");
        return institutionRepository.findAll(pageable)
            .map(institutionMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<InstitutionDTO> findOne(Long id) {
        log.debug("Request to get Institution : {}", id);
        return institutionRepository.findById(id)
            .map(institutionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Institution : {}", id);
        institutionRepository.deleteById(id);
    }
}
