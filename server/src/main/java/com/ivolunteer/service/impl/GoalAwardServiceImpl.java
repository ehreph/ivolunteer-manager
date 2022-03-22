package com.ivolunteer.service.impl;

import com.ivolunteer.service.GoalAwardService;
import com.ivolunteer.domain.GoalAward;
import com.ivolunteer.repository.GoalAwardRepository;
import com.ivolunteer.service.dto.GoalAwardDTO;
import com.ivolunteer.service.mapper.GoalAwardMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GoalAward}.
 */
@Service
@Transactional
public class GoalAwardServiceImpl implements GoalAwardService {

    private final Logger log = LoggerFactory.getLogger(GoalAwardServiceImpl.class);

    private final GoalAwardRepository goalAwardRepository;

    private final GoalAwardMapper goalAwardMapper;

    public GoalAwardServiceImpl(GoalAwardRepository goalAwardRepository, GoalAwardMapper goalAwardMapper) {
        this.goalAwardRepository = goalAwardRepository;
        this.goalAwardMapper = goalAwardMapper;
    }

    @Override
    public GoalAwardDTO save(GoalAwardDTO goalAwardDTO) {
        log.debug("Request to save GoalAward : {}", goalAwardDTO);
        GoalAward goalAward = goalAwardMapper.toEntity(goalAwardDTO);
        goalAward = goalAwardRepository.save(goalAward);
        return goalAwardMapper.toDto(goalAward);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GoalAwardDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GoalAwards");
        return goalAwardRepository.findAll(pageable)
            .map(goalAwardMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<GoalAwardDTO> findOne(Long id) {
        log.debug("Request to get GoalAward : {}", id);
        return goalAwardRepository.findById(id)
            .map(goalAwardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete GoalAward : {}", id);
        goalAwardRepository.deleteById(id);
    }
}
