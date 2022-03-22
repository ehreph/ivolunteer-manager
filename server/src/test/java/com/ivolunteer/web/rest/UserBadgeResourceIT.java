package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.Badge;
import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserBadge;
import com.ivolunteer.repository.UserBadgeRepository;
import com.ivolunteer.service.UserBadgeService;
import com.ivolunteer.service.dto.UserBadgeDTO;
import com.ivolunteer.service.mapper.UserBadgeMapper;

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
 * Integration tests for the {@link UserBadgeResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserBadgeResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;
    private static final String DEFAULT_BADGE_NAME = "badge";
    private static final String UPDATED_BADGE_NAME = "updatedBadge";

    @Autowired
    private UserBadgeRepository userBadgeRepository;

    @Autowired
    private UserBadgeMapper userBadgeMapper;

    @Autowired
    private UserBadgeService userBadgeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserBadgeMockMvc;

    private UserBadge userBadge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBadge createEntity(EntityManager em) {
        Badge badge = new Badge();
        badge.setName(DEFAULT_BADGE_NAME);
        em.persist(badge);

        UserBadge userBadge = new UserBadge()
            .user(new User(DEFAULT_USER_ID));
        userBadge.setBadge(badge);
        return userBadge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserBadge createUpdatedEntity(EntityManager em) {
        Badge badge = new Badge();
        badge.setName(UPDATED_BADGE_NAME);
        em.persist(badge);

        UserBadge userBadge = new UserBadge()
            .user(new User(UPDATED_USER_ID));
        userBadge.setBadge(badge);
        return userBadge;
    }

    @BeforeEach
    public void initTest() {
        userBadge = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserBadge() throws Exception {
        int databaseSizeBeforeCreate = userBadgeRepository.findAll().size();
        // Create the UserBadge
        UserBadgeDTO userBadgeDTO = userBadgeMapper.toDto(userBadge);
        restUserBadgeMockMvc.perform(post("/api/user-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserBadge in the database
        List<UserBadge> userBadgeList = userBadgeRepository.findAll();
        assertThat(userBadgeList).hasSize(databaseSizeBeforeCreate + 1);
        UserBadge testUserBadge = userBadgeList.get(userBadgeList.size() - 1);
        assertThat(testUserBadge.getUser().getId()).isEqualTo(DEFAULT_USER_ID);
    }

    @Test
    @Transactional
    public void createUserBadgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userBadgeRepository.findAll().size();

        // Create the UserBadge with an existing ID
        userBadge.setId(1L);
        UserBadgeDTO userBadgeDTO = userBadgeMapper.toDto(userBadge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserBadgeMockMvc.perform(post("/api/user-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBadge in the database
        List<UserBadge> userBadgeList = userBadgeRepository.findAll();
        assertThat(userBadgeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserBadges() throws Exception {
        // Initialize the database
        userBadgeRepository.saveAndFlush(userBadge);

        // Get all the userBadgeList
        restUserBadgeMockMvc.perform(get("/api/user-badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userBadge.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserBadge() throws Exception {
        // Initialize the database
        userBadgeRepository.saveAndFlush(userBadge);

        // Get the userBadge
        restUserBadgeMockMvc.perform(get("/api/user-badges/{id}", userBadge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userBadge.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserBadge() throws Exception {
        // Get the userBadge
        restUserBadgeMockMvc.perform(get("/api/user-badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserBadge() throws Exception {
        // Initialize the database
        userBadgeRepository.saveAndFlush(userBadge);

        int databaseSizeBeforeUpdate = userBadgeRepository.findAll().size();

        // Update the userBadge
        UserBadge updatedUserBadge = userBadgeRepository.findById(userBadge.getId()).get();
        // Disconnect from session so that the updates on updatedUserBadge are not directly saved in db
        em.detach(updatedUserBadge);
        updatedUserBadge
            .user(new User(UPDATED_USER_ID));
        UserBadgeDTO userBadgeDTO = userBadgeMapper.toDto(updatedUserBadge);

        restUserBadgeMockMvc.perform(put("/api/user-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgeDTO)))
            .andExpect(status().isOk());

        // Validate the UserBadge in the database
        List<UserBadge> userBadgeList = userBadgeRepository.findAll();
        assertThat(userBadgeList).hasSize(databaseSizeBeforeUpdate);
        UserBadge testUserBadge = userBadgeList.get(userBadgeList.size() - 1);
        assertThat(testUserBadge.getUser().getId()).isEqualTo(UPDATED_USER_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserBadge() throws Exception {
        int databaseSizeBeforeUpdate = userBadgeRepository.findAll().size();

        // Create the UserBadge
        UserBadgeDTO userBadgeDTO = userBadgeMapper.toDto(userBadge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserBadgeMockMvc.perform(put("/api/user-badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userBadgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserBadge in the database
        List<UserBadge> userBadgeList = userBadgeRepository.findAll();
        assertThat(userBadgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserBadge() throws Exception {
        // Initialize the database
        userBadgeRepository.saveAndFlush(userBadge);

        int databaseSizeBeforeDelete = userBadgeRepository.findAll().size();

        // Delete the userBadge
        restUserBadgeMockMvc.perform(delete("/api/user-badges/{id}", userBadge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserBadge> userBadgeList = userBadgeRepository.findAll();
        assertThat(userBadgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
