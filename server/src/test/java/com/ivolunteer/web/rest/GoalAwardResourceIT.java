package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.GoalAward;
import com.ivolunteer.repository.GoalAwardRepository;
import com.ivolunteer.service.GoalAwardService;
import com.ivolunteer.service.dto.GoalAwardDTO;
import com.ivolunteer.service.mapper.GoalAwardMapper;

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
 * Integration tests for the {@link GoalAwardResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoalAwardResourceIT {

    private static final GlobalType DEFAULT_AWARD_TYPE = GlobalType.GOAL;
    private static final GlobalType UPDATED_AWARD_TYPE = GlobalType.COMPETENCE;

    private static final Long DEFAULT_GENERAL_ID = 1L;
    private static final Long UPDATED_GENERAL_ID = 2L;

    private static final Double DEFAULT_INCREASE_LEVEL = 1D;
    private static final Double UPDATED_INCREASE_LEVEL = 2D;

    @Autowired
    private GoalAwardRepository goalAwardRepository;

    @Autowired
    private GoalAwardMapper goalAwardMapper;

    @Autowired
    private GoalAwardService goalAwardService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoalAwardMockMvc;

    private GoalAward goalAward;
    //TODO refactor test goalAward cannot exist without goal


//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static GoalAward createEntity(EntityManager em) {
//        GoalAward goalAward = new GoalAward()
//            .awardType(DEFAULT_AWARD_TYPE)
//            .generalId(DEFAULT_GENERAL_ID)
//            .increaseLevel(DEFAULT_INCREASE_LEVEL);
//        return goalAward;
//    }
//    /**
//     * Create an updated entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static GoalAward createUpdatedEntity(EntityManager em) {
//        GoalAward goalAward = new GoalAward()
//            .awardType(UPDATED_AWARD_TYPE)
//            .generalId(UPDATED_GENERAL_ID)
//            .increaseLevel(UPDATED_INCREASE_LEVEL);
//        return goalAward;
//    }
//
//    @BeforeEach
//    public void initTest() {
//        goalAward = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createGoalAward() throws Exception {
//        int databaseSizeBeforeCreate = goalAwardRepository.findAll().size();
//        // Create the GoalAward
//        GoalAwardDTO goalAwardDTO = goalAwardMapper.toDto(goalAward);
//        restGoalAwardMockMvc.perform(post("/api/goal-awards")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(goalAwardDTO)))
//            .andExpect(status().isCreated());
//
//        // Validate the GoalAward in the database
//        List<GoalAward> goalAwardList = goalAwardRepository.findAll();
//        assertThat(goalAwardList).hasSize(databaseSizeBeforeCreate + 1);
//        GoalAward testGoalAward = goalAwardList.get(goalAwardList.size() - 1);
//        assertThat(testGoalAward.getAwardType()).isEqualTo(DEFAULT_AWARD_TYPE);
//        assertThat(testGoalAward.getGeneralId()).isEqualTo(DEFAULT_GENERAL_ID);
//        assertThat(testGoalAward.getIncreaseLevel()).isEqualTo(DEFAULT_INCREASE_LEVEL);
//    }
//
//    @Test
//    @Transactional
//    public void createGoalAwardWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = goalAwardRepository.findAll().size();
//
//        // Create the GoalAward with an existing ID
//        goalAward.setId(1L);
//        GoalAwardDTO goalAwardDTO = goalAwardMapper.toDto(goalAward);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restGoalAwardMockMvc.perform(post("/api/goal-awards")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(goalAwardDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the GoalAward in the database
//        List<GoalAward> goalAwardList = goalAwardRepository.findAll();
//        assertThat(goalAwardList).hasSize(databaseSizeBeforeCreate);
//    }
//
//
//    @Test
//    @Transactional
//    public void getAllGoalAwards() throws Exception {
//        // Initialize the database
//        goalAwardRepository.saveAndFlush(goalAward);
//
//        // Get all the goalAwardList
//        restGoalAwardMockMvc.perform(get("/api/goal-awards?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(goalAward.getId().intValue())))
//            .andExpect(jsonPath("$.[*].awardType").value(hasItem(DEFAULT_AWARD_TYPE.toString())))
//            .andExpect(jsonPath("$.[*].generalId").value(hasItem(DEFAULT_GENERAL_ID.intValue())))
//            .andExpect(jsonPath("$.[*].increaseLevel").value(hasItem(DEFAULT_INCREASE_LEVEL.doubleValue())));
//    }
//
//    @Test
//    @Transactional
//    public void getGoalAward() throws Exception {
//        // Initialize the database
//        goalAwardRepository.saveAndFlush(goalAward);
//
//        // Get the goalAward
//        restGoalAwardMockMvc.perform(get("/api/goal-awards/{id}", goalAward.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
//            .andExpect(jsonPath("$.id").value(goalAward.getId().intValue()))
//            .andExpect(jsonPath("$.awardType").value(DEFAULT_AWARD_TYPE.toString()))
//            .andExpect(jsonPath("$.generalId").value(DEFAULT_GENERAL_ID.intValue()))
//            .andExpect(jsonPath("$.increaseLevel").value(DEFAULT_INCREASE_LEVEL.doubleValue()));
//    }
//    @Test
//    @Transactional
//    public void getNonExistingGoalAward() throws Exception {
//        // Get the goalAward
//        restGoalAwardMockMvc.perform(get("/api/goal-awards/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateGoalAward() throws Exception {
//        // Initialize the database
//        goalAwardRepository.saveAndFlush(goalAward);
//
//        int databaseSizeBeforeUpdate = goalAwardRepository.findAll().size();
//
//        // Update the goalAward
//        GoalAward updatedGoalAward = goalAwardRepository.findById(goalAward.getId()).get();
//        // Disconnect from session so that the updates on updatedGoalAward are not directly saved in db
//        em.detach(updatedGoalAward);
//        updatedGoalAward
//            .awardType(UPDATED_AWARD_TYPE)
//            .generalId(UPDATED_GENERAL_ID)
//            .increaseLevel(UPDATED_INCREASE_LEVEL);
//        GoalAwardDTO goalAwardDTO = goalAwardMapper.toDto(updatedGoalAward);
//
//        restGoalAwardMockMvc.perform(put("/api/goal-awards")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(goalAwardDTO)))
//            .andExpect(status().isOk());
//
//        // Validate the GoalAward in the database
//        List<GoalAward> goalAwardList = goalAwardRepository.findAll();
//        assertThat(goalAwardList).hasSize(databaseSizeBeforeUpdate);
//        GoalAward testGoalAward = goalAwardList.get(goalAwardList.size() - 1);
//        assertThat(testGoalAward.getAwardType()).isEqualTo(UPDATED_AWARD_TYPE);
//        assertThat(testGoalAward.getGeneralId()).isEqualTo(UPDATED_GENERAL_ID);
//        assertThat(testGoalAward.getIncreaseLevel()).isEqualTo(UPDATED_INCREASE_LEVEL);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingGoalAward() throws Exception {
//        int databaseSizeBeforeUpdate = goalAwardRepository.findAll().size();
//
//        // Create the GoalAward
//        GoalAwardDTO goalAwardDTO = goalAwardMapper.toDto(goalAward);
//
//        // If the entity doesn't have an ID, it will throw BadRequestAlertException
//        restGoalAwardMockMvc.perform(put("/api/goal-awards")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(TestUtil.convertObjectToJsonBytes(goalAwardDTO)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the GoalAward in the database
//        List<GoalAward> goalAwardList = goalAwardRepository.findAll();
//        assertThat(goalAwardList).hasSize(databaseSizeBeforeUpdate);
//    }
//
//    @Test
//    @Transactional
//    public void deleteGoalAward() throws Exception {
//        // Initialize the database
//        goalAwardRepository.saveAndFlush(goalAward);
//
//        int databaseSizeBeforeDelete = goalAwardRepository.findAll().size();
//
//        // Delete the goalAward
//        restGoalAwardMockMvc.perform(delete("/api/goal-awards/{id}", goalAward.getId())
//            .accept(MediaType.APPLICATION_JSON))
//            .andExpect(status().isNoContent());
//
//        // Validate the database contains one less item
//        List<GoalAward> goalAwardList = goalAwardRepository.findAll();
//        assertThat(goalAwardList).hasSize(databaseSizeBeforeDelete - 1);
//    }
}
