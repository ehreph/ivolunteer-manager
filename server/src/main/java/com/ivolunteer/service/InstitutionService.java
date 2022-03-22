package com.ivolunteer.service;

import com.ivolunteer.service.dto.InstitutionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.Institution}.
 */
public interface InstitutionService {

    /**
     * Save a institution.
     *
     * @param institutionDTO the entity to save.
     * @return the persisted entity.
     */
    InstitutionDTO save(InstitutionDTO institutionDTO);

    /**
     * Get all the institutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<InstitutionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" institution.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<InstitutionDTO> findOne(Long id);

    /**
     * Delete the "id" institution.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
