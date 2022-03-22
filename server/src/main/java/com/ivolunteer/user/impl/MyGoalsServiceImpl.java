package com.ivolunteer.user.impl;

import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserGoals;
import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.repository.*;
import com.ivolunteer.service.UserService;
import com.ivolunteer.service.dto.GoalAwardDTO;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.service.dto.GoalRequirementsDTO;
import com.ivolunteer.service.mapper.UserBadgeMapper;
import com.ivolunteer.service.mapper.UserCompetenceMapper;
import com.ivolunteer.service.mapper.UserGoalsMapper;
import com.ivolunteer.user.GoalEntityService;
import com.ivolunteer.user.MyGoalsService;
import com.ivolunteer.user.dto.ActivityProgressDTO;
import com.ivolunteer.user.dto.MyGoalDTO;

import com.ivolunteer.user.dto.UserActivityDTO;
import com.ivolunteer.user.mapper.ActivityProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserGoals}.
 */
@Service
@Transactional
public class MyGoalsServiceImpl implements MyGoalsService {

  private final Logger log = LoggerFactory.getLogger(MyGoalsServiceImpl.class);

  private final UserGoalsRepository userGoalsRepository;
  private final UserCompetenceRepository userCompetenceRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final ActivityProgressRepository activityProgressRepository;
  private final UserService userService;
  private final GoalRepository goalRepository;
  private final UserCompetenceMapper userCompetenceMapper;
  private final UserBadgeMapper userBadgeMapper;
  private final UserGoalsMapper userGoalsMapper;
  private final GoalEntityService goalEntityService;
  private final ActivityProgressMapper activityProgressMapper;

  public MyGoalsServiceImpl(UserGoalsRepository userGoalsRepository,
                            UserCompetenceRepository userCompetenceRepository,
                            UserBadgeRepository userBadgeRepository,
                            ActivityProgressRepository activityProgressRepository,
                            UserService userService, GoalRepository goalRepository,
                            UserCompetenceMapper userCompetenceMapper,
                            UserBadgeMapper userBadgeMapper,
                            UserGoalsMapper userGoalsMapper, GoalEntityService goalEntityService,
                            ActivityProgressMapper activityProgressMapper) {
    this.userGoalsRepository = userGoalsRepository;
    this.userCompetenceRepository = userCompetenceRepository;
    this.userBadgeRepository = userBadgeRepository;
    this.activityProgressRepository = activityProgressRepository;
    this.userService = userService;
    this.goalRepository = goalRepository;
    this.userCompetenceMapper = userCompetenceMapper;
    this.userBadgeMapper = userBadgeMapper;
    this.userGoalsMapper = userGoalsMapper;
    this.goalEntityService = goalEntityService;
    this.activityProgressMapper = activityProgressMapper;
  }

  @Transactional(readOnly = true)
  public Page<MyGoalDTO> findAllForCurrentUser(Pageable pageable) {
    log.debug("Request to get goals with progress of current User");
    User user = userService.getUserWithAuthorities().get();
    return userGoalsRepository.findAllByUserIdAndFinishedFalse(user.getId(), pageable)
      .map(userGoals -> {
        MyGoalDTO myGoal = userGoalsMapper.toMyGoalDTO(userGoals);
        setMyGoalCollections(myGoal);


//        if (myGoal.getGoal().getAwardedCompetences() != null) {
//          for (GoalAwardDTO awardedCompetence : myGoal.getGoal().getAwardedCompetences()) {
//            goalEntityService.setAwardEntityName(awardedCompetence);
//          }
//        }
//        if (myGoal.getGoal().getRequirements() != null) {
//          for (GoalRequirementsDTO requirementsDTO : myGoal.getGoal().getRequirements()) {
//            goalEntityService.setRequirementEntityName(requirementsDTO);
//          }
//        }


        return myGoal;
      });
  }

  private void setMyGoalCollections(MyGoalDTO myGoal) {
    final Long userId = myGoal.getUserId();
    final GoalDTO goalDTO = myGoal.getGoal();
    List<Long> goalCompIds = getRequirementIdsOfType(goalDTO, GlobalType.COMPETENCE);
    if (!goalCompIds.isEmpty()) {
      myGoal.setUserCompetences(userCompetenceRepository
        .findAllByUserIdAndCompetenceIdIn(userId, goalCompIds));
    }
    List<Long> goalBadgeIds = getRequirementIdsOfType(goalDTO, GlobalType.BADGE);
    if (!goalBadgeIds.isEmpty()) {
      myGoal.setUserBadges(userBadgeRepository
        .findAllByUserIdAndBadgeIdIn(userId, goalBadgeIds));
    }

    List<Long> goalReqGoalIds = getRequirementIdsOfType(goalDTO, GlobalType.GOAL);
    if (!goalReqGoalIds.isEmpty()) {
      myGoal.setUserGoals(userGoalsRepository
        .findAllByUserIdAndGoalIdIn(userId, goalReqGoalIds));
    }

    List<GoalRequirementsDTO> requirementActivities = getRequirementsOfType(myGoal.getGoal(), GlobalType.ACTIVITY);
    myGoal.setUserActivities(requirementActivities.stream().map(activity -> {
      goalEntityService.setRequirementEntityName(activity);
      UserActivityDTO a = new UserActivityDTO();
      a.setActivityId(activity.getGeneralId());
      a.setUserId(userId);
      a.setName(activity.getEntityName());
      a.setAttributeName(activity.getAttributesName());
      a.setAttributeUnitName(activity.getAttributesUnitName());
      a.setProgressAmount(
        activityProgressRepository.selectProgressSumOfUserForActivity(activity.getGeneralId(), userId,
          activity.getActStartDate(), activity.getActEndDate()));
      return a;
    }).collect(Collectors.toList()));
  }

  private List<Long> getRequirementIdsOfType(GoalDTO goalDTO, GlobalType type) {
    return goalDTO.getRequirements().stream().filter(
        req -> req.getType().equals(type)).map(req -> req.getGeneralId())
      .collect(Collectors.toList());
  }

  private List<GoalRequirementsDTO> getRequirementsOfType(GoalDTO goalDTO, GlobalType type) {
    return goalDTO.getRequirements().stream().filter(
        req -> req.getType().equals(type))
      .collect(Collectors.toList());
  }


}
