package com.ivolunteer.service;

import com.ivolunteer.service.dto.UserBadgeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.UserBadge}.
 */
public interface UserBadgeService {

    /**
     * Save a userBadge.
     *
     * @param userBadgeDTO the entity to save.
     * @return the persisted entity.
     */
    UserBadgeDTO save(UserBadgeDTO userBadgeDTO);

    /**
     * Get all the userBadges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserBadgeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" userBadge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserBadgeDTO> findOne(Long id);

    /**
     * Delete the "id" userBadge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    Page<UserBadgeDTO> findAllByUserId(Long userId, Pageable pageable);
}
