package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.Goal;
import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserGoals;
import com.ivolunteer.repository.GoalRepository;
import com.ivolunteer.repository.UserGoalsRepository;
import com.ivolunteer.service.mapper.UserGoalsMapper;
import com.ivolunteer.user.UserGoalsResource;
import com.ivolunteer.user.UserGoalsService;
import com.ivolunteer.user.dto.UserGoalsDTO;
import com.ivolunteer.user.exception.UserGoalsServiceException;
import org.junit.jupiter.api.Assertions;
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
 * Integration tests for the {@link UserGoalsResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser(authorities = {"ROLE_ADMIN"})
public class AdminUserGoalsResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Double DEFAULT_CURRENT_VALUE = 1D;
    private static final Double UPDATED_CURRENT_VALUE = 2D;

    @Autowired
    private UserGoalsRepository userGoalsRepository;

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserGoalsMapper userGoalsMapper;

    @Autowired
    private UserGoalsService userGoalsService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserGoalsMockMvc;

    private UserGoals userGoals;

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGoals createEntity(EntityManager em) {
        UserGoals userGoals = new UserGoals()
            .user(new User(DEFAULT_USER_ID));
        return userGoals;
    }

    /**
     * Create an updated entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGoals createUpdatedEntity(EntityManager em) {
        UserGoals userGoals = new UserGoals()
            .user(new User(DEFAULT_USER_ID));
        userGoals.setFinished(false);
        return userGoals;
    }

    @BeforeEach
    public void initTest() {
        userGoals = createEntity(em);
    }

    @Test
    @Transactional
    public void assignGoalToUser() throws Exception {
        Goal goal = new Goal();
        goal.setName("Test goal");
        goal.setIsPersonal(false);
        goalRepository.saveAndFlush(goal);

        userGoals.setGoal(goal);


        int databaseSizeBeforeCreate = userGoalsRepository.findAll().size();
        // Create the UserGoals
        UserGoalsDTO userGoalsDTO = userGoalsMapper.toDto(userGoals);
        restUserGoalsMockMvc.perform(post("/api/user-goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(userGoalsDTO)))
            .andExpect(status().isCreated());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeCreate + 1);
        UserGoals testUserGoals = userGoalsList.get(userGoalsList.size() - 1);
        assertThat(testUserGoals.getUser().getId()).isEqualTo(DEFAULT_USER_ID);
    }


    @Test
    @Transactional
    public void assignGoalToUser_UserGoalServiceException() throws Exception {
            // Create the UserGoals
            UserGoalsDTO userGoalsDTO = userGoalsMapper.toDto(userGoals);
            restUserGoalsMockMvc.perform(post("/api/user-goals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalsDTO)))
                .andExpect(status().isInternalServerError());

    }


    @Test
    @Transactional
    public void createUserGoalsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGoalsRepository.findAll().size();

        // Create the UserGoals with an existing ID
        userGoals.setId(1L);
        UserGoalsDTO userGoalsDTO = userGoalsMapper.toDto(userGoals);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGoalsMockMvc.perform(post("/api/user-goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(userGoalsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get all the userGoalsList
        restUserGoalsMockMvc.perform(get("/api/user-goals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGoals.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);

        // Get the userGoals
        restUserGoalsMockMvc.perform(get("/api/user-goals/{id}", userGoals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userGoals.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserGoals() throws Exception {
        // Get the userGoals
        restUserGoalsMockMvc.perform(get("/api/user-goals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    public void updateUserGoals() throws Exception {


        // Initialize the database
        Goal goal = new Goal();
        goal.setName("Test goal");
        goal.setIsPersonal(false);
        goalRepository.saveAndFlush(goal);
        userGoals.setGoal(goal);
        userGoalsRepository.saveAndFlush(userGoals);

        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();

        // Update the userGoals
        UserGoals updatedUserGoals = userGoalsRepository.findById(userGoals.getId()).get();
        // Disconnect from session so that the updates on updatedUserGoals are not directly saved in db
        em.detach(updatedUserGoals);
        updatedUserGoals
            .user(new User(UPDATED_USER_ID));
        UserGoalsDTO userGoalsDTO = userGoalsMapper.toDto(updatedUserGoals);

        restUserGoalsMockMvc.perform(put("/api/user-goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(userGoalsDTO)))
            .andExpect(status().isOk());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
        UserGoals testUserGoals = userGoalsList.get(userGoalsList.size() - 1);
        assertThat(testUserGoals.getUser().getId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateUserGoals_UserGoalsServiceException() throws Exception {
            // Initialize the database
            userGoalsRepository.saveAndFlush(userGoals);

            // Update the userGoals
            UserGoals updatedUserGoals = userGoalsRepository.findById(userGoals.getId()).get();
            // Disconnect from session so that the updates on updatedUserGoals are not directly saved in db
            em.detach(updatedUserGoals);
            updatedUserGoals
                .user(new User(UPDATED_USER_ID));
            UserGoalsDTO userGoalsDTO = userGoalsMapper.toDto(updatedUserGoals);

            restUserGoalsMockMvc.perform(put("/api/user-goals")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userGoalsDTO)))
                .andExpect(status().isInternalServerError());

    }

    @Test
    @Transactional
    public void updateNonExistingUserGoals() throws Exception {
        int databaseSizeBeforeUpdate = userGoalsRepository.findAll().size();

        // Create the UserGoals
        UserGoalsDTO userGoalsDTO = userGoalsMapper.toDto(userGoals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGoalsMockMvc.perform(put("/api/user-goals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtil.convertObjectToJsonBytes(userGoalsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserGoals in the database
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

}
