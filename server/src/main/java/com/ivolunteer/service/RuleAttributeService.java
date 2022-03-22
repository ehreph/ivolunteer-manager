package com.ivolunteer.service;

import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.service.dto.RuleAttributeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.ivolunteer.domain.RuleAttribute}.
 */
public interface RuleAttributeService {

    /**
     * Save a ruleAttribute.
     *
     * @param ruleAttributeDTO the entity to save.
     * @return the persisted entity.
     */
    RuleAttributeDTO save(RuleAttributeDTO ruleAttributeDTO);

    /**
     * Get all the ruleAttributes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RuleAttributeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" ruleAttribute.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RuleAttributeDTO> findOne(Long id);

    /**
     * Delete the "id" ruleAttribute.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

  Page<RuleAttributeDTO> findAllOfType(Pageable pageable, GlobalType filter);
}
