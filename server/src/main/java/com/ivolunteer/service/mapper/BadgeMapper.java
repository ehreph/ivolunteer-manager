package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.BadgeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {



    default Badge fromId(Long id) {
        if (id == null) {
            return null;
        }
        Badge badge = new Badge();
        badge.setId(id);
        return badge;
    }
}
