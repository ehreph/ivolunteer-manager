package com.ivolunteer.user;

import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.service.dto.VisibleGoalDTO;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * REST controller for managing {@link com.ivolunteer.domain.Goal}.
 */
@RestController
@RequestMapping("/api")
public class VisibleGoalResource {

    private final Logger log = LoggerFactory.getLogger(VisibleGoalResource.class);

    private static final String ENTITY_NAME = "goal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VisibleGoalService visibleGoalService;

    public VisibleGoalResource(VisibleGoalService visibleGoalService) {
        this.visibleGoalService = visibleGoalService;
    }


    /**
     * {@code GET  /goals} : get all the goals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goals in body.
     */
    @GetMapping("/goals/visible")
    public ResponseEntity<List<VisibleGoalDTO>> getAllGoalsAvailableGoalsForCurrentUser(Pageable pageable) {
        log.debug("REST request to get a page of Goals");
        Page<VisibleGoalDTO> page = visibleGoalService.findAllGoalsAvailableGoalsForCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
