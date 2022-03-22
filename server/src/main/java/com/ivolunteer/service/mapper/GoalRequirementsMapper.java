package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.GoalRequirementsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GoalRequirements} and its DTO {@link GoalRequirementsDTO}.
 */
@Mapper(componentModel = "spring", uses = {RuleAttributeMapper.class, GoalMapper.class, BaseDataMapper.class})
public interface GoalRequirementsMapper extends EntityMapper<GoalRequirementsDTO, GoalRequirements> {

  @Mapping(source = "goal.id", target = "goalId")
  @Mapping(source = "data.id", target = "generalId")
  @Mapping(source = "data.name", target = "entityName")
  @Mapping(source = "attributes.id", target = "attributesId")
  @Mapping(source = "attributes.name", target = "attributesName")
  @Mapping(source = "attributes.unitName", target = "attributesUnitName")
  GoalRequirementsDTO toDto(GoalRequirements goalRequirements);


  @Mapping(source = "goalId", target = "goal")
  @Mapping(source = "attributesId", target = "attributes")
  @Mapping(source = "generalId", target = "data", qualifiedBy = BaseInfo.class)
  GoalRequirements toEntity(GoalRequirementsDTO goalRequirementsDTO);

  default GoalRequirements fromId(Long id) {
    if (id == null) {
      return null;
    }
    GoalRequirements goalRequirements = new GoalRequirements();
    goalRequirements.setId(id);
    return goalRequirements;
  }
}
