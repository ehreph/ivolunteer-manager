package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.repository.CompetenceRepository;
import com.ivolunteer.service.CompetenceService;
import com.ivolunteer.service.dto.CompetenceDTO;
import com.ivolunteer.service.mapper.CompetenceMapper;

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

/**
 * Integration tests for the {@link CompetenceResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CompetenceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_MAX_LEVEL = 1D;
    private static final Double UPDATED_MAX_LEVEL = 2D;

    @Autowired
    private CompetenceRepository competenceRepository;

    @Autowired
    private CompetenceMapper competenceMapper;

    @Autowired
    private CompetenceService competenceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompetenceMockMvc;

    private Competence competence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competence createEntity(EntityManager em) {
        Competence competence = new Competence()
            .maxLevel(DEFAULT_MAX_LEVEL)
            .name(DEFAULT_NAME);
        return competence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competence createUpdatedEntity(EntityManager em) {
        Competence competence = new Competence()
            .name(UPDATED_NAME)
            .maxLevel(UPDATED_MAX_LEVEL);
        return competence;
    }

    @BeforeEach
    public void initTest() {
        competence = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetence() throws Exception {
        int databaseSizeBeforeCreate = competenceRepository.findAll().size();
        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);
        restCompetenceMockMvc.perform(post("/api/competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate + 1);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetence.getMaxLevel()).isEqualTo(DEFAULT_MAX_LEVEL);
    }

    @Test
    @Transactional
    public void createCompetenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competenceRepository.findAll().size();

        // Create the Competence with an existing ID
        competence.setId(1L);
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetenceMockMvc.perform(post("/api/competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompetences() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);

        // Get all the competenceList
        restCompetenceMockMvc.perform(get("/api/competences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competence.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].maxLevel").value(hasItem(DEFAULT_MAX_LEVEL.doubleValue())));
    }

    @Test
    @Transactional
    public void getCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);

        // Get the competence
        restCompetenceMockMvc.perform(get("/api/competences/{id}", competence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(competence.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.maxLevel").value(DEFAULT_MAX_LEVEL.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingCompetence() throws Exception {
        // Get the competence
        restCompetenceMockMvc.perform(get("/api/competences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);

        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Update the competence
        Competence updatedCompetence = competenceRepository.findById(competence.getId()).get();
        // Disconnect from session so that the updates on updatedCompetence are not directly saved in db
        em.detach(updatedCompetence);
        updatedCompetence
            .name(UPDATED_NAME)
            .maxLevel(UPDATED_MAX_LEVEL);
        CompetenceDTO competenceDTO = competenceMapper.toDto(updatedCompetence);

        restCompetenceMockMvc.perform(put("/api/competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isOk());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
        Competence testCompetence = competenceList.get(competenceList.size() - 1);
        assertThat(testCompetence.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetence.getMaxLevel()).isEqualTo(UPDATED_MAX_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetence() throws Exception {
        int databaseSizeBeforeUpdate = competenceRepository.findAll().size();

        // Create the Competence
        CompetenceDTO competenceDTO = competenceMapper.toDto(competence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetenceMockMvc.perform(put("/api/competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(competenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Competence in the database
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetence() throws Exception {
        // Initialize the database
        competenceRepository.saveAndFlush(competence);

        int databaseSizeBeforeDelete = competenceRepository.findAll().size();

        // Delete the competence
        restCompetenceMockMvc.perform(delete("/api/competences/{id}", competence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competence> competenceList = competenceRepository.findAll();
        assertThat(competenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
