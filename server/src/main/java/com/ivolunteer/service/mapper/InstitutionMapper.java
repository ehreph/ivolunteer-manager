package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.InstitutionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Institution} and its DTO {@link InstitutionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InstitutionMapper extends EntityMapper<InstitutionDTO, Institution> {


    @Mapping(target = "institutionGoals", ignore = true)
    @Mapping(target = "removeInstitutionGoals", ignore = true)
    Institution toEntity(InstitutionDTO institutionDTO);

    default Institution fromId(Long id) {
        if (id == null) {
            return null;
        }
        Institution institution = new Institution();
        institution.setId(id);
        return institution;
    }
}
