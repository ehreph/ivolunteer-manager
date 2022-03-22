package com.ivolunteer.user;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.user.dto.UserGoalsDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.UserGoals}.
 */
@RestController
@RequestMapping("/api")
public class UserGoalsResource {

  private final Logger log = LoggerFactory.getLogger(UserGoalsResource.class);

  private static final String ENTITY_NAME = "userGoals";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final UserGoalsService userGoalsService;

  public UserGoalsResource(UserGoalsService userGoalsService) {
    this.userGoalsService = userGoalsService;
  }

  /**
   * {@code POST  /user-goals} : Create a new userGoals.
   *
   * @param userGoalsDTO the userGoalsDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGoalsDTO, or with status {@code 400 (Bad Request)} if the userGoals has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/user-goals")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<UserGoalsDTO> adminAssignGoalToUser(
    @RequestBody UserGoalsDTO userGoalsDTO) throws URISyntaxException {
    log.debug("REST request to save UserGoals : {}", userGoalsDTO);
    if (userGoalsDTO.getId() != null) {
      throw new BadRequestAlertException("A new userGoals cannot already have an ID", ENTITY_NAME, "idexists");
    }
    UserGoalsDTO result = userGoalsService.save(userGoalsDTO);
    return ResponseEntity.created(new URI("/api/user-goals/" + result.getId()))
      .headers(
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }


  /**
   * {@code POST  /user-goals/subscribe/goal/{goalId}} : Create a new userGoals from existing goal and subscribe to user.
   *
   * @param goalId the goal to subscribe the user to.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userGoalsDTO, or with status {@code 400 (Bad Request)} if the userGoals has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/user-goals/subscribe/goal/{goalId}")
  @Secured(value = AuthoritiesConstants.USER)
  public ResponseEntity<UserGoalsDTO> subscribeToGoal(
    @PathVariable Long goalId) throws URISyntaxException {
    log.debug("REST request to create subscription for user of existing goal with id : {}", goalId);
    if (goalId == null) {
      throw new BadRequestAlertException("A new userGoals cannot be create if goal ID is null", ENTITY_NAME, "idnull");
    }
    UserGoalsDTO result = userGoalsService.subscribeUser(goalId);
    return ResponseEntity.created(new URI("/api/user-goals/" + result.getId()))
      .headers(
        HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);

  }


  /**
   * {@code PUT  /user-goals} : Updates an existing userGoals.
   *
   * @param userGoalsDTO the userGoalsDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userGoalsDTO,
   * or with status {@code 400 (Bad Request)} if the userGoalsDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the userGoalsDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/user-goals")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<UserGoalsDTO> updateUserGoals(
    @RequestBody UserGoalsDTO userGoalsDTO) throws URISyntaxException {
    log.debug("REST request to update UserGoals : {}", userGoalsDTO);
    if (userGoalsDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    UserGoalsDTO result = userGoalsService.save(userGoalsDTO);
    return ResponseEntity.ok()
      .headers(
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userGoalsDTO.getId().toString()))
      .body(result);
  }

  /**
   * {@code GET  /user-goals} : get all the userGoals.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGoals in body.
   */
  @GetMapping("/user-goals")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<List<UserGoalsDTO>> getAllUserGoals(Pageable pageable) {
    log.debug("REST request to get a page of UserGoals");
    Page<UserGoalsDTO> page = userGoalsService.findAll(pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
      ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }


  /**
   * {@code GET  /user-goals} : get all the userGoals.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGoals in body.
   */
  @GetMapping("/user-goals/user/{userId}")
  @Secured(AuthoritiesConstants.USER)
  public ResponseEntity<List<UserGoalsDTO>> getAllUserGoalsByUserId(@PathVariable Long userId, Pageable pageable) {
    log.debug("REST request to get a page of UserCompetences");
    Page<UserGoalsDTO> page = userGoalsService.findAllByUserId(userId, pageable);
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
      ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }


  /**
   * {@code GET  /user-goals/:id} : get the "id" userGoals.
   *
   * @param id the id of the userGoalsDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userGoalsDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/user-goals/{id}")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<UserGoalsDTO> getUserGoals(@PathVariable Long id) {
    log.debug("REST request to get UserGoals : {}", id);
    Optional<UserGoalsDTO> userGoalsDTO = userGoalsService.findOne(id);
    return ResponseUtil.wrapOrNotFound(userGoalsDTO);
  }

  /**
   * {@code DELETE  /user-goals/:id} : delete the "id" userGoals.
   *
   * @param id the id of the userGoalsDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/user-goals/{id}")
  @Secured(AuthoritiesConstants.USER)
  public ResponseEntity<Void> deleteUserGoals(@PathVariable Long id) {
    log.debug("REST request to delete UserGoals : {}", id);
    userGoalsService.delete(id);
    return ResponseEntity.noContent().headers(
      HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }


  /**
   * {@code POST  /user-goals/{id}/finish} :finish userGoal
   */
  @PostMapping("/user-goals/{id}/finish")
  @Secured(AuthoritiesConstants.USER)
  public ResponseEntity<UserGoalsDTO> finishUserGoal(@PathVariable Long id) throws URISyntaxException {
    log.debug("REST request to finish UserGoal : {}", id);
    UserGoalsDTO result = userGoalsService.finishUserGoal(id);
    return ResponseEntity.ok()
      .headers(
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);

  }

}
