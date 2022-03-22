package com.ivolunteer.web.rest;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.service.GoalService;
import com.ivolunteer.service.dto.CompetenceDTO;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.GoalDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.Goal}.
 */
@RestController
@RequestMapping("/api")
public class GoalResource {

  private final Logger log = LoggerFactory.getLogger(GoalResource.class);

  private static final String ENTITY_NAME = "goal";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final GoalService goalService;

  public GoalResource(GoalService goalService) {
    this.goalService = goalService;
  }

  /**
   * {@code POST  /goals} : Create a new goal.
   *
   * @param goalDTO the goalDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goalDTO, or with status {@code 400 (Bad Request)} if the goal has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/goals")
  public ResponseEntity<GoalDTO> createGoal(@RequestBody GoalDTO goalDTO) throws URISyntaxException {
    log.debug("REST request to save Goal : {}", goalDTO);
    if (goalDTO.getId() != null) {
      throw new BadRequestAlertException("A new goal cannot already have an ID", ENTITY_NAME, "idexists");
    }
    GoalDTO result = goalService.save(goalDTO);
    return ResponseEntity.created(new URI("/api/goals/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /goals} : Updates an existing goal.
   *
   * @param goalDTO the goalDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalDTO,
   * or with status {@code 400 (Bad Request)} if the goalDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the goalDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/goals")
  public ResponseEntity<GoalDTO> updateGoal(@RequestBody GoalDTO goalDTO) throws URISyntaxException {
    log.debug("REST request to update Goal : {}", goalDTO);
    if (goalDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    GoalDTO result = goalService.save(goalDTO);
    return ResponseEntity.ok()
      .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goalDTO.getId().toString()))
      .body(result);
  }

  /**
   * {@code GET  /goals} : get all external goals.
   * This request is used to test external static sites 
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goals in body.
   */
  @GetMapping("/goals/external")
  public ResponseEntity<List<GoalDTO>> getAllExternalGoals(Pageable pageable) {
    log.debug("REST request to get a page of Goals");
    Page<GoalDTO> page = goalService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
      page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /goals} : get all the goals.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goals in body.
   */
  @GetMapping("/goals")
  public ResponseEntity<List<GoalDTO>> getAllGoals(Pageable pageable) {
    log.debug("REST request to get a page of Goals");
    Page<GoalDTO> page = goalService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
      page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  /**
   * {@code GET  /goals/:id} : get the "id" goal.
   *
   * @param id the id of the goalDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goalDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/goals/{id}")
  public ResponseEntity<GoalDTO> getGoal(@PathVariable Long id) {
    log.debug("REST request to get Goal : {}", id);
    Optional<GoalDTO> goalDTO = goalService.findOne(id);
    return ResponseUtil.wrapOrNotFound(goalDTO);
  }

  /**
   * {@code DELETE  /goals/:id} : delete the "id" goal.
   *
   * @param id the id of the goalDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/goals/{id}")
  public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
    log.debug("REST request to delete Goal : {}", id);
    goalService.delete(id);
    return ResponseEntity.noContent().headers(
      HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }

  /**
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goals in body.
   */
  @GetMapping("/goals/user/{userId}")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<List<GoalDTO>> getNewGoalsForUser(@PathVariable("userId") Long userId) {
    log.debug("REST request to get not assigned goals for user");
    List<GoalDTO> list = goalService.findNewGoalsForUser(userId);
    return ResponseEntity.ok().body(list);
  }


}
