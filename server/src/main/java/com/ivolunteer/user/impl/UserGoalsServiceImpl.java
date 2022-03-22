package com.ivolunteer.user.impl;

import com.ivolunteer.domain.*;
import com.ivolunteer.repository.*;
import com.ivolunteer.security.SecurityUtils;
import com.ivolunteer.service.UserService;
import com.ivolunteer.user.dto.UserGoalsDTO;
import com.ivolunteer.service.mapper.UserGoalsMapper;
import com.ivolunteer.user.UserGoalsService;
import com.ivolunteer.user.exception.UserGoalsServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserGoals}.
 */
@Service
@Transactional
public class UserGoalsServiceImpl implements UserGoalsService {

  private final Logger log = LoggerFactory.getLogger(UserGoalsServiceImpl.class);

  private final UserGoalsRepository userGoalsRepository;
  private final UserService userService;
  private final GoalRepository goalRepository;
  private final UserGoalsMapper userGoalsMapper;
  private final RewardService rewardService;
  private final GoalRequirementCheckerService requirementCheckerService;

  public UserGoalsServiceImpl(UserGoalsRepository userGoalsRepository, UserService userService,
                              GoalRepository goalRepository,
                              UserGoalsMapper userGoalsMapper, RewardService rewardService,
                              GoalRequirementCheckerService requirementCheckerService) {
    this.userGoalsRepository = userGoalsRepository;
    this.userService = userService;
    this.goalRepository = goalRepository;
    this.userGoalsMapper = userGoalsMapper;
    this.rewardService = rewardService;
    this.requirementCheckerService = requirementCheckerService;
  }

  @Override
  public UserGoalsDTO save(UserGoalsDTO userGoalsDTO) {
    log.debug("Request to save UserGoals : {}", userGoalsDTO);

    UserGoals userGoals = userGoalsMapper.toEntity(userGoalsDTO);

    if (userGoals.getGoal() == null) {
      throw new UserGoalsServiceException("ERROR: Goal must not be null");
    }
    Boolean isSubscribed = userGoalsRepository.existsByGoalIdAndUserId(userGoals.getGoal().getId(),
      userGoals.getUser().getId());

    if (isSubscribed) {
      throw new IllegalArgumentException("User cannot be subscribed to a goal twice");
    }
    if (userGoals.getFinished() == null) {
      userGoals.setFinished(false);
    }

    userGoals = userGoalsRepository.save(userGoals);
    return userGoalsMapper.toDto(userGoals);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserGoalsDTO> findAll(Pageable pageable) {
    log.debug("Request to get all UserGoals");
    return userGoalsRepository.findAll(pageable)
      .map(userGoalsMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<UserGoalsDTO> findAllByUserId(Long userId, Pageable pageable) {
    log.debug("Request to get all UserGoals");
    return userGoalsRepository.findAllByUserId(userId, pageable)
      .map(userGoalsMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<UserGoalsDTO> findOne(Long id) {
    log.debug("Request to get UserGoals : {}", id);
    return userGoalsRepository.findById(id)
      .map(userGoalsMapper::toDto);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    log.debug("Request to delete UserGoals : {}", id);
    //TODO maybe check if allowed to remove usergoal
    User currentUser = userService.getUserWithAuthorities().orElseThrow(() -> new EntityNotFoundException());
    UserGoals userGoals = userGoalsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());


    if (userGoals.getUser() != null) {
      //TODO check why sometimes the security exeption is thrown on removing a usergoal
      checkSecurityRules(currentUser, "USERGOAL_ENTITY", userGoals.getUser().getId());

      User assignedUser = userGoals.getUser();
      if (userGoals.getFinished()) {
        throw new IllegalArgumentException("finished user Goals cannot be deleted");
      } else {
        assignedUser.getUserGoals().remove(userGoals);
        userGoalsRepository.delete(userGoals);
      }
    } else {
      log.error("Usergoal is not assigned to any user userGoalId:", userGoals.getId());
      throw new IllegalArgumentException("Usergoal is not assigned to any user");
    }


  }


  @Override
  @Transactional
  public UserGoalsDTO subscribeUser(User user, Goal goal) {
    log.info("Subscribe user {} to goal {}", user, goal);
    return performSubscribe(user, goal);
  }

  @Override
  @Transactional
  public UserGoalsDTO subscribeUser(Long goalId) {
    log.info("subscribe user to goal with ID {}", goalId);
    User user = userService.getUserWithAuthorities().get();
    Goal goal = goalRepository.findById(goalId).orElseThrow(() -> new EntityNotFoundException());

    return performSubscribe(user, goal);
  }


  @Override
  @Transactional
  public UserGoalsDTO finishUserGoal(Long userGoalId) {
    log.info("finish user goal with ID: ", userGoalId);
    return performFinish(userGoalId);
  }

  private UserGoalsDTO performFinish(Long userGoalId) {
    User user = userService.getUserWithAuthorities().get();
    UserGoals userGoals = userGoalsRepository.findByIdAndUserId(userGoalId, user.getId()).orElseThrow(
      () -> new EntityNotFoundException());
    if (!userGoals.getFinished()) {
      // TODO better would be also checking MyGoalConditions;
      Boolean canBeFinished = requirementCheckerService.canGoalBeFinished(userGoals.getGoal(), user);
      if (canBeFinished) {
        rewardService.applyGoalRewards(userGoals.getGoal(), user);
        userGoals.setFinishedDate(Instant.now());
        userGoals.setFinished(true);
      }else{
        throw new IllegalArgumentException("goal requirements not completed");
      }

    } else {
      throw new IllegalArgumentException("goal cannot be finished twice");
    }
    return userGoalsMapper.toDto(userGoals);
  }

  private UserGoalsDTO performSubscribe(User user, Goal goal) {
    Boolean isSubscribed = userGoalsRepository.existsByGoalIdAndUserId(goal.getId(), user.getId());
    if (isSubscribed) {
      throw new IllegalArgumentException("User cannot subscribe to a goal twice");
    }
    UserGoals userGoals = new UserGoals();
    userGoals.setUser(user);
    userGoals.setGoal(goal);
    userGoals.setFinished(false);

    userGoals = userGoalsRepository.save(userGoals);
//        user.getUserGoals().add(userGoals);
    return userGoalsMapper.toDto(userGoals);
  }


  private void checkSecurityRules(User currentUser, String entityType, Long entityUserId) {
    // TODO move method to specific Service
    log.info("Check if current User is allowed to delete: " + entityType);
    if (currentUser.getAuthorities().stream().map(authority -> authority.getName()).collect(
      Collectors.toSet()).contains("ROLE_ADMIN")) {
      return;
    }
    if (currentUser.getId().equals(entityUserId)) {
      return;
    }
    throw new SecurityException("User with id: " + currentUser.getId() + " is not allowed to perfom this action");
  }


}
