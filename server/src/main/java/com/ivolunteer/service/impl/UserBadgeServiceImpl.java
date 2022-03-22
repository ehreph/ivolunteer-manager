package com.ivolunteer.service.impl;

import com.ivolunteer.service.UserBadgeService;
import com.ivolunteer.domain.UserBadge;
import com.ivolunteer.repository.UserBadgeRepository;
import com.ivolunteer.service.dto.UserBadgeDTO;
import com.ivolunteer.service.dto.UserCompetenceDTO;
import com.ivolunteer.service.mapper.UserBadgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link UserBadge}.
 */
@Service
@Transactional
public class UserBadgeServiceImpl implements UserBadgeService {

    private final Logger log = LoggerFactory.getLogger(UserBadgeServiceImpl.class);

    private final UserBadgeRepository userBadgeRepository;

    private final UserBadgeMapper userBadgeMapper;

    public UserBadgeServiceImpl(UserBadgeRepository userBadgeRepository, UserBadgeMapper userBadgeMapper) {
        this.userBadgeRepository = userBadgeRepository;
        this.userBadgeMapper = userBadgeMapper;
    }

    @Override
    public UserBadgeDTO save(UserBadgeDTO userBadgeDTO) {
        log.debug("Request to save UserBadge : {}", userBadgeDTO);
        UserBadge userBadge = userBadgeMapper.toEntity(userBadgeDTO);
        userBadge = userBadgeRepository.save(userBadge);
        return userBadgeMapper.toDto(userBadge);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserBadgeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserBadges");
        return userBadgeRepository.findAll(pageable)
            .map(userBadgeMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserBadgeDTO> findOne(Long id) {
        log.debug("Request to get UserBadge : {}", id);
        return userBadgeRepository.findById(id)
            .map(userBadgeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserBadge : {}", id);
        userBadgeRepository.deleteById(id);
    }

    @Override
    public Page<UserBadgeDTO> findAllByUserId(Long userId, Pageable pageable) {

        log.debug("Request to get all User Badges by UserId");
        return userBadgeRepository.findAllByUserId(userId, pageable)
            .map(userBadgeMapper::toDto);

    }
}
