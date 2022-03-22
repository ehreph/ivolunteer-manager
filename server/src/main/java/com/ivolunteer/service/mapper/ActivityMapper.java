package com.ivolunteer.service.mapper;


import org.mapstruct.Mapper;

import com.ivolunteer.domain.Activity;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.service.dto.CompetenceDTO;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring",  uses = {RuleAttributeMapper.class})
public interface ActivityMapper extends EntityMapper<ActivityDTO, Activity> {


    @Mapping(source = "attributesId", target = "attributes")
    Activity toEntity(ActivityDTO dto);

    @Mapping(source = "attributes.id", target = "attributesId")
    @Mapping(source = "attributes.name", target = "attributesName")
    @Mapping(source = "attributes.unitName", target = "attributesUnitName")
    ActivityDTO toDto(Activity entity);

    default Activity fromId(Long id) {
        if (id == null) {
            return null;
        }
        Activity activity = new Activity();
        activity.setId(id);
        return activity;
    }
}
