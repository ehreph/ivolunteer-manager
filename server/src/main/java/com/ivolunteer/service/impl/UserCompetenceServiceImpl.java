package com.ivolunteer.service.impl;

import com.ivolunteer.service.UserCompetenceService;
import com.ivolunteer.domain.UserCompetence;
import com.ivolunteer.repository.UserCompetenceRepository;
import com.ivolunteer.service.dto.UserCompetenceDTO;
import com.ivolunteer.service.mapper.UserCompetenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserCompetence}.
 */
@Service
@Transactional
public class UserCompetenceServiceImpl implements UserCompetenceService {

    private final Logger log = LoggerFactory.getLogger(UserCompetenceServiceImpl.class);

    private final UserCompetenceRepository userCompetenceRepository;

    private final UserCompetenceMapper userCompetenceMapper;

    public UserCompetenceServiceImpl(UserCompetenceRepository userCompetenceRepository, UserCompetenceMapper userCompetenceMapper) {
        this.userCompetenceRepository = userCompetenceRepository;
        this.userCompetenceMapper = userCompetenceMapper;
    }

    @Override
    public UserCompetenceDTO save(UserCompetenceDTO userCompetenceDTO) {
        log.debug("Request to save UserCompetence : {}", userCompetenceDTO);
        UserCompetence userCompetence = userCompetenceMapper.toEntity(userCompetenceDTO);
        userCompetence = userCompetenceRepository.save(userCompetence);
        return userCompetenceMapper.toDto(userCompetence);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserCompetenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserCompetences");
        return userCompetenceRepository.findAll(pageable)
            .map(userCompetenceMapper::toDto);
    }

    @Override
    public Page<UserCompetenceDTO> findAllByUserId(Long userId,Pageable pageable) {
        log.debug("Request to get all UserCompetences by UserId");
        return userCompetenceRepository.findAllByUserId(userId,pageable)
            .map(userCompetenceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserCompetenceDTO> findOne(Long id) {
        log.debug("Request to get UserCompetence : {}", id);
        return userCompetenceRepository.findById(id)
            .map(userCompetenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserCompetence : {}", id);
        userCompetenceRepository.deleteById(id);
    }
}
