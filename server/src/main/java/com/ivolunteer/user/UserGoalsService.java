package com.ivolunteer.user;

import com.ivolunteer.domain.Goal;
import com.ivolunteer.domain.User;
import com.ivolunteer.user.dto.MyGoalDTO;
import com.ivolunteer.user.dto.UserGoalsDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.UserGoals}.
 */
public interface UserGoalsService {

  /**
   * Save a userGoals.
   *
   * @param userGoalsDTO the entity to save.
   * @return the persisted entity.
   */
  UserGoalsDTO save(UserGoalsDTO userGoalsDTO);

  /**
   * Get all the userGoals.
   *
   * @param pageable the pagination information.
   * @return the list of entities.
   */
  Page<UserGoalsDTO> findAll(Pageable pageable);


  /**
   * Get the "id" userGoals.
   *
   * @param id the id of the entity.
   * @return the entity.
   */
  Optional<UserGoalsDTO> findOne(Long id);

  /**
   * Delete the "id" userGoals.
   *
   * @param id the id of the entity.
   */
  void delete(Long id);

  Page<UserGoalsDTO> findAllByUserId(Long userId, Pageable pageable);

  UserGoalsDTO subscribeUser(Long goalId);

  UserGoalsDTO subscribeUser(User user, Goal goal);

  UserGoalsDTO finishUserGoal(Long id);
}
