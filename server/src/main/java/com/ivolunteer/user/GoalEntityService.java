package com.ivolunteer.user;

import java.util.Optional;

import com.ivolunteer.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.repository.ActivityRepository;
import com.ivolunteer.repository.BadgeRepository;
import com.ivolunteer.repository.CompetenceRepository;
import com.ivolunteer.repository.GoalRepository;
import com.ivolunteer.repository.UserGoalsRepository;
import com.ivolunteer.service.UserService;
import com.ivolunteer.service.dto.GoalAwardDTO;
import com.ivolunteer.service.dto.GoalRequirementsDTO;
import com.ivolunteer.service.mapper.GoalMapper;

@Service
@Transactional
public class GoalEntityService {

    private final GoalRepository goalRepository;
    private final UserService userService;
    private final UserGoalsRepository userGoalsRepository;
    private final GoalMapper goalMapper;
    private final CompetenceRepository competenceRepository;
    private final BadgeRepository badgeRepository;
    private final ActivityRepository activityRepository;

    public GoalEntityService(GoalRepository goalRepository, UserService userService,
                              UserGoalsRepository userGoalsRepository, GoalMapper goalMapper,
                              CompetenceRepository competenceRepository,
                              BadgeRepository badgeRepository,
                              ActivityRepository activityRepository) {
        this.goalRepository = goalRepository;
        this.userService = userService;
        this.userGoalsRepository = userGoalsRepository;
        this.goalMapper = goalMapper;
        this.competenceRepository = competenceRepository;
        this.badgeRepository = badgeRepository;
        this.activityRepository = activityRepository;
    }


    public void setAwardEntityName(GoalAwardDTO awardedCompetence) {
        //TODO if slow add query for name only
        if (awardedCompetence.getAwardType().equals(GlobalType.COMPETENCE)) {
            Optional<Competence> competence = competenceRepository.findById(awardedCompetence.getGeneralId());
            if (competence.isPresent()) {
                awardedCompetence.setEntityName(competence.get().getName());
            }
        } else if (awardedCompetence.getAwardType().equals(GlobalType.BADGE)) {
            Optional<Badge> badge = badgeRepository.findById(awardedCompetence.getGeneralId());
            if (badge.isPresent()) {
                awardedCompetence.setEntityName(badge.get().getName());
            }
        }
    }

    public void setRequirementEntityName(GoalRequirementsDTO goalRequirementsDTO) {
        //TODO if slow add query for name only
        if (goalRequirementsDTO.getType().equals(GlobalType.COMPETENCE)) {
            Optional<Competence> competence = competenceRepository.findById(goalRequirementsDTO.getGeneralId());
            if (competence.isPresent()) {
                goalRequirementsDTO.setEntityName(competence.get().getName());
            }
        } else if (goalRequirementsDTO.getType().equals(GlobalType.BADGE)) {
            Optional<Badge> badge = badgeRepository.findById(goalRequirementsDTO.getGeneralId());
            if (badge.isPresent()) {
                goalRequirementsDTO.setEntityName(badge.get().getName());
            }
        } else if (goalRequirementsDTO.getType().equals(GlobalType.GOAL)) {
            Optional<Goal> goal = goalRepository.findById(goalRequirementsDTO.getGeneralId());
            if (goal.isPresent()) {
                goalRequirementsDTO.setEntityName(goal.get().getName());
            }
        } else if (goalRequirementsDTO.getType().equals(GlobalType.ACTIVITY)) {
            Optional<Activity> activity = activityRepository.findById(goalRequirementsDTO.getGeneralId());
            if (activity.isPresent()) {
                Activity a = activity.get();
                goalRequirementsDTO.setEntityName(a.getName());
                goalRequirementsDTO.setAttributesId(a.getAttributes().getId());
                goalRequirementsDTO.setAttributesName(a.getAttributes().getName());
                goalRequirementsDTO.setAttributesUnitName(a.getAttributes().getUnitName());
            }
        }
    }
}
