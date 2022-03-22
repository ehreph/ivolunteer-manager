package com.ivolunteer.user;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.service.GoalService;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.Goal}.
 */
@RestController
@RequestMapping("/api")
public class PersonalGoalResource {

    private final Logger log = LoggerFactory.getLogger(PersonalGoalResource.class);

    private static final String ENTITY_NAME = "goal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoalService goalService;

    public PersonalGoalResource(GoalService goalService) {
        this.goalService = goalService;
    }

    /**
     * {@code POST  /personal-goals} : Create a new goal.
     *
     * @param goalDTO the goalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goalDTO, or with status {@code 400 (Bad Request)} if the goal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-goals")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<GoalDTO> createPersonalGoal(@RequestBody GoalDTO goalDTO) throws URISyntaxException {
        log.debug("REST request to save Goal : {}", goalDTO);
        if (goalDTO.getId() != null) {
            throw new BadRequestAlertException("A new goal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoalDTO result = goalService.savePersonalGoal(goalDTO);
        return ResponseEntity.created(new URI("/api/goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-goals} : Updates an existing personal goal.
     *
     * @param goalDTO the goalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goalDTO,
     * or with status {@code 400 (Bad Request)} if the goalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-goals")
    public ResponseEntity<GoalDTO> updatePersonalGoal(@RequestBody GoalDTO goalDTO) throws URISyntaxException {
        log.debug("REST request to update Goal : {}", goalDTO);
        if (goalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GoalDTO result = goalService.savePersonalGoal(goalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goalDTO.getId().toString()))
            .body(result);
    }


    /**
     * {@code GET  /personal-goals/:id} : get personal goal with "id".
     *
     * @param id the id of the goalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-goals/{id}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<GoalDTO> getPersonalGoal(@PathVariable Long id) {
        log.debug("REST request to get Goal : {}", id);
        Optional<GoalDTO> goalDTO = goalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goalDTO);
    }

//    /**
//     * {@code DELETE  /personal-goals/:id} : delete the "id" goal.
//     *
//     * @param id the id of the goalDTO to delete.
//     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
//     */
//    @DeleteMapping("/personal-goals/{id}")
//    public ResponseEntity<Void> deleteGoal(@PathVariable Long id) {
//        log.debug("REST request to delete Goal : {}", id);
//        goalService.delete(id);
//        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
//    }
}
