package com.ivolunteer.web.rest;

import com.ivolunteer.service.GoalRequirementsService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.GoalRequirementsDTO;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.GoalRequirements}.
 */
@RestController
@RequestMapping("/api")
public class GoalRequirementsResource {

    private final Logger log = LoggerFactory.getLogger(GoalRequirementsResource.class);

    private static final String ENTITY_NAME = "goalRequirements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalRequirementsService goalRequirementsService;

    public GoalRequirementsResource(GoalRequirementsService goalRequirementsService) {
        this.goalRequirementsService = goalRequirementsService;
    }

    /**
     * {@code POST  /goal-requirements} : Create a new goalRequirements.
     *
     * @param goalRequirementsDTO the goalRequirementsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goalRequirementsDTO, or with status {@code 400 (Bad Request)} if the goalRequirements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goal-requirements")
    public ResponseEntity<GoalRequirementsDTO> createGoalRequirements(@RequestBody GoalRequirementsDTO goalRequirementsDTO) throws URISyntaxException {
        log.debug("REST request to save GoalRequirements : {}", goalRequirementsDTO);
        if (goalRequirementsDTO.getId() != null) {
            throw new BadRequestAlertException("A new goalRequirements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoalRequirementsDTO result = goalRequirementsService.save(goalRequirementsDTO);
        return ResponseEntity.created(new URI("/api/goal-requirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goal-requirements} : Updates an existing goalRequirements.
     *
     * @param goalRequirementsDTO the goalRequirementsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalRequirementsDTO,
     * or with status {@code 400 (Bad Request)} if the goalRequirementsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goalRequirementsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goal-requirements")
    public ResponseEntity<GoalRequirementsDTO> updateGoalRequirements(@RequestBody GoalRequirementsDTO goalRequirementsDTO) throws URISyntaxException {
        log.debug("REST request to update GoalRequirements : {}", goalRequirementsDTO);
        if (goalRequirementsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoalRequirementsDTO result = goalRequirementsService.save(goalRequirementsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goalRequirementsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goal-requirements} : get all the goalRequirements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goalRequirements in body.
     */
    @GetMapping("/goal-requirements")
    public ResponseEntity<List<GoalRequirementsDTO>> getAllGoalRequirements(Pageable pageable) {
        log.debug("REST request to get a page of GoalRequirements");
        Page<GoalRequirementsDTO> page = goalRequirementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goal-requirements/:id} : get the "id" goalRequirements.
     *
     * @param id the id of the goalRequirementsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goalRequirementsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goal-requirements/{id}")
    public ResponseEntity<GoalRequirementsDTO> getGoalRequirements(@PathVariable Long id) {
        log.debug("REST request to get GoalRequirements : {}", id);
        Optional<GoalRequirementsDTO> goalRequirementsDTO = goalRequirementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goalRequirementsDTO);
    }

    /**
     * {@code DELETE  /goal-requirements/:id} : delete the "id" goalRequirements.
     *
     * @param id the id of the goalRequirementsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goal-requirements/{id}")
    public ResponseEntity<Void> deleteGoalRequirements(@PathVariable Long id) {
        log.debug("REST request to delete GoalRequirements : {}", id);
        goalRequirementsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
