package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.BaseDataDTO;

import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link BaseData} and its DTO {@link BaseDataDTO}.
 */
@Mapper(componentModel = "spring", uses = {ActivityMapper.class, GoalMapper.class, CompetenceMapper.class, BadgeMapper.class})
public interface BaseDataMapper extends EntityMapper<BaseDataDTO, BaseData> {

  BaseDataDTO toDto(BaseData data);


  BaseData toEntity(BaseDataDTO data);

  @BaseInfo
  default BaseData fromId(Long id) {
    if (id == null) {
      return null;
    }
    BaseData data = new BaseData();
    data.setId(id);
    return data;
  }
}
