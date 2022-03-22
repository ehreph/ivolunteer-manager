package com.ivolunteer.web.rest;

import com.ivolunteer.service.UserBadgeService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.UserBadgeDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.ivolunteer.domain.UserBadge}.
 */
@RestController
@RequestMapping("/api")
public class UserBadgeResource {

    private final Logger log = LoggerFactory.getLogger(UserBadgeResource.class);

    private static final String ENTITY_NAME = "userBadge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserBadgeService userBadgeService;

    public UserBadgeResource(UserBadgeService userBadgeService) {
        this.userBadgeService = userBadgeService;
    }

    /**
     * {@code POST  /user-badges} : Create a new userBadge.
     *
     * @param userBadgeDTO the userBadgeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userBadgeDTO, or with status {@code 400 (Bad Request)} if the userBadge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-badges")
    public ResponseEntity<UserBadgeDTO> createUserBadge(@RequestBody UserBadgeDTO userBadgeDTO) throws URISyntaxException {
        log.debug("REST request to save UserBadge : {}", userBadgeDTO);
        if (userBadgeDTO.getId() != null) {
            throw new BadRequestAlertException("A new userBadge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserBadgeDTO result = userBadgeService.save(userBadgeDTO);
        return ResponseEntity.created(new URI("/api/user-badges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-badges} : Updates an existing userBadge.
     *
     * @param userBadgeDTO the userBadgeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userBadgeDTO,
     * or with status {@code 400 (Bad Request)} if the userBadgeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userBadgeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-badges")
    public ResponseEntity<UserBadgeDTO> updateUserBadge(@RequestBody UserBadgeDTO userBadgeDTO) throws URISyntaxException {
        log.debug("REST request to update UserBadge : {}", userBadgeDTO);
        if (userBadgeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserBadgeDTO result = userBadgeService.save(userBadgeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userBadgeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-badges} : get all the userBadges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBadges in body.
     */
    @GetMapping("/user-badges")
    public ResponseEntity<List<UserBadgeDTO>> getAllUserBadges(Pageable pageable) {
        log.debug("REST request to get a page of UserBadges");
        Page<UserBadgeDTO> page = userBadgeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-badges} : get all the badges of user.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userBadges in body.
     */
    @GetMapping("/user-badges/user/{userId}")
    public ResponseEntity<List<UserBadgeDTO>> getAllUserBadgesByUserId(@PathVariable Long userId, Pageable pageable) {
        log.debug("REST request to get a page of User Badge");
        Page<UserBadgeDTO> page = userBadgeService.findAllByUserId(userId, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



    /**
     * {@code GET  /user-badges/:id} : get the "id" userBadge.
     *
     * @param id the id of the userBadgeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userBadgeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-badges/{id}")
    public ResponseEntity<UserBadgeDTO> getUserBadge(@PathVariable Long id) {
        log.debug("REST request to get UserBadge : {}", id);
        Optional<UserBadgeDTO> userBadgeDTO = userBadgeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userBadgeDTO);
    }

    /**
     * {@code DELETE  /user-badges/:id} : delete the "id" userBadge.
     *
     * @param id the id of the userBadgeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-badges/{id}")
    public ResponseEntity<Void> deleteUserBadge(@PathVariable Long id) {
        log.debug("REST request to delete UserBadge : {}", id);
        userBadgeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
