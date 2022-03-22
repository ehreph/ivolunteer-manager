package com.ivolunteer.web.rest;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.service.CompetenceService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.CompetenceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.Competence}.
 */
@RestController
@RequestMapping("/api")
public class CompetenceResource {

  private final Logger log = LoggerFactory.getLogger(CompetenceResource.class);

  private static final String ENTITY_NAME = "competence";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final CompetenceService competenceService;

  public CompetenceResource(CompetenceService competenceService) {
    this.competenceService = competenceService;
  }

  /**
   * {@code POST  /competences} : Create a new competence.
   *
   * @param competenceDTO the competenceDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competenceDTO, or with status {@code 400 (Bad Request)} if the competence has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/competences")
  public ResponseEntity<CompetenceDTO> createCompetence(
    @RequestBody CompetenceDTO competenceDTO) throws URISyntaxException {
    log.debug("REST request to save Competence : {}", competenceDTO);
    if (competenceDTO.getId() != null) {
      throw new BadRequestAlertException("A new competence cannot already have an ID", ENTITY_NAME, "idexists");
    }
    CompetenceDTO result = competenceService.save(competenceDTO);
    return ResponseEntity.created(new URI("/api/competences/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /competences} : Updates an existing competence.
   *
   * @param competenceDTO the competenceDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competenceDTO,
   * or with status {@code 400 (Bad Request)} if the competenceDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the competenceDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/competences")
  public ResponseEntity<CompetenceDTO> updateCompetence(
    @RequestBody CompetenceDTO competenceDTO) throws URISyntaxException {
    log.debug("REST request to update Competence : {}", competenceDTO);
    if (competenceDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    CompetenceDTO result = competenceService.save(competenceDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, competenceDTO.getId().toString()))
      .body(result);
  }

  /**
   * {@code GET  /competences} : get all the competences.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competences in body.
   */
  @GetMapping("/competences")
  public ResponseEntity<List<CompetenceDTO>> getAllCompetences(Pageable pageable) {
    log.debug("REST request to get a page of Competences");
    Page<CompetenceDTO> page = competenceService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
      page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /competences} : get all the competences.
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competences in body.
   */
  @GetMapping("/competences/user/{userId}")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<List<CompetenceDTO>> getNewCompetencesForUser(@PathVariable("userId") Long userId) {
    log.debug("REST request to get Competences not assigned to user");
    List<CompetenceDTO> list = competenceService.findNewCompetencesForUser(userId);
    return ResponseEntity.ok().body(list);
  }


  /**
   * {@code GET  /competences/:id} : get the "id" competence.
   *
   * @param id the id of the competenceDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competenceDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/competences/{id}")
  public ResponseEntity<CompetenceDTO> getCompetence(@PathVariable Long id) {
    log.debug("REST request to get Competence : {}", id);
    Optional<CompetenceDTO> competenceDTO = competenceService.findOne(id);
    return ResponseUtil.wrapOrNotFound(competenceDTO);
  }

  /**
   * {@code DELETE  /competences/:id} : delete the "id" competence.
   *
   * @param id the id of the competenceDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/competences/{id}")
  public ResponseEntity<Void> deleteCompetence(@PathVariable Long id) {
    log.debug("REST request to delete Competence : {}", id);
    competenceService.delete(id);
    return ResponseEntity.noContent().headers(
      HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
