package com.ivolunteer.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivolunteer.domain.Activity;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.repository.ActivityRepository;
import com.ivolunteer.repository.CompetenceRepository;
import com.ivolunteer.service.ActivityService;
import com.ivolunteer.service.CompetenceService;
import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.service.mapper.ActivityMapper;
import com.ivolunteer.service.mapper.CompetenceMapper;

/**
 * Service Implementation for managing {@link Competence}.
 */
@Service
@Transactional
public class ActivityServiceImpl implements ActivityService {

    private final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityRepository activityRepository, ActivityMapper activityMapper) {
        this.activityRepository = activityRepository;
        this.activityMapper = activityMapper;
    }

    @Override
    public ActivityDTO save(ActivityDTO activityDTO) {
        log.debug("Request to save Competence : {}", activityDTO);
        Activity activity = activityMapper.toEntity(activityDTO);
        activity = activityRepository.save(activity);
        return activityMapper.toDto(activity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competences");
        return activityRepository.findAll(pageable)
            .map(activityMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ActivityDTO> findOne(Long id) {
        log.debug("Request to get Competence : {}", id);
        return activityRepository.findById(id)
            .map(activityMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competence : {}", id);
        activityRepository.deleteById(id);
    }
}
