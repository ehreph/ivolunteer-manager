package com.ivolunteer.service;

import com.ivolunteer.service.dto.UserCompetenceDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.UserCompetence}.
 */
public interface UserCompetenceService {

    /**
     * Save a userCompetence.
     *
     * @param userCompetenceDTO the entity to save.
     * @return the persisted entity.
     */
    UserCompetenceDTO save(UserCompetenceDTO userCompetenceDTO);

    /**
     * Get all the userCompetences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserCompetenceDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userCompetence.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserCompetenceDTO> findOne(Long id);

    /**
     * Delete the "id" userCompetence.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<UserCompetenceDTO> findAllByUserId(Long userId,Pageable pageable);
}
