package com.ivolunteer.user.impl;

import com.ivolunteer.domain.Activity;
import com.ivolunteer.domain.ActivityProgress;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.domain.User;
import com.ivolunteer.repository.ActivityProgressRepository;
import com.ivolunteer.repository.ActivityRepository;
import com.ivolunteer.repository.UserRepository;
import com.ivolunteer.service.UserService;
import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.service.mapper.ActivityMapper;
import com.ivolunteer.user.ActivityProgressService;
import com.ivolunteer.user.dto.ActivityProgressDTO;
import com.ivolunteer.user.mapper.ActivityProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;


/**
 * Service Implementation for managing {@link Competence}.
 */
@Service
@Transactional
public class ActivityProgressServiceImpl implements ActivityProgressService {

  private final Logger log = LoggerFactory.getLogger(ActivityProgressServiceImpl.class);

  private final ActivityProgressRepository activityProgressRepository;

  private final ActivityProgressMapper activityProgressMapper;
  private final ActivityRepository activityRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  public ActivityProgressServiceImpl(ActivityProgressRepository activityProgressRepository,
                                     ActivityProgressMapper activityProgressMapper,
                                     ActivityRepository activityRepository,
                                     UserRepository userRepository, UserService userService) {
    this.activityProgressRepository = activityProgressRepository;
    this.activityProgressMapper = activityProgressMapper;
    this.activityRepository = activityRepository;
    this.userRepository = userRepository;
    this.userService = userService;
  }

  @Override
  public ActivityProgressDTO save(ActivityProgressDTO activityProgressDTO) {
    log.debug("Request to save Competence : {}", activityProgressDTO);
    User user = userService.getUserWithAuthorities().get();
    ActivityProgress activityProgress = activityProgressMapper.toEntity(activityProgressDTO);
    Activity activity = activityRepository.findById(activityProgressDTO.getActivityId()).orElseThrow(
      () -> new EntityNotFoundException());
    activityProgress.setUser(user);
    activityProgress.setActivity(activity);
    activityProgress.setAttributes(activity.getAttributes());
    activityProgress = activityProgressRepository.save(activityProgress);
    return activityProgressMapper.toDto(activityProgress);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<ActivityProgressDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Competences");
    return activityProgressRepository.findAll(pageable)
      .map(activityProgressMapper::toDto);
  }


}
