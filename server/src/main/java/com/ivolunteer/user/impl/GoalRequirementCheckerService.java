package com.ivolunteer.user.impl;

import com.ivolunteer.domain.Goal;
import com.ivolunteer.domain.GoalRequirements;
import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserCompetence;
import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.domain.enumeration.RuleOperator;
import com.ivolunteer.repository.ActivityProgressRepository;
import com.ivolunteer.repository.UserBadgeRepository;
import com.ivolunteer.repository.UserCompetenceRepository;
import com.ivolunteer.repository.UserGoalsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GoalRequirementCheckerService {

  private final Logger log = LoggerFactory.getLogger(GoalRequirementCheckerService.class);

  private final UserBadgeRepository userBadgeRepository;
  private final UserCompetenceRepository userCompetenceRepository;
  private final UserGoalsRepository userGoalsRepository;
  private final ActivityProgressRepository activityProgressRepository;

  public GoalRequirementCheckerService(UserBadgeRepository userBadgeRepository,
                                       UserCompetenceRepository userCompetenceRepository,
                                       UserGoalsRepository userGoalsRepository,
                                       ActivityProgressRepository activityProgressRepository) {
    this.userBadgeRepository = userBadgeRepository;
    this.userCompetenceRepository = userCompetenceRepository;
    this.userGoalsRepository = userGoalsRepository;
    this.activityProgressRepository = activityProgressRepository;
  }

  public boolean canGoalBeFinished(final Goal goal, final User user) {
    List<GoalRequirements> requirements = goal.getRequirements();
    if (requirements != null && requirements.size() > 0) {
      Long unfinished = requirements.stream().filter(
        r -> !this.checkRequirementCompleted(r, user)).count();
      log.info("User {} has currently {} requirements unfinished", user, unfinished);
      if (unfinished > 0) {
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }


  }


  private boolean checkRequirementCompleted(GoalRequirements requirement, User user) {
    final GlobalType type = requirement.getType();
    if (type == GlobalType.GOAL) {
      return this.userHasGoalCompleted(requirement, user);
    }
    if (type == GlobalType.ACTIVITY) {
      return this.userHasActivityCompleted(requirement, user);
    }
    if (type == GlobalType.COMPETENCE) {
      return this.userHasCompetenceCompleted(requirement, user);
    }
    if (type == GlobalType.BADGE) {
      return this.userHasBadgeCompleted(requirement, user);
    } else {
      return false;
    }
  }


  private boolean userHasBadgeCompleted(GoalRequirements requirement, User user) {
    return this.userBadgeRepository.existsByBadgeIdAndUserId(requirement.getData().getId(), user.getId());
  }

  private boolean userHasGoalCompleted(GoalRequirements requirement, User user) {
    return this.userGoalsRepository.existsByGoalIdAndUserId(requirement.getData().getId(), user.getId());
  }

  private boolean userHasActivityCompleted(GoalRequirements requirement, User user) {
    Double activityProgressSum = this.activityProgressRepository.selectProgressSumOfUserForActivity(
      requirement.getData().getId(),
      user.getId(),
      requirement.getActStartDate(),
      requirement.getActEndDate());

    if(activityProgressSum != null){
      return activityProgressSum >= requirement.getValue();
    }
    return false;
  }


  private boolean userHasCompetenceCompleted(GoalRequirements requirement, User user) {
    Optional<UserCompetence> optional = userCompetenceRepository.findByCompIdAndUserId(requirement.getData().getId(),
      user.getId());
    if (optional.isPresent()) {
      UserCompetence userCompetence = optional.get();
      if (requirement != null && userCompetence != null) {
        if (requirement.getOperator() == RuleOperator.BIGGER_THAN) {
          return userCompetence.getUserLevel() >= requirement.getValue();
        }
        if (requirement.getOperator() == RuleOperator.BIGGER) {
          return userCompetence.getUserLevel() > requirement.getValue();
        }
      }
    }
    return false;
  }


}
