package com.ivolunteer.service.mapper;


import com.ivolunteer.domain.*;
import com.ivolunteer.service.dto.CompetenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Competence} and its DTO {@link CompetenceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CompetenceMapper  extends EntityMapper<CompetenceDTO, Competence>  {



    default Competence fromId(Long id) {
        if (id == null) {
            return null;
        }
        Competence competence = new Competence();
        competence.setId(id);
        return competence;
    }
}
