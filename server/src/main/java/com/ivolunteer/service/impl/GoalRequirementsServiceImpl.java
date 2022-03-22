package com.ivolunteer.service.impl;

import com.ivolunteer.service.GoalRequirementsService;
import com.ivolunteer.domain.GoalRequirements;
import com.ivolunteer.repository.GoalRequirementsRepository;
import com.ivolunteer.service.dto.GoalRequirementsDTO;
import com.ivolunteer.service.mapper.GoalRequirementsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GoalRequirements}.
 */
@Service
@Transactional
public class GoalRequirementsServiceImpl implements GoalRequirementsService {

    private final Logger log = LoggerFactory.getLogger(GoalRequirementsServiceImpl.class);

    private final GoalRequirementsRepository goalRequirementsRepository;

    private final GoalRequirementsMapper goalRequirementsMapper;

    public GoalRequirementsServiceImpl(GoalRequirementsRepository goalRequirementsRepository, GoalRequirementsMapper goalRequirementsMapper) {
        this.goalRequirementsRepository = goalRequirementsRepository;
        this.goalRequirementsMapper = goalRequirementsMapper;
    }

    @Override
    public GoalRequirementsDTO save(GoalRequirementsDTO goalRequirementsDTO) {
        log.debug("Request to save GoalRequirements : {}", goalRequirementsDTO);
        GoalRequirements goalRequirements = goalRequirementsMapper.toEntity(goalRequirementsDTO);
        goalRequirements = goalRequirementsRepository.save(goalRequirements);
        return goalRequirementsMapper.toDto(goalRequirements);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoalRequirementsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoalRequirements");
        return goalRequirementsRepository.findAll(pageable)
            .map(goalRequirementsMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GoalRequirementsDTO> findOne(Long id) {
        log.debug("Request to get GoalRequirements : {}", id);
        return goalRequirementsRepository.findById(id)
            .map(goalRequirementsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoalRequirements : {}", id);
        goalRequirementsRepository.deleteById(id);
    }
}
