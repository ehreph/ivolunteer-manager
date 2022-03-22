package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.UserBadgeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserBadge} and its DTO {@link UserBadgeDTO}.
 */
@Mapper(componentModel = "spring", uses = {BadgeMapper.class,UserMapper.class})
public interface UserBadgeMapper extends EntityMapper<UserBadgeDTO, UserBadge> {

    @Mapping(source = "badge.id", target = "badgeId")
    @Mapping(source = "badge.name", target = "badgeName")
    @Mapping(source = "user.id", target = "userId")
    UserBadgeDTO toDto(UserBadge userBadge);

    @Mapping(source = "badgeId", target = "badge")
    @Mapping(source = "userId", target = "user")
    UserBadge toEntity(UserBadgeDTO userBadgeDTO);

    default UserBadge fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserBadge userBadge = new UserBadge();
        userBadge.setId(id);
        return userBadge;
    }
}
