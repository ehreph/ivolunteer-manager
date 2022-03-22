package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.GoalAwardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoalAward} and its DTO {@link GoalAwardDTO}.
 */
@Mapper(componentModel = "spring", uses = {GoalMapper.class, BaseDataMapper.class})
public interface GoalAwardMapper extends EntityMapper<GoalAwardDTO, GoalAward> {

    @Mapping(source = "goal.id", target = "goalId")
    @Mapping(source = "data.id", target = "generalId")
    @Mapping(source = "data.name", target = "entityName")
    GoalAwardDTO toDto(GoalAward goalAward);

    @Mapping(source = "goalId", target = "goal")
    @Mapping(source = "generalId", target = "data.id")
    GoalAward toEntity(GoalAwardDTO goalAwardDTO);

    default GoalAward fromId(Long id) {
        if (id == null) {
            return null;
        }
        GoalAward goalAward = new GoalAward();
        goalAward.setId(id);
        return goalAward;
    }
}
