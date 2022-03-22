package com.ivolunteer.web.rest;

import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.service.RuleAttributeService;
import com.ivolunteer.web.rest.errors.BadRequestAlertException;
import com.ivolunteer.service.dto.RuleAttributeDTO;

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
 * REST controller for managing {@link com.ivolunteer.domain.RuleAttribute}.
 */
@RestController
@RequestMapping("/api")
public class RuleAttributeResource {

  private final Logger log = LoggerFactory.getLogger(RuleAttributeResource.class);

  private static final String ENTITY_NAME = "ruleAttribute";

  @Value("${jhipster.clientApp.name}")
  private String applicationName;

  private final RuleAttributeService ruleAttributeService;

  public RuleAttributeResource(RuleAttributeService ruleAttributeService) {
    this.ruleAttributeService = ruleAttributeService;
  }

  /**
   * {@code POST  /rule-attributes} : Create a new ruleAttribute.
   *
   * @param ruleAttributeDTO the ruleAttributeDTO to create.
   * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ruleAttributeDTO, or with status {@code 400 (Bad Request)} if the ruleAttribute has already an ID.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PostMapping("/rule-attributes")
  public ResponseEntity<RuleAttributeDTO> createRuleAttribute(
    @RequestBody RuleAttributeDTO ruleAttributeDTO) throws URISyntaxException {
    log.debug("REST request to save RuleAttribute : {}", ruleAttributeDTO);
    if (ruleAttributeDTO.getId() != null) {
      throw new BadRequestAlertException("A new ruleAttribute cannot already have an ID", ENTITY_NAME, "idexists");
    }
    RuleAttributeDTO result = ruleAttributeService.save(ruleAttributeDTO);
    return ResponseEntity.created(new URI("/api/rule-attributes/" + result.getId()))
      .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
      .body(result);
  }

  /**
   * {@code PUT  /rule-attributes} : Updates an existing ruleAttribute.
   *
   * @param ruleAttributeDTO the ruleAttributeDTO to update.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ruleAttributeDTO,
   * or with status {@code 400 (Bad Request)} if the ruleAttributeDTO is not valid,
   * or with status {@code 500 (Internal Server Error)} if the ruleAttributeDTO couldn't be updated.
   * @throws URISyntaxException if the Location URI syntax is incorrect.
   */
  @PutMapping("/rule-attributes")
  public ResponseEntity<RuleAttributeDTO> updateRuleAttribute(
    @RequestBody RuleAttributeDTO ruleAttributeDTO) throws URISyntaxException {
    log.debug("REST request to update RuleAttribute : {}", ruleAttributeDTO);
    if (ruleAttributeDTO.getId() == null) {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    RuleAttributeDTO result = ruleAttributeService.save(ruleAttributeDTO);
    return ResponseEntity.ok()
      .headers(
        HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ruleAttributeDTO.getId().toString()))
      .body(result);
  }

  /**
   * {@code GET  /rule-attributes} : get all the ruleAttributes.
   *
   * @param pageable the pagination information.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ruleAttributes in body.
   */
  @GetMapping("/rule-attributes")
  public ResponseEntity<List<RuleAttributeDTO>> getAllRuleAttributes(Pageable pageable,
                                                                     @RequestParam(required = false) GlobalType filter) {
    Page<RuleAttributeDTO> page;
    log.debug("REST request to get a page of RuleAttributes");
    if (filter != null) {
      page = ruleAttributeService.findAllOfType(pageable, filter);
    } else {
      page = ruleAttributeService.findAll(pageable);
    }
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(),
      page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }


  /**
   * {@code GET  /rule-attributes/:id} : get the "id" ruleAttribute.
   *
   * @param id the id of the ruleAttributeDTO to retrieve.
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ruleAttributeDTO, or with status {@code 404 (Not Found)}.
   */
  @GetMapping("/rule-attributes/{id}")
  public ResponseEntity<RuleAttributeDTO> getRuleAttribute(@PathVariable Long id) {
    log.debug("REST request to get RuleAttribute : {}", id);
    Optional<RuleAttributeDTO> ruleAttributeDTO = ruleAttributeService.findOne(id);
    return ResponseUtil.wrapOrNotFound(ruleAttributeDTO);
  }

  /**
   * {@code DELETE  /rule-attributes/:id} : delete the "id" ruleAttribute.
   *
   * @param id the id of the ruleAttributeDTO to delete.
   * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
   */
  @DeleteMapping("/rule-attributes/{id}")
  public ResponseEntity<Void> deleteRuleAttribute(@PathVariable Long id) {
    log.debug("REST request to delete RuleAttribute : {}", id);
    ruleAttributeService.delete(id);
    return ResponseEntity.noContent().headers(
      HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
  }
}
