package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.RuleAttribute;
import com.ivolunteer.repository.RuleAttributeRepository;
import com.ivolunteer.service.RuleAttributeService;
import com.ivolunteer.service.dto.RuleAttributeDTO;
import com.ivolunteer.service.mapper.RuleAttributeMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ivolunteer.domain.enumeration.GlobalType;
/**
 * Integration tests for the {@link RuleAttributeResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class RuleAttributeResourceIT {

    private static final GlobalType DEFAULT_RULE_TYPE = GlobalType.GOAL;
    private static final GlobalType UPDATED_RULE_TYPE = GlobalType.COMPETENCE;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";


    private static final String DEFAULT_UNIT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_UNIT_NAME = "BBBBBBBBBB";

    @Autowired
    private RuleAttributeRepository ruleAttributeRepository;

    @Autowired
    private RuleAttributeMapper ruleAttributeMapper;

    @Autowired
    private RuleAttributeService ruleAttributeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRuleAttributeMockMvc;

    private RuleAttribute ruleAttribute;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleAttribute createEntity(EntityManager em) {
        RuleAttribute ruleAttribute = new RuleAttribute()
            .ruleType(DEFAULT_RULE_TYPE)
            .name(DEFAULT_NAME)
            .unitName(DEFAULT_UNIT_NAME);
        return ruleAttribute;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RuleAttribute createUpdatedEntity(EntityManager em) {
        RuleAttribute ruleAttribute = new RuleAttribute()
            .ruleType(UPDATED_RULE_TYPE)
            .name(UPDATED_NAME)
            .unitName(UPDATED_UNIT_NAME);
        return ruleAttribute;
    }

    @BeforeEach
    public void initTest() {
        ruleAttribute = createEntity(em);
    }

    @Test
    @Transactional
    public void createRuleAttribute() throws Exception {
        int databaseSizeBeforeCreate = ruleAttributeRepository.findAll().size();
        // Create the RuleAttribute
        RuleAttributeDTO ruleAttributeDTO = ruleAttributeMapper.toDto(ruleAttribute);
        restRuleAttributeMockMvc.perform(post("/api/rule-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributeDTO)))
            .andExpect(status().isCreated());

        // Validate the RuleAttribute in the database
        List<RuleAttribute> ruleAttributeList = ruleAttributeRepository.findAll();
        assertThat(ruleAttributeList).hasSize(databaseSizeBeforeCreate + 1);
        RuleAttribute testRuleAttribute = ruleAttributeList.get(ruleAttributeList.size() - 1);
        assertThat(testRuleAttribute.getRuleType()).isEqualTo(DEFAULT_RULE_TYPE);
        assertThat(testRuleAttribute.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRuleAttribute.getUnitName()).isEqualTo(DEFAULT_UNIT_NAME);
    }

    @Test
    @Transactional
    public void createRuleAttributeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ruleAttributeRepository.findAll().size();

        // Create the RuleAttribute with an existing ID
        ruleAttribute.setId(1L);
        RuleAttributeDTO ruleAttributeDTO = ruleAttributeMapper.toDto(ruleAttribute);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRuleAttributeMockMvc.perform(post("/api/rule-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuleAttribute in the database
        List<RuleAttribute> ruleAttributeList = ruleAttributeRepository.findAll();
        assertThat(ruleAttributeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRuleAttributes() throws Exception {
        // Initialize the database
        ruleAttributeRepository.saveAndFlush(ruleAttribute);

        // Get all the ruleAttributeList
        restRuleAttributeMockMvc.perform(get("/api/rule-attributes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ruleAttribute.getId().intValue())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].unitName").value(hasItem(DEFAULT_UNIT_NAME)));
    }

    @Test
    @Transactional
    public void getRuleAttribute() throws Exception {
        // Initialize the database
        ruleAttributeRepository.saveAndFlush(ruleAttribute);

        // Get the ruleAttribute
        restRuleAttributeMockMvc.perform(get("/api/rule-attributes/{id}", ruleAttribute.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ruleAttribute.getId().intValue()))
            .andExpect(jsonPath("$.ruleType").value(DEFAULT_RULE_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.unitName").value(DEFAULT_UNIT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingRuleAttribute() throws Exception {
        // Get the ruleAttribute
        restRuleAttributeMockMvc.perform(get("/api/rule-attributes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRuleAttribute() throws Exception {
        // Initialize the database
        ruleAttributeRepository.saveAndFlush(ruleAttribute);

        int databaseSizeBeforeUpdate = ruleAttributeRepository.findAll().size();

        // Update the ruleAttribute
        RuleAttribute updatedRuleAttribute = ruleAttributeRepository.findById(ruleAttribute.getId()).get();
        // Disconnect from session so that the updates on updatedRuleAttribute are not directly saved in db
        em.detach(updatedRuleAttribute);
        updatedRuleAttribute
            .ruleType(UPDATED_RULE_TYPE)
            .name(UPDATED_NAME)
            .unitName(UPDATED_UNIT_NAME);
        RuleAttributeDTO ruleAttributeDTO = ruleAttributeMapper.toDto(updatedRuleAttribute);

        restRuleAttributeMockMvc.perform(put("/api/rule-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributeDTO)))
            .andExpect(status().isOk());

        // Validate the RuleAttribute in the database
        List<RuleAttribute> ruleAttributeList = ruleAttributeRepository.findAll();
        assertThat(ruleAttributeList).hasSize(databaseSizeBeforeUpdate);
        RuleAttribute testRuleAttribute = ruleAttributeList.get(ruleAttributeList.size() - 1);
        assertThat(testRuleAttribute.getRuleType()).isEqualTo(UPDATED_RULE_TYPE);
        assertThat(testRuleAttribute.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRuleAttribute.getUnitName()).isEqualTo(UPDATED_UNIT_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRuleAttribute() throws Exception {
        int databaseSizeBeforeUpdate = ruleAttributeRepository.findAll().size();

        // Create the RuleAttribute
        RuleAttributeDTO ruleAttributeDTO = ruleAttributeMapper.toDto(ruleAttribute);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRuleAttributeMockMvc.perform(put("/api/rule-attributes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(ruleAttributeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the RuleAttribute in the database
        List<RuleAttribute> ruleAttributeList = ruleAttributeRepository.findAll();
        assertThat(ruleAttributeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRuleAttribute() throws Exception {
        // Initialize the database
        ruleAttributeRepository.saveAndFlush(ruleAttribute);

        int databaseSizeBeforeDelete = ruleAttributeRepository.findAll().size();

        // Delete the ruleAttribute
        restRuleAttributeMockMvc.perform(delete("/api/rule-attributes/{id}", ruleAttribute.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RuleAttribute> ruleAttributeList = ruleAttributeRepository.findAll();
        assertThat(ruleAttributeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
