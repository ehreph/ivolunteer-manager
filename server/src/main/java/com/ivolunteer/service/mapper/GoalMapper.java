package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.GoalDTO;

import com.ivolunteer.service.dto.VisibleGoalDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Goal} and its DTO {@link GoalDTO}.
 */
@Mapper(componentModel = "spring", uses = {InstitutionMapper.class,
    GoalAwardMapper.class,
    GoalRequirementsMapper.class})
public interface GoalMapper extends EntityMapper<GoalDTO, Goal> {

    @Mapping(target = "awardedCompetences")
    @Mapping(target = "requirements")
    @Mapping(source = "institution.id", target = "institutionId")
    @Mapping(source = "institution.name", target = "institutionName")
    GoalDTO toDto(Goal goal);


    @Mapping(target = "awardedCompetences")
    @Mapping(target = "requirements")
    VisibleGoalDTO toVisibleGoalDto(Goal goal);


    @Mapping(target = "awardedCompetences")
    @Mapping(target = "requirements")
    @Mapping(source = "institutionId", target = "institution")
    Goal toEntity(GoalDTO goalDTO);

    default Goal fromId(Long id) {
        if (id == null) {
            return null;
        }
        Goal goal = new Goal();
        goal.setId(id);
        return goal;
    }
}
