package com.ivolunteer.web.rest;

import com.ivolunteer.service.InstitutionService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.InstitutionDTO;

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
 * REST controller for managing {@link com.ivolunteer.domain.Institution}.
 */
@RestController
@RequestMapping("/api")
public class InstitutionResource {

    private final Logger log = LoggerFactory.getLogger(InstitutionResource.class);

    private static final String ENTITY_NAME = "institution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstitutionService institutionService;

    public InstitutionResource(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    /**
     * {@code POST  /institutions} : Create a new institution.
     *
     * @param institutionDTO the institutionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institutionDTO, or with status {@code 400 (Bad Request)} if the institution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institutions")
    public ResponseEntity<InstitutionDTO> createInstitution(@RequestBody InstitutionDTO institutionDTO) throws URISyntaxException {
        log.debug("REST request to save Institution : {}", institutionDTO);
        if (institutionDTO.getId() != null) {
            throw new BadRequestAlertException("A new institution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstitutionDTO result = institutionService.save(institutionDTO);
        return ResponseEntity.created(new URI("/api/institutions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institutions} : Updates an existing institution.
     *
     * @param institutionDTO the institutionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institutionDTO,
     * or with status {@code 400 (Bad Request)} if the institutionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the institutionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institutions")
    public ResponseEntity<InstitutionDTO> updateInstitution(@RequestBody InstitutionDTO institutionDTO) throws URISyntaxException {
        log.debug("REST request to update Institution : {}", institutionDTO);
        if (institutionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InstitutionDTO result = institutionService.save(institutionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institutionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /institutions} : get all the institutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institutions in body.
     */
    @GetMapping("/institutions")
    public ResponseEntity<List<InstitutionDTO>> getAllInstitutions(Pageable pageable) {
        log.debug("REST request to get a page of Institutions");
        Page<InstitutionDTO> page = institutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /institutions/:id} : get the "id" institution.
     *
     * @param id the id of the institutionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institutionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institutions/{id}")
    public ResponseEntity<InstitutionDTO> getInstitution(@PathVariable Long id) {
        log.debug("REST request to get Institution : {}", id);
        Optional<InstitutionDTO> institutionDTO = institutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(institutionDTO);
    }

    /**
     * {@code DELETE  /institutions/:id} : delete the "id" institution.
     *
     * @param id the id of the institutionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institutions/{id}")
    public ResponseEntity<Void> deleteInstitution(@PathVariable Long id) {
        log.debug("REST request to delete Institution : {}", id);
        institutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
