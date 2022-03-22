package com.ivolunteer.service.impl;

import com.ivolunteer.service.BadgeService;
import com.ivolunteer.domain.Badge;
import com.ivolunteer.repository.BadgeRepository;
import com.ivolunteer.service.dto.BadgeDTO;
import com.ivolunteer.service.mapper.BadgeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Badge}.
 */
@Service
@Transactional
public class BadgeServiceImpl implements BadgeService {

  private final Logger log = LoggerFactory.getLogger(BadgeServiceImpl.class);

  private final BadgeRepository badgeRepository;

  private final BadgeMapper badgeMapper;

  public BadgeServiceImpl(BadgeRepository badgeRepository, BadgeMapper badgeMapper) {
    this.badgeRepository = badgeRepository;
    this.badgeMapper = badgeMapper;
  }

  @Override
  public BadgeDTO save(BadgeDTO badgeDTO) {
    log.debug("Request to save Badge : {}", badgeDTO);
    Badge badge = badgeMapper.toEntity(badgeDTO);
    badge = badgeRepository.save(badge);
    return badgeMapper.toDto(badge);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<BadgeDTO> findAll(Pageable pageable) {
    log.debug("Request to get all Badges");
    return badgeRepository.findAll(pageable)
      .map(badgeMapper::toDto);
  }


  @Override
  @Transactional(readOnly = true)
  public Optional<BadgeDTO> findOne(Long id) {
    log.debug("Request to get Badge : {}", id);
    return badgeRepository.findById(id)
      .map(badgeMapper::toDto);
  }

  @Override
  public void delete(Long id) {
    log.debug("Request to delete Badge : {}", id);
    badgeRepository.deleteById(id);
  }

  @Override
  public List<BadgeDTO> findNewBadgesForUser(Long userId) {
    List<Badge> list = badgeRepository.findAllNewBadgesForUser(userId);
    return badgeMapper.toDto(list);
  }
}
