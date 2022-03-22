package com.ivolunteer.service.impl;

import com.ivolunteer.domain.User;
import com.ivolunteer.service.GoalService;
import com.ivolunteer.domain.Goal;
import com.ivolunteer.repository.GoalRepository;
import com.ivolunteer.service.UserService;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.service.mapper.GoalMapper;
import com.ivolunteer.user.UserGoalsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Goal}.
 */
@Service
@Transactional
public class GoalServiceImpl implements GoalService {

  private final Logger log = LoggerFactory.getLogger(GoalServiceImpl.class);

  private final GoalRepository goalRepository;

  private final GoalMapper goalMapper;
  private final UserService userService;
  private final UserGoalsService userGoalsService;

  public GoalServiceImpl(GoalRepository goalRepository,
                         GoalMapper goalMapper,
                         UserService userService, UserGoalsService userGoalsService) {
    this.goalRepository = goalRepository;
    this.goalMapper = goalMapper;
    this.userService = userService;
    this.userGoalsService = userGoalsService;
  }

  @Override
  @Transactional
  public GoalDTO save(GoalDTO goalDTO) {
    log.debug("Request to save Goal : {}", goalDTO);

    Goal goal = goalMapper.toEntity(goalDTO);

    //TODO other method needs to be if personal goals are saved/updated
    // if personal goal is edited it becomes public
    goal.setCreatedByUser(null);
    goal.setIsPersonal(false);

    Goal finalGoal = goalRepository.save(goal);

    if (finalGoal.getAwardedCompetences() != null) {
      finalGoal.getAwardedCompetences().forEach(goalAward -> {
        goalAward.setGoal(finalGoal);
      });
    }
    if (finalGoal.getRequirements() != null) {
      finalGoal.getRequirements().forEach(goalRequirements -> {
        goalRequirements.setGoal(finalGoal);
      });
    }
    //TODO fix error where requirement time cannot be saved
    return goalMapper.toDto(finalGoal);
  }

  @Override
  @Transactional
  public GoalDTO savePersonalGoal(GoalDTO goalDTO) {
    log.debug("Request to save personal Goal : {}", goalDTO);
    User user = userService.getUserWithAuthorities().get();
    Goal goal = goalMapper.toEntity(goalDTO);
    goal.setCreatedByUser(user);
    goal.setIsPersonal(true);
    Goal finalGoal = goalRepository.save(goal);

    if (finalGoal.getRequirements() != null) {
      finalGoal.getRequirements().forEach(requirement -> {
        requirement.setGoal(finalGoal);
      });
    }

    userGoalsService.subscribeUser(user, finalGoal);
    return goalMapper.toDto(finalGoal);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<GoalDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Goals");
    return goalRepository.findAll(pageable)
      .map(goalMapper::toDto);
  }


  @Override
  @Transactional(readOnly = true)
  public Optional<GoalDTO> findOne(Long id) {
    log.debug("Request to get Goal : {}", id);
    return goalRepository.findById(id)
      .map(goalMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Goal : {}", id);
    goalRepository.deleteById(id);
  }

  @Override
  public List<GoalDTO> findNewGoalsForUser(Long userId) {
    List<Goal> list =  goalRepository.findAllNewGoalsForUser(userId);
    return goalMapper.toDto(list);
  }
}
