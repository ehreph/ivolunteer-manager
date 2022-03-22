package com.ivolunteer.user;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.user.dto.MyGoalDTO;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

/**
 * REST controller for managing {@link com.ivolunteer.domain.UserGoals}.
 */
@RestController
@RequestMapping("/api")
public class MyGoalsResource {

    private final Logger log = LoggerFactory.getLogger(MyGoalsResource.class);

    private static final String ENTITY_NAME = "myGoals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MyGoalsService myGoalsService;

    public MyGoalsResource(MyGoalsService myGoalsService) {
        this.myGoalsService = myGoalsService;
    }


    /**
     * {@code GET  /user-goals/current} : get all the userGoals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGoals in body.
     */
    @GetMapping("/my-goals")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<MyGoalDTO>> getAllUserGoalsByUserId(Pageable pageable) {
        log.debug("REST request to get a page of UserCompetences");
        Page<MyGoalDTO> page = myGoalsService.findAllForCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


}
