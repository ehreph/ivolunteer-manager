package com.ivolunteer.web.rest;

import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.Badge;
import com.ivolunteer.repository.BadgeRepository;
import com.ivolunteer.service.BadgeService;
import com.ivolunteer.service.dto.BadgeDTO;
import com.ivolunteer.service.mapper.BadgeMapper;

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
 * Integration tests for the {@link BadgeResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BadgeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private BadgeRepository badgeRepository;

    @Autowired
    private BadgeMapper badgeMapper;

    @Autowired
    private BadgeService badgeService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBadgeMockMvc;

    private Badge badge;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badge createEntity(EntityManager em) {
        Badge badge = new Badge()
            .name(DEFAULT_NAME);
        return badge;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Badge createUpdatedEntity(EntityManager em) {
        Badge badge = new Badge()
            .name(UPDATED_NAME);
        return badge;
    }

    @BeforeEach
    public void initTest() {
        badge = createEntity(em);
    }

    @Test
    @Transactional
    public void createBadge() throws Exception {
        int databaseSizeBeforeCreate = badgeRepository.findAll().size();
        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);
        restBadgeMockMvc.perform(post("/api/badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeDTO)))
            .andExpect(status().isCreated());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeCreate + 1);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createBadgeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = badgeRepository.findAll().size();

        // Create the Badge with an existing ID
        badge.setId(1L);
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBadgeMockMvc.perform(post("/api/badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBadges() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get all the badgeList
        restBadgeMockMvc.perform(get("/api/badges?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(badge.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        // Get the badge
        restBadgeMockMvc.perform(get("/api/badges/{id}", badge.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(badge.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingBadge() throws Exception {
        // Get the badge
        restBadgeMockMvc.perform(get("/api/badges/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Update the badge
        Badge updatedBadge = badgeRepository.findById(badge.getId()).get();
        // Disconnect from session so that the updates on updatedBadge are not directly saved in db
        em.detach(updatedBadge);
        updatedBadge
            .name(UPDATED_NAME);
        BadgeDTO badgeDTO = badgeMapper.toDto(updatedBadge);

        restBadgeMockMvc.perform(put("/api/badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeDTO)))
            .andExpect(status().isOk());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
        Badge testBadge = badgeList.get(badgeList.size() - 1);
        assertThat(testBadge.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingBadge() throws Exception {
        int databaseSizeBeforeUpdate = badgeRepository.findAll().size();

        // Create the Badge
        BadgeDTO badgeDTO = badgeMapper.toDto(badge);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBadgeMockMvc.perform(put("/api/badges")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(badgeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Badge in the database
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBadge() throws Exception {
        // Initialize the database
        badgeRepository.saveAndFlush(badge);

        int databaseSizeBeforeDelete = badgeRepository.findAll().size();

        // Delete the badge
        restBadgeMockMvc.perform(delete("/api/badges/{id}", badge.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Badge> badgeList = badgeRepository.findAll();
        assertThat(badgeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
