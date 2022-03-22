package com.ivolunteer.web.rest;

import com.ivolunteer.service.UserCompetenceService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.UserCompetenceDTO;

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
 * REST controller for managing {@link com.ivolunteer.domain.UserCompetence}.
 */
@RestController
@RequestMapping("/api")
public class UserCompetenceResource {

    private final Logger log = LoggerFactory.getLogger(UserCompetenceResource.class);

    private static final String ENTITY_NAME = "userCompetence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserCompetenceService userCompetenceService;

    public UserCompetenceResource(UserCompetenceService userCompetenceService) {
        this.userCompetenceService = userCompetenceService;
    }

    /**
     * {@code POST  /user-competences} : Create a new userCompetence.
     *
     * @param userCompetenceDTO the userCompetenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userCompetenceDTO, or with status {@code 400 (Bad Request)} if the userCompetence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-competences")
    public ResponseEntity<UserCompetenceDTO> createUserCompetence(@RequestBody UserCompetenceDTO userCompetenceDTO) throws URISyntaxException {
        log.debug("REST request to save UserCompetence : {}", userCompetenceDTO);
        if (userCompetenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new userCompetence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserCompetenceDTO result = userCompetenceService.save(userCompetenceDTO);
        return ResponseEntity.created(new URI("/api/user-competences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-competences} : Updates an existing userCompetence.
     *
     * @param userCompetenceDTO the userCompetenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userCompetenceDTO,
     * or with status {@code 400 (Bad Request)} if the userCompetenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userCompetenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-competences")
    public ResponseEntity<UserCompetenceDTO> updateUserCompetence(@RequestBody UserCompetenceDTO userCompetenceDTO) throws URISyntaxException {
        log.debug("REST request to update UserCompetence : {}", userCompetenceDTO);
        if (userCompetenceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserCompetenceDTO result = userCompetenceService.save(userCompetenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userCompetenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-competences} : get all the userCompetences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCompetences in body.
     */
    @GetMapping("/user-competences")
    public ResponseEntity<List<UserCompetenceDTO>> getAllUserCompetences(Pageable pageable) {
        log.debug("REST request to get a page of UserCompetences");
        Page<UserCompetenceDTO> page = userCompetenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }



    /**
     * {@code GET  /user-competences} : get all the userCompetences of User.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userCompetences in body.
     */
    @GetMapping("/user-competences/user/{userId}")
    public ResponseEntity<List<UserCompetenceDTO>> getAllUserCompetencesByUserId(@PathVariable Long userId, Pageable pageable) {
        log.debug("REST request to get a page of UserCompetences");
        Page<UserCompetenceDTO> page = userCompetenceService.findAllByUserId(userId,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }


    /**
     * {@code GET  /user-competences/:id} : get the "id" userCompetence.
     *
     * @param id the id of the userCompetenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userCompetenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-competences/{id}")
    public ResponseEntity<UserCompetenceDTO> getUserCompetence(@PathVariable Long id) {
        log.debug("REST request to get UserCompetence : {}", id);
        Optional<UserCompetenceDTO> userCompetenceDTO = userCompetenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userCompetenceDTO);
    }

    /**
     * {@code DELETE  /user-competences/:id} : delete the "id" userCompetence.
     *
     * @param id the id of the userCompetenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-competences/{id}")
    public ResponseEntity<Void> deleteUserCompetence(@PathVariable Long id) {
        log.debug("REST request to delete UserCompetence : {}", id);
        userCompetenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
