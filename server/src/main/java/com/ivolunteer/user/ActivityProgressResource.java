package com.ivolunteer.user;

import com.ivolunteer.service.ActivityService;
import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.user.dto.ActivityProgressDTO;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.Competence}.
 */
@RestController
@RequestMapping("/api")
public class ActivityProgressResource {

    private final Logger log = LoggerFactory.getLogger(ActivityProgressResource.class);

    private static final String ENTITY_NAME = "activityProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActivityProgressService activityProgressService;

    public ActivityProgressResource(ActivityProgressService activityProgressService) {
        this.activityProgressService = activityProgressService;
    }

    /**
     * {@code POST  /activity} : Create a new activityProgressDTO.
     *
     * @param activityProgressDTO the activityProgress to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new activityProgressDTO, or with status {@code 400 (Bad Request)} if the activity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/activity/progress")
    public ResponseEntity<ActivityProgressDTO> createActivityProgress(@RequestBody ActivityProgressDTO activityProgressDTO) throws URISyntaxException {
        log.debug("REST request to save activity progress : {}", activityProgressDTO);
        if (activityProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new activity progress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActivityProgressDTO result = activityProgressService.save(activityProgressDTO);
        return ResponseEntity.created(new URI("/api/activity/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /activity} : Updates an existing activityProgressDTO.
     *
     * @param activityProgressDTO the activityProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated activityProgressDTO,
     * or with status {@code 400 (Bad Request)} if the activityProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the activityProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/activity/progress")
    public ResponseEntity<ActivityProgressDTO> updateActivity(@RequestBody ActivityProgressDTO activityProgressDTO) throws URISyntaxException {
        log.debug("REST request to update activity : {}", activityProgressDTO);
        if (activityProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActivityProgressDTO result = activityProgressService.save(activityProgressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, activityProgressDTO.getId().toString()))
            .body(result);
    }

//    /**
//     * {@code GET  /activity} : get all the activity Progress.
//     *
//     * @param pageable the pagination information.
//     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of activity in body.
//     */
//    @GetMapping("/activity")
//    public ResponseEntity<List<ActivityProgressDTO>> getAllActivities(Pageable pageable) {
//        log.debug("REST request to get a page of activity");
//        Page<ActivityProgressDTO> page = activityProgressService.findAll(pageable);
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
//        return ResponseEntity.ok().headers(headers).body(page.getContent());
//    }

}
