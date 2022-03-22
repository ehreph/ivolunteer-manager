package com.ivolunteer.user.impl;

import com.ivolunteer.domain.*;
import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RewardService {
  private final Logger log = LoggerFactory.getLogger(RewardService.class);

  private final UserCompetenceRepository userCompetenceRepository;
  private final CompetenceRepository competenceRepository;
  private final BadgeRepository badgeRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final CompetenceProgressRepository competenceProgressRepository;

  public RewardService(
    UserCompetenceRepository userCompetenceRepository,
    CompetenceRepository competenceRepository,
    BadgeRepository badgeRepository,
    UserBadgeRepository userBadgeRepository,
    CompetenceProgressRepository competenceProgressRepository) {
    this.userCompetenceRepository = userCompetenceRepository;
    this.competenceRepository = competenceRepository;
    this.badgeRepository = badgeRepository;
    this.userBadgeRepository = userBadgeRepository;
    this.competenceProgressRepository = competenceProgressRepository;
  }

  @Transactional
  public void applyGoalRewards(final Goal goal, User user) {
    log.info("Apply goal rewards {} for user {}", goal, user);
    List<GoalAward> awardList = goal.getAwardedCompetences();
    if (awardList != null && awardList.size() >= 0) {
      awardList.forEach(reward -> {
        if (reward.getAwardType().equals(GlobalType.COMPETENCE)) {
          applyCompetenceReward(user, reward);
        } else if (reward.getAwardType().equals(GlobalType.BADGE)) {
          applyBadgeReward(user, reward);
        } else {
          log.error("Goal has invalid reward type! reward: {}", reward);
          throw new IllegalArgumentException("Invalid goal reward");
        }
      });
    } else {
      log.info("Goal has no award {}", goal);
    }


  }

  private void applyBadgeReward(User user, GoalAward reward) {
    log.info("Apply badge reward: {0} for user with id: {1}", reward.getId(), user.getId());
    Optional<UserBadge> optUserBadge = this.userBadgeRepository.findByBadgeIdAndUserId(reward.getData().getId(),
      user.getId());
    if (optUserBadge.isPresent()) {
      log.info("Badge: {} already awarded. Cannot be awarded twice!", optUserBadge.get().getBadge().getName());
    } else {
      Optional<Badge> badge = this.badgeRepository.findById(reward.getData().getId());
      if (badge.isPresent()) {
        UserBadge userBadge1 = new UserBadge();
        userBadge1.setBadge(badge.get());
        userBadge1.setUser(user);
        userBadge1.setFinishedDate(Instant.now());
        userBadge1 = this.userBadgeRepository.save(userBadge1);
        user.getUserBadges().add(userBadge1);
      } else {
        throw new IllegalArgumentException("Badge does not exist. Invalid reward!");
      }
    }
  }

  private void applyCompetenceReward(User user, GoalAward reward) {
    log.info("Apply competence reward: {0} for user with id: {1}", reward.getId(), user.getId());
    Optional<UserCompetence> optUserCompetence = this.userCompetenceRepository
      .findByCompIdAndUserId(reward.getData().getId(), user.getId());
    UserCompetence userCompetence;
    if (optUserCompetence.isPresent()) {
      userCompetence = optUserCompetence.get();
      userCompetence.setUserLevel(userCompetence.getUserLevel() + reward.getIncreaseLevel());
    } else {
      Optional<Competence> comp = this.competenceRepository.findById(reward.getData().getId());
      if (comp.isPresent()) {
        userCompetence = new UserCompetence();
        userCompetence.setComp(comp.get());
        userCompetence.setUser(user);
        userCompetence.setUserLevel(reward.getIncreaseLevel());
        userCompetence = this.userCompetenceRepository.save(userCompetence);
        user.getUserCompetences().add(userCompetence);
      } else {
        throw new IllegalArgumentException("Competence does not exist. Invalid reward!");
      }
    }
    createCompetenceProgress(user, reward, userCompetence);
  }

  private void createCompetenceProgress(User user, GoalAward reward, UserCompetence userCompetence) {
    CompetenceProgress trackProgress = new CompetenceProgress();
    trackProgress.setCompetence(userCompetence.getComp());
    trackProgress.setUser(user);
    trackProgress.setDate(Instant.now());
    // TODO maybe change to current level instead of change
    trackProgress.setUserLevelChange(reward.getIncreaseLevel());
    competenceProgressRepository.save(trackProgress);
  }


}
