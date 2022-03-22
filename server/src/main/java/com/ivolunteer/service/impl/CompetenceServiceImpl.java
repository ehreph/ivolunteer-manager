package com.ivolunteer.service.impl;

import com.ivolunteer.service.CompetenceService;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.repository.CompetenceRepository;
import com.ivolunteer.service.dto.CompetenceDTO;
import com.ivolunteer.service.mapper.CompetenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Competence}.
 */
@Service
@Transactional
public class CompetenceServiceImpl implements CompetenceService {

    private final Logger log = LoggerFactory.getLogger(CompetenceServiceImpl.class);

    private final CompetenceRepository competenceRepository;

    private final CompetenceMapper competenceMapper;

    public CompetenceServiceImpl(CompetenceRepository competenceRepository, CompetenceMapper competenceMapper) {
        this.competenceRepository = competenceRepository;
        this.competenceMapper = competenceMapper;
    }

    @Override
    public CompetenceDTO save(CompetenceDTO competenceDTO) {
        log.debug("Request to save Competence : {}", competenceDTO);
        Competence competence = competenceMapper.toEntity(competenceDTO);
        competence = competenceRepository.save(competence);
        return competenceMapper.toDto(competence);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetenceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Competences");
        return competenceRepository.findAll(pageable)
            .map(competenceMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<CompetenceDTO> findOne(Long id) {
        log.debug("Request to get Competence : {}", id);
        return competenceRepository.findById(id)
            .map(competenceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Competence : {}", id);
        competenceRepository.deleteById(id);
    }


  @Override
  public List<CompetenceDTO> findNewCompetencesForUser(Long userId) {
      List<Competence> list =  competenceRepository.findAllNewCompetencesForUser(userId);
      return competenceMapper.toDto(list);
  }
}
