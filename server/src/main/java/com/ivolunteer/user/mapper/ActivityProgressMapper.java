package com.ivolunteer.user.mapper;


import com.ivolunteer.domain.Activity;
import com.ivolunteer.domain.ActivityProgress;
import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.service.mapper.ActivityMapper;
import com.ivolunteer.service.mapper.EntityMapper;
import com.ivolunteer.service.mapper.RuleAttributeMapper;
import com.ivolunteer.service.mapper.UserMapper;
import com.ivolunteer.user.dto.ActivityProgressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Activity} and its DTO {@link ActivityDTO}.
 */
@Mapper(componentModel = "spring",  uses = {RuleAttributeMapper.class, ActivityMapper.class, UserMapper.class})
public interface ActivityProgressMapper extends EntityMapper<ActivityProgressDTO, ActivityProgress> {


    @Mapping(source = "attributesId", target = "attributes")
    ActivityProgress toEntity(ActivityProgressDTO dto);

    @Mapping(source = "attributes.id", target = "attributesId")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "activity.id", target = "activityId")
    ActivityProgressDTO toDto(ActivityProgress entity);

    default ActivityProgress fromId(Long id) {
        if (id == null) {
            return null;
        }
        ActivityProgress activityProgress = new ActivityProgress();
        activityProgress.setId(id);
        return activityProgress;
    }
}
