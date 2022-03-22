package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.Competence;
import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserCompetence;
import com.ivolunteer.repository.UserCompetenceRepository;
import com.ivolunteer.service.UserCompetenceService;
import com.ivolunteer.service.dto.UserCompetenceDTO;
import com.ivolunteer.service.mapper.UserCompetenceMapper;

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
 * Integration tests for the {@link UserCompetenceResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UserCompetenceResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Double DEFAULT_USER_LEVEL = 1D;
    private static final Double UPDATED_USER_LEVEL = 2D;
    private static final String DEFAULT_COMP_NAME = "comp";
    private static final String UPDATED_COMP_NAME = "updatedComp";

    @Autowired
    private UserCompetenceRepository userCompetenceRepository;

    @Autowired
    private UserCompetenceMapper userCompetenceMapper;

    @Autowired
    private UserCompetenceService userCompetenceService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserCompetenceMockMvc;

    private UserCompetence userCompetence;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCompetence createEntity(EntityManager em) {
        Competence c = new Competence();
        c.setName(DEFAULT_COMP_NAME);
        em.persist(c);


        UserCompetence userCompetence = new UserCompetence()
            .user(new User(DEFAULT_USER_ID))
            .userLevel(DEFAULT_USER_LEVEL);
        userCompetence.setComp(c);
        return userCompetence;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCompetence createUpdatedEntity(EntityManager em) {
        Competence c = new Competence();
        c.setName(UPDATED_COMP_NAME);
        em.persist(c);


        UserCompetence userCompetence = new UserCompetence()
            .user(new User(UPDATED_USER_ID))
            .userLevel(UPDATED_USER_LEVEL);
        userCompetence.setComp(c);
        return userCompetence;
    }

    @BeforeEach
    public void initTest() {
        userCompetence = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserCompetence() throws Exception {
        int databaseSizeBeforeCreate = userCompetenceRepository.findAll().size();
        // Create the UserCompetence
        UserCompetenceDTO userCompetenceDTO = userCompetenceMapper.toDto(userCompetence);
        restUserCompetenceMockMvc.perform(post("/api/user-competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCompetenceDTO)))
            .andExpect(status().isCreated());

        // Validate the UserCompetence in the database
        List<UserCompetence> userCompetenceList = userCompetenceRepository.findAll();
        assertThat(userCompetenceList).hasSize(databaseSizeBeforeCreate + 1);
        UserCompetence testUserCompetence = userCompetenceList.get(userCompetenceList.size() - 1);
        assertThat(testUserCompetence.getUser().getId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserCompetence.getUserLevel()).isEqualTo(DEFAULT_USER_LEVEL);
    }

    @Test
    @Transactional
    public void createUserCompetenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userCompetenceRepository.findAll().size();

        // Create the UserCompetence with an existing ID
        userCompetence.setId(1L);
        UserCompetenceDTO userCompetenceDTO = userCompetenceMapper.toDto(userCompetence);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCompetenceMockMvc.perform(post("/api/user-competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCompetenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCompetence in the database
        List<UserCompetence> userCompetenceList = userCompetenceRepository.findAll();
        assertThat(userCompetenceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserCompetences() throws Exception {
        // Initialize the database
        userCompetenceRepository.saveAndFlush(userCompetence);

        // Get all the userCompetenceList
        restUserCompetenceMockMvc.perform(get("/api/user-competences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCompetence.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.intValue())))
            .andExpect(jsonPath("$.[*].userLevel").value(hasItem(DEFAULT_USER_LEVEL.doubleValue())));
    }

    @Test
    @Transactional
    public void getUserCompetence() throws Exception {
        // Initialize the database
        userCompetenceRepository.saveAndFlush(userCompetence);

        // Get the userCompetence
        restUserCompetenceMockMvc.perform(get("/api/user-competences/{id}", userCompetence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userCompetence.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.intValue()))
            .andExpect(jsonPath("$.userLevel").value(DEFAULT_USER_LEVEL.doubleValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserCompetence() throws Exception {
        // Get the userCompetence
        restUserCompetenceMockMvc.perform(get("/api/user-competences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserCompetence() throws Exception {
        // Initialize the database
        userCompetenceRepository.saveAndFlush(userCompetence);

        int databaseSizeBeforeUpdate = userCompetenceRepository.findAll().size();

        // Update the userCompetence
        UserCompetence updatedUserCompetence = userCompetenceRepository.findById(userCompetence.getId()).get();
        // Disconnect from session so that the updates on updatedUserCompetence are not directly saved in db
        em.detach(updatedUserCompetence);
        updatedUserCompetence
            .user(new User(UPDATED_USER_ID))
            .userLevel(UPDATED_USER_LEVEL);
        UserCompetenceDTO userCompetenceDTO = userCompetenceMapper.toDto(updatedUserCompetence);

        restUserCompetenceMockMvc.perform(put("/api/user-competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCompetenceDTO)))
            .andExpect(status().isOk());

        // Validate the UserCompetence in the database
        List<UserCompetence> userCompetenceList = userCompetenceRepository.findAll();
        assertThat(userCompetenceList).hasSize(databaseSizeBeforeUpdate);
        UserCompetence testUserCompetence = userCompetenceList.get(userCompetenceList.size() - 1);
        assertThat(testUserCompetence.getUser().getId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserCompetence.getUserLevel()).isEqualTo(UPDATED_USER_LEVEL);
    }

    @Test
    @Transactional
    public void updateNonExistingUserCompetence() throws Exception {
        int databaseSizeBeforeUpdate = userCompetenceRepository.findAll().size();

        // Create the UserCompetence
        UserCompetenceDTO userCompetenceDTO = userCompetenceMapper.toDto(userCompetence);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserCompetenceMockMvc.perform(put("/api/user-competences")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userCompetenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserCompetence in the database
        List<UserCompetence> userCompetenceList = userCompetenceRepository.findAll();
        assertThat(userCompetenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserCompetence() throws Exception {
        // Initialize the database
        userCompetenceRepository.saveAndFlush(userCompetence);

        int databaseSizeBeforeDelete = userCompetenceRepository.findAll().size();

        // Delete the userCompetence
        restUserCompetenceMockMvc.perform(delete("/api/user-competences/{id}", userCompetence.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserCompetence> userCompetenceList = userCompetenceRepository.findAll();
        assertThat(userCompetenceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
