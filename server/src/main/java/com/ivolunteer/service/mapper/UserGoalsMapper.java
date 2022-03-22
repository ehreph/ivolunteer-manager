package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.user.dto.MyGoalDTO;
import com.ivolunteer.user.dto.UserGoalsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserGoals} and its DTO {@link UserGoalsDTO}.
 */
@Mapper(componentModel = "spring", uses = {GoalMapper.class, UserMapper.class})
public interface UserGoalsMapper extends EntityMapper<UserGoalsDTO, UserGoals> {

  @Mapping(source = "goal.id", target = "goalId")
  @Mapping(source = "goal.name", target = "goalName")
  @Mapping(source = "user.id", target = "userId")
  UserGoalsDTO toDto(UserGoals userGoals);

  @Mapping(source = "goalId", target = "goal")
  @Mapping(source = "userId", target = "user")
  UserGoals toEntity(UserGoalsDTO userGoalsDTO);

  default UserGoals fromId(Long id) {
    if (id == null) {
      return null;
    }
    UserGoals userGoals = new UserGoals();
    userGoals.setId(id);
    return userGoals;
  }


  @Mapping(source = "user.id", target = "userId")
//  @Mapping(source = "user.userCompetences", target = "userCompetences")
//  @Mapping(source = "user.userBadges", target = "userBadges")
//  @Mapping(source = "user.userGoals", target = "userGoals")
  MyGoalDTO toMyGoalDTO(UserGoals userGoals);
}
