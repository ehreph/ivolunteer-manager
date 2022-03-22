package com.ivolunteer.user;

import com.ivolunteer.domain.*;
import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.repository.*;
import com.ivolunteer.service.GoalAwardService;
import com.ivolunteer.service.GoalRequirementsService;
import com.ivolunteer.service.GoalService;
import com.ivolunteer.service.UserService;
import com.ivolunteer.service.dto.GoalAwardDTO;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.service.dto.GoalRequirementsDTO;
import com.ivolunteer.service.dto.VisibleGoalDTO;
import com.ivolunteer.service.mapper.GoalMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Goal}.
 */
@Service
@Transactional
public class VisibleGoalService {

  private final Logger log = LoggerFactory.getLogger(VisibleGoalService.class);

  private final GoalRepository goalRepository;
  private final UserService userService;
  private final UserGoalsRepository userGoalsRepository;
  private final GoalMapper goalMapper;


  public VisibleGoalService(GoalRepository goalRepository, UserService userService,
                            UserGoalsRepository userGoalsRepository,
                            GoalMapper goalMapper) {
    this.goalRepository = goalRepository;
    this.userService = userService;
    this.userGoalsRepository = userGoalsRepository;
    this.goalMapper = goalMapper;

  }

  @Transactional(readOnly = true)
  public Page<VisibleGoalDTO> findAllGoalsAvailableGoalsForCurrentUser(Pageable pageable) {
    log.debug("Request to get all Goals not subscribed of user ");
    User user = userService.getUserWithAuthorities().get();
    List<Long> userGoalsIds = userGoalsRepository.findAllGoalIdsByUserId(user.getId());

    Page<Goal> goals;
    if (userGoalsIds.isEmpty()) {
      goals = goalRepository.findAllVisibleGoals(pageable);
    } else {
      goals = goalRepository.findAllGoalsNotSubscribedByUser(userGoalsIds, pageable);
    }


    return goals
      .map(goal -> {
        if (goal.getRequirements() != null) {
          //pre map activity attribute to requirement
          goal.getRequirements().forEach(requirements -> {
            if (requirements.getType().equals(GlobalType.ACTIVITY)) {
              requirements.setAttributes(((Activity) requirements.getData()).getAttributes());
            }
          });
        }
        VisibleGoalDTO goalDTO = goalMapper.toVisibleGoalDto(goal);
        return goalDTO;
      });


  }

}
