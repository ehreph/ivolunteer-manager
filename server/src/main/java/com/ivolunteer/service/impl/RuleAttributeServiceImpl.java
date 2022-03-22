package com.ivolunteer.service.impl;

import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.service.RuleAttributeService;
import com.ivolunteer.domain.RuleAttribute;
import com.ivolunteer.repository.RuleAttributeRepository;
import com.ivolunteer.service.dto.RuleAttributeDTO;
import com.ivolunteer.service.mapper.RuleAttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RuleAttribute}.
 */
@Service
@Transactional
public class RuleAttributeServiceImpl implements RuleAttributeService {

    private final Logger log = LoggerFactory.getLogger(RuleAttributeServiceImpl.class);

    private final RuleAttributeRepository ruleAttributeRepository;

    private final RuleAttributeMapper ruleAttributeMapper;

    public RuleAttributeServiceImpl(RuleAttributeRepository ruleAttributeRepository, RuleAttributeMapper ruleAttributeMapper) {
        this.ruleAttributeRepository = ruleAttributeRepository;
        this.ruleAttributeMapper = ruleAttributeMapper;
    }

    @Override
    public RuleAttributeDTO save(RuleAttributeDTO ruleAttributeDTO) {
        log.debug("Request to save RuleAttribute : {}", ruleAttributeDTO);
        RuleAttribute ruleAttribute = ruleAttributeMapper.toEntity(ruleAttributeDTO);
        ruleAttribute = ruleAttributeRepository.save(ruleAttribute);
        return ruleAttributeMapper.toDto(ruleAttribute);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleAttributeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RuleAttributes");
        return ruleAttributeRepository.findAll(pageable)
            .map(ruleAttributeMapper::toDto);
    }

  @Override
  @Transactional(readOnly = true)
  public Page<RuleAttributeDTO> findAllOfType(Pageable pageable, GlobalType filter) {
    log.debug("Request to get all RuleAttributes");
    return ruleAttributeRepository.findAllByRuleType(pageable,filter)
      .map(ruleAttributeMapper::toDto);
  }



  @Override
    @Transactional(readOnly = true)
    public Optional<RuleAttributeDTO> findOne(Long id) {
        log.debug("Request to get RuleAttribute : {}", id);
        return ruleAttributeRepository.findById(id)
            .map(ruleAttributeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RuleAttribute : {}", id);
        ruleAttributeRepository.deleteById(id);
    }
}
