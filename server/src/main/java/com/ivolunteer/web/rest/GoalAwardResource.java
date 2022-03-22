package com.ivolunteer.web.rest;

import com.ivolunteer.service.GoalAwardService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.GoalAwardDTO;

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
 * REST controller for managing {@link com.ivolunteer.domain.GoalAward}.
 */
@RestController
@RequestMapping("/api")
public class GoalAwardResource {

    private final Logger log = LoggerFactory.getLogger(GoalAwardResource.class);

    private static final String ENTITY_NAME = "goalAward";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalAwardService goalAwardService;

    public GoalAwardResource(GoalAwardService goalAwardService) {
        this.goalAwardService = goalAwardService;
    }

    /**
     * {@code POST  /goal-awards} : Create a new goalAward.
     *
     * @param goalAwardDTO the goalAwardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goalAwardDTO, or with status {@code 400 (Bad Request)} if the goalAward has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/goal-awards")
    public ResponseEntity<GoalAwardDTO> createGoalAward(@RequestBody GoalAwardDTO goalAwardDTO) throws URISyntaxException {
        log.debug("REST request to save GoalAward : {}", goalAwardDTO);
        if (goalAwardDTO.getId() != null) {
            throw new BadRequestAlertException("A new goalAward cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoalAwardDTO result = goalAwardService.save(goalAwardDTO);
        return ResponseEntity.created(new URI("/api/goal-awards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /goal-awards} : Updates an existing goalAward.
     *
     * @param goalAwardDTO the goalAwardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalAwardDTO,
     * or with status {@code 400 (Bad Request)} if the goalAwardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goalAwardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/goal-awards")
    public ResponseEntity<GoalAwardDTO> updateGoalAward(@RequestBody GoalAwardDTO goalAwardDTO) throws URISyntaxException {
        log.debug("REST request to update GoalAward : {}", goalAwardDTO);
        if (goalAwardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoalAwardDTO result = goalAwardService.save(goalAwardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goalAwardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /goal-awards} : get all the goalAwards.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goalAwards in body.
     */
    @GetMapping("/goal-awards")
    public ResponseEntity<List<GoalAwardDTO>> getAllGoalAwards(Pageable pageable) {
        log.debug("REST request to get a page of GoalAwards");
        Page<GoalAwardDTO> page = goalAwardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /goal-awards/:id} : get the "id" goalAward.
     *
     * @param id the id of the goalAwardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goalAwardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/goal-awards/{id}")
    public ResponseEntity<GoalAwardDTO> getGoalAward(@PathVariable Long id) {
        log.debug("REST request to get GoalAward : {}", id);
        Optional<GoalAwardDTO> goalAwardDTO = goalAwardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goalAwardDTO);
    }

    /**
     * {@code DELETE  /goal-awards/:id} : delete the "id" goalAward.
     *
     * @param id the id of the goalAwardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/goal-awards/{id}")
    public ResponseEntity<Void> deleteGoalAward(@PathVariable Long id) {
        log.debug("REST request to delete GoalAward : {}", id);
        goalAwardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
