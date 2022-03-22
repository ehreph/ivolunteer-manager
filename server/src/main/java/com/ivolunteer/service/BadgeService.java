package com.ivolunteer.service;

import com.ivolunteer.service.dto.BadgeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.Badge}.
 */
public interface BadgeService {

    /**
     * Save a badge.
     *
     * @param badgeDTO the entity to save.
     * @return the persisted entity.
     */
    BadgeDTO save(BadgeDTO badgeDTO);

    /**
     * Get all the badges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BadgeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" badge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BadgeDTO> findOne(Long id);

    /**
     * Delete the "id" badge.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    List<BadgeDTO> findNewBadgesForUser(Long userId);
}
