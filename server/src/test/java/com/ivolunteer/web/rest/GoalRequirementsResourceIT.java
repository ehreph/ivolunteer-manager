package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.BaseData;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.domain.GoalRequirements;
import com.ivolunteer.repository.GoalRequirementsRepository;
import com.ivolunteer.service.GoalRequirementsService;
import com.ivolunteer.service.dto.GoalRequirementsDTO;
import com.ivolunteer.service.mapper.GoalRequirementsMapper;

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
import com.ivolunteer.domain.enumeration.RuleOperator;
/**
 * Integration tests for the {@link GoalRequirementsResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoalRequirementsResourceIT {

    private static final GlobalType DEFAULT_TYPE = GlobalType.GOAL;
    private static final GlobalType UPDATED_TYPE = GlobalType.COMPETENCE;

    private static final Long DEFAULT_GENERAL_ID = 1l;
    private static final Long UPDATED_GENERAL_ID = 1l;

    private static final RuleOperator DEFAULT_OPERATOR = RuleOperator.BIGGER_THAN;
    private static final RuleOperator UPDATED_OPERATOR = RuleOperator.BIGGER;

    private static final Double DEFAULT_VALUE = 1D;
    private static final Double UPDATED_VALUE = 2D;
    private static final String DEFAULT_COMP_NAME = "comp";
    private static final String UPDATED_COMP_NAME = "updatedComp";


    @Autowired
    private GoalRequirementsRepository goalRequirementsRepository;

    @Autowired
    private GoalRequirementsMapper goalRequirementsMapper;

    @Autowired
    private GoalRequirementsService goalRequirementsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoalRequirementsMockMvc;

    private GoalRequirements goalRequirements;
    private static Competence defaultComp;
    private static Competence updatedComp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalRequirements createEntity(EntityManager em) {


        GoalRequirements goalRequirements = new GoalRequirements()
            .type(DEFAULT_TYPE)
            .operator(DEFAULT_OPERATOR)
            .value(DEFAULT_VALUE);
        goalRequirements.setData(defaultComp);
        return goalRequirements;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoalRequirements createUpdatedEntity(EntityManager em) {
        GoalRequirements goalRequirements = new GoalRequirements()
            .type(UPDATED_TYPE)
            .operator(UPDATED_OPERATOR)
            .value(UPDATED_VALUE);
        goalRequirements.setData(updatedComp);
        return goalRequirements;
    }

    @BeforeEach
    public void initTest() {
        defaultComp = new Competence();
        defaultComp.setName(DEFAULT_COMP_NAME);
        em.persist(defaultComp);
        updatedComp = new Competence();
        defaultComp.setName(UPDATED_COMP_NAME);
        em.persist(updatedComp);

        goalRequirements = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoalRequirements() throws Exception {
        int databaseSizeBeforeCreate = goalRequirementsRepository.findAll().size();
        // Create the GoalRequirements
        GoalRequirementsDTO goalRequirementsDTO = goalRequirementsMapper.toDto(goalRequirements);
        restGoalRequirementsMockMvc.perform(post("/api/goal-requirements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalRequirementsDTO)))
            .andExpect(status().isCreated());

        // Validate the GoalRequirements in the database
        List<GoalRequirements> goalRequirementsList = goalRequirementsRepository.findAll();
        assertThat(goalRequirementsList).hasSize(databaseSizeBeforeCreate + 1);
        GoalRequirements testGoalRequirements = goalRequirementsList.get(goalRequirementsList.size() - 1);
        assertThat(testGoalRequirements.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGoalRequirements.getData().getId()).isEqualTo(defaultComp.getId());
        assertThat(testGoalRequirements.getOperator()).isEqualTo(DEFAULT_OPERATOR);
        assertThat(testGoalRequirements.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createGoalRequirementsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = goalRequirementsRepository.findAll().size();

        // Create the GoalRequirements with an existing ID
        goalRequirements.setId(1L);
        GoalRequirementsDTO goalRequirementsDTO = goalRequirementsMapper.toDto(goalRequirements);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoalRequirementsMockMvc.perform(post("/api/goal-requirements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalRequirementsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoalRequirements in the database
        List<GoalRequirements> goalRequirementsList = goalRequirementsRepository.findAll();
        assertThat(goalRequirementsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGoalRequirements() throws Exception {
        // Initialize the database
        goalRequirementsRepository.saveAndFlush(goalRequirements);

        // Get all the goalRequirementsList
        restGoalRequirementsMockMvc.perform(get("/api/goal-requirements?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goalRequirements.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].generalId").value(hasItem(goalRequirements.getData().getId().intValue())))
            .andExpect(jsonPath("$.[*].operator").value(hasItem(DEFAULT_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.doubleValue())));
    }

    @Test
    @Transactional
    public void getGoalRequirements() throws Exception {
        // Initialize the database
        goalRequirementsRepository.saveAndFlush(goalRequirements);

        // Get the goalRequirements
        restGoalRequirementsMockMvc.perform(get("/api/goal-requirements/{id}", goalRequirements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goalRequirements.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.generalId").value(defaultComp.getId().toString()))
            .andExpect(jsonPath("$.operator").value(DEFAULT_OPERATOR.toString()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingGoalRequirements() throws Exception {
        // Get the goalRequirements
        restGoalRequirementsMockMvc.perform(get("/api/goal-requirements/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGoalRequirements() throws Exception {
        // Initialize the database
        goalRequirementsRepository.saveAndFlush(goalRequirements);

        int databaseSizeBeforeUpdate = goalRequirementsRepository.findAll().size();

        // Update the goalRequirements
        GoalRequirements updatedGoalRequirements = goalRequirementsRepository.findById(goalRequirements.getId()).get();
        // Disconnect from session so that the updates on updatedGoalRequirements are not directly saved in db
        em.detach(updatedGoalRequirements);
        updatedGoalRequirements
            .type(UPDATED_TYPE)
            .operator(UPDATED_OPERATOR)
            .value(UPDATED_VALUE);
        updatedGoalRequirements.setData(updatedComp);

        GoalRequirementsDTO goalRequirementsDTO = goalRequirementsMapper.toDto(updatedGoalRequirements);

        restGoalRequirementsMockMvc.perform(put("/api/goal-requirements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalRequirementsDTO)))
            .andExpect(status().isOk());

        // Validate the GoalRequirements in the database
        List<GoalRequirements> goalRequirementsList = goalRequirementsRepository.findAll();
        assertThat(goalRequirementsList).hasSize(databaseSizeBeforeUpdate);
        GoalRequirements testGoalRequirements = goalRequirementsList.get(goalRequirementsList.size() - 1);
        assertThat(testGoalRequirements.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGoalRequirements.getData().getId()).isEqualTo(updatedComp.getId());
        assertThat(testGoalRequirements.getOperator()).isEqualTo(UPDATED_OPERATOR);
        assertThat(testGoalRequirements.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingGoalRequirements() throws Exception {
        int databaseSizeBeforeUpdate = goalRequirementsRepository.findAll().size();

        // Create the GoalRequirements
        GoalRequirementsDTO goalRequirementsDTO = goalRequirementsMapper.toDto(goalRequirements);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoalRequirementsMockMvc.perform(put("/api/goal-requirements")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(goalRequirementsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoalRequirements in the database
        List<GoalRequirements> goalRequirementsList = goalRequirementsRepository.findAll();
        assertThat(goalRequirementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoalRequirements() throws Exception {
        // Initialize the database
        goalRequirementsRepository.saveAndFlush(goalRequirements);

        int databaseSizeBeforeDelete = goalRequirementsRepository.findAll().size();

        // Delete the goalRequirements
        restGoalRequirementsMockMvc.perform(delete("/api/goal-requirements/{id}", goalRequirements.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoalRequirements> goalRequirementsList = goalRequirementsRepository.findAll();
        assertThat(goalRequirementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
