package com.ivolunteer.web.rest;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.service.BadgeService;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.BadgeDTO;

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
 * REST controller for managing {@link com.ivolunteer.domain.Badge}.
 */
@RestController
@RequestMapping("/api")
public class BadgeResource {

    private final Logger log = LoggerFactory.getLogger(BadgeResource.class);

    private static final String ENTITY_NAME = "badge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BadgeService badgeService;

    public BadgeResource(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    /**
     * {@code POST  /badges} : Create a new badge.
     *
     * @param badgeDTO the badgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new badgeDTO, or with status {@code 400 (Bad Request)} if the badge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/badges")
    public ResponseEntity<BadgeDTO> createBadge(@RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        log.debug("REST request to save Badge : {}", badgeDTO);
        if (badgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new badge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BadgeDTO result = badgeService.save(badgeDTO);
        return ResponseEntity.created(new URI("/api/badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /badges} : Updates an existing badge.
     *
     * @param badgeDTO the badgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated badgeDTO,
     * or with status {@code 400 (Bad Request)} if the badgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the badgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/badges")
    public ResponseEntity<BadgeDTO> updateBadge(@RequestBody BadgeDTO badgeDTO) throws URISyntaxException {
        log.debug("REST request to update Badge : {}", badgeDTO);
        if (badgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BadgeDTO result = badgeService.save(badgeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, badgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /badges} : get all the badges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
     */
    @GetMapping("/badges")
    public ResponseEntity<List<BadgeDTO>> getAllBadges(Pageable pageable) {
        log.debug("REST request to get a page of Badges");
        Page<BadgeDTO> page = badgeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /badges/:id} : get the "id" badge.
     *
     * @param id the id of the badgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the badgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/badges/{id}")
    public ResponseEntity<BadgeDTO> getBadge(@PathVariable Long id) {
        log.debug("REST request to get Badge : {}", id);
        Optional<BadgeDTO> badgeDTO = badgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(badgeDTO);
    }

    /**
     * {@code DELETE  /badges/:id} : delete the "id" badge.
     *
     * @param id the id of the badgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/badges/{id}")
    public ResponseEntity<Void> deleteBadge(@PathVariable Long id) {
        log.debug("REST request to delete Badge : {}", id);
        badgeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

  /**
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of badges in body.
   */
  @GetMapping("/badges/user/{userId}")
  @Secured(AuthoritiesConstants.ADMIN)
  public ResponseEntity<List<BadgeDTO>> getNewBadgesForUser(@PathVariable("userId") Long userId) {
    log.debug("REST request to get not assigned badges for user");
    List<BadgeDTO> list = badgeService.findNewBadgesForUser(userId);
    return ResponseEntity.ok().body(list);
  }


}

