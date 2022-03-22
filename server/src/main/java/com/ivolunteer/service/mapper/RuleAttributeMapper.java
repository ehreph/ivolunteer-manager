package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.RuleAttributeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RuleAttribute} and its DTO {@link RuleAttributeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RuleAttributeMapper extends EntityMapper<RuleAttributeDTO, RuleAttribute> {



    default RuleAttribute fromId(Long id) {
        if (id == null) {
            return null;
        }
        RuleAttribute ruleAttribute = new RuleAttribute();
        ruleAttribute.setId(id);
        return ruleAttribute;
    }
}
