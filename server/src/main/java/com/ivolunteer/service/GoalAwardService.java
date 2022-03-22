package com.ivolunteer.service;

import com.ivolunteer.service.dto.GoalAwardDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.GoalAward}.
 */
public interface GoalAwardService {

    /**
     * Save a goalAward.
     *
     * @param goalAwardDTO the entity to save.
     * @return the persisted entity.
     */
    GoalAwardDTO save(GoalAwardDTO goalAwardDTO);

    /**
     * Get all the goalAwards.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<GoalAwardDTO> findAll(Pageable pageable);


    /**
     * Get the "id" goalAward.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<GoalAwardDTO> findOne(Long id);

    /**
     * Delete the "id" goalAward.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
