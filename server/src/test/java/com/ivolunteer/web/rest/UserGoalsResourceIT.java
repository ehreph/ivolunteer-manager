package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserGoals;
import com.ivolunteer.repository.UserGoalsRepository;
import com.ivolunteer.user.UserGoalsService;
import com.ivolunteer.user.dto.UserGoalsDTO;
import com.ivolunteer.service.mapper.UserGoalsMapper;

import com.ivolunteer.user.UserGoalsResource;
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
@WithMockUser
public class UserGoalsResourceIT {

    private static final Long DEFAULT_USER_ID = 4L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Double DEFAULT_CURRENT_VALUE = 1D;
    private static final Double UPDATED_CURRENT_VALUE = 2D;

    @Autowired
    private UserGoalsRepository userGoalsRepository;

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
        userGoals.setFinished(false);
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
            .user(new User(UPDATED_USER_ID));
        return userGoals;
    }

    @BeforeEach
    public void initTest() {
        userGoals = createEntity(em);
    }


    @Test
    @Transactional
    public void deleteUserGoals() throws Exception {
        // Initialize the database
        userGoalsRepository.saveAndFlush(userGoals);
        int databaseSizeBeforeDelete = userGoalsRepository.findAll().size();

        // Delete the userGoals
        restUserGoalsMockMvc.perform(delete("/api/user-goals/{id}", userGoals.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeDelete - 1);
    }


    @Test
    @Transactional
    public void deleteFinishedUserGoals() throws Exception {

        // Initialize the database
        userGoals.setFinished(true);
        userGoalsRepository.saveAndFlush(userGoals);
        int databaseSizeBeforeDelete = userGoalsRepository.findAll().size();

        // try deleting the finished userGoal
        restUserGoalsMockMvc.perform(delete("/api/user-goals/{id}", userGoals.getId())
                .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isInternalServerError());

        // Validate the database contains same item size
        List<UserGoals> userGoalsList = userGoalsRepository.findAll();
        assertThat(userGoalsList).hasSize(databaseSizeBeforeDelete);
    }



}
