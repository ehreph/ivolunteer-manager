package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.UserCompetenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserCompetence} and its DTO {@link UserCompetenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {CompetenceMapper.class,UserMapper.class})
public interface UserCompetenceMapper extends EntityMapper<UserCompetenceDTO, UserCompetence> {

    @Mapping(source = "comp.id", target = "compId")
    @Mapping(source = "comp.name", target = "compName")
    @Mapping(source = "user.id", target = "userId")
    UserCompetenceDTO toDto(UserCompetence userCompetence);

    @Mapping(source = "compId", target = "comp")
    @Mapping(source = "userId", target = "user")
    UserCompetence toEntity(UserCompetenceDTO userCompetenceDTO);

    default UserCompetence fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserCompetence userCompetence = new UserCompetence();
        userCompetence.setId(id);
        return userCompetence;
    }
}
