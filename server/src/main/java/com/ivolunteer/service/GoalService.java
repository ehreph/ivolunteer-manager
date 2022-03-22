package com.ivolunteer.service;

import com.ivolunteer.service.dto.GoalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.Goal}.
 */
public interface GoalService {

  /**
   * Save a goal.
   *
   * @param goalDTO the entity to save.
   * @return the persisted entity.
   */
  GoalDTO save(GoalDTO goalDTO);

  /**
   * Get all the goals.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<GoalDTO> findAll(Pageable pageable);


  /**
   * Get the "id" goal.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<GoalDTO> findOne(Long id);

  /**
   * Delete the "id" goal.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);

  GoalDTO savePersonalGoal(GoalDTO goalDTO);

  List<GoalDTO> findNewGoalsForUser(Long userId);
}
