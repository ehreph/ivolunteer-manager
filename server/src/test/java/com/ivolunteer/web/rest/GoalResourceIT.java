package com.ivolunteer.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ivolunteer.IvolunteerManagerApp;
import com.ivolunteer.domain.*;
import com.ivolunteer.domain.enumeration.GlobalType;
import com.ivolunteer.domain.enumeration.RuleOperator;
import com.ivolunteer.repository.GoalRepository;
import com.ivolunteer.service.GoalService;
import com.ivolunteer.service.dto.GoalAwardDTO;
import com.ivolunteer.service.dto.GoalDTO;
import com.ivolunteer.service.dto.GoalRequirementsDTO;
import com.ivolunteer.service.mapper.GoalMapper;

import com.ivolunteer.user.dto.ActivityProgressDTO;
import com.ivolunteer.user.dto.UserGoalsDTO;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Integration tests for the {@link GoalResource} REST controller.
 */
@SpringBootTest(classes = IvolunteerManagerApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GoalResourceIT {

  private static final String DEFAULT_NAME = "AAAAAAAAAA";
  private static final String UPDATED_NAME = "BBBBBBBBBB";

  private static final String DEFAULT_INFO = "AAAAAAAAAA";
  private static final String UPDATED_INFO = "BBBBBBBBBB";

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private GoalRepository goalRepository;

  @Autowired
  private GoalMapper goalMapper;

  @Autowired
  private GoalService goalService;

  @Autowired
  private EntityManager em;

  @Autowired
  private MockMvc restGoalMockMvc;


  @BeforeEach
  public void setup() {
    objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Create an entity for this test.
   * <p>
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Goal createEntity(EntityManager em) {
    Goal goal = new Goal()
      .name(DEFAULT_NAME)
      .info(DEFAULT_INFO);
    goal.setIsPersonal(false);
    return goal;
  }

  /**
   * Create an updated entity for this test.
   * <p>
   * This is a static method, as tests for other entities might also need it,
   * if they test an entity which requires the current entity.
   */
  public static Goal createUpdatedEntity(EntityManager em) {
    Goal goal = new Goal()
      .name(UPDATED_NAME)
      .info(UPDATED_INFO);
    return goal;
  }


  @Nested
  @SpringBootTest(classes = IvolunteerManagerApp.class)
  @AutoConfigureMockMvc
  @WithMockUser
  @Transactional
  class BasicGoalCreationTests {

    private Goal goal;

    @BeforeEach
    public void initTest() {
      goal = createEntity(em);
    }

    @Test
    @Transactional
    public void createGoal_withoutAnything() throws Exception {
      int databaseSizeBeforeCreate = goalRepository.findAll().size();
      // Create the Goal
      GoalDTO goalDTO = goalMapper.toDto(goal);
      performSaveGoal(goalDTO, post("/api/goals"), status().isCreated());

      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertGoalWasCreated(goalList, databaseSizeBeforeCreate + 1, DEFAULT_NAME, DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createGoal_withAward_Competence() throws Exception {

      GoalAward award = createGoalAwardCompetence();
      goal.getAwardedCompetences().add(award);

      GoalDTO goalDTO = goalMapper.toDto(goal);
      String json = performSaveGoal(goalDTO, post("/api/goals"), status().isCreated())
        .andReturn().getResponse().getContentAsString();
      goalDTO = objectMapper.readValue(json, GoalDTO.class);
      GoalAwardDTO goalAwardDTO = goalDTO.getAwardedCompetences().get(0);

      restGoalMockMvc.perform(get("/api/goals/{id}", goalDTO.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(goalDTO.getId()))
        .andExpect(jsonPath("$.name").value(goalDTO.getName()))
        .andExpect(jsonPath("$.info").value(goalDTO.getInfo()))
        .andExpect(jsonPath("$.awardedCompetences[0].awardType").value(award.getAwardType().name()))
        .andExpect(jsonPath("$.awardedCompetences[0].generalId").value(award.getData().getId()))
        .andExpect(jsonPath("$.awardedCompetences[0].increaseLevel").value(award.getIncreaseLevel()))
        .andExpect(jsonPath("$.awardedCompetences[0].goalId").value(goalDTO.getId()));

    }

    @Test
    @Transactional
    public void createGoal_withAward_Badge() throws Exception {

      GoalAward award = createGoalAwardBadge();
      goal.getAwardedCompetences().add(award);

      GoalDTO goalDTO = goalMapper.toDto(goal);
      String json = performSaveGoal(goalDTO, post("/api/goals"), status().isCreated())
        .andReturn().getResponse().getContentAsString();
      goalDTO = objectMapper.readValue(json, GoalDTO.class);
      GoalAwardDTO goalAwardDTO = goalDTO.getAwardedCompetences().get(0);

      restGoalMockMvc.perform(get("/api/goals/{id}", goalDTO.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(goalDTO.getId()))
        .andExpect(jsonPath("$.name").value(goalDTO.getName()))
        .andExpect(jsonPath("$.info").value(goalDTO.getInfo()))
        .andExpect(jsonPath("$.awardedCompetences[0].awardType").value(award.getAwardType().name()))
        .andExpect(jsonPath("$.awardedCompetences[0].generalId").value(award.getData().getId()))
        .andExpect(jsonPath("$.awardedCompetences[0].increaseLevel").value(award.getIncreaseLevel()))
        .andExpect(jsonPath("$.awardedCompetences[0].goalId").value(goalDTO.getId()));

    }


    @Test
    @Transactional
    public void createGoal_withRequirements_Competence() throws Exception {
      GoalRequirements requirement = createGoalRequirementCompetence();

      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);

      String json = performSaveGoal(goalDTO, post("/api/goals"), status().isCreated())
        .andReturn().getResponse().getContentAsString();
      goalDTO = objectMapper.readValue(json, GoalDTO.class);
      GoalRequirementsDTO requirementsDTO = goalDTO.getRequirements().get(0);

      restGoalMockMvc.perform(get("/api/goals/{id}", goalDTO.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(goalDTO.getId()))
        .andExpect(jsonPath("$.name").value(goalDTO.getName()))
        .andExpect(jsonPath("$.info").value(goalDTO.getInfo()))
        .andExpect(jsonPath("$.requirements[0].type").value(requirement.getType().name()))
        .andExpect(jsonPath("$.requirements[0].operator").value(requirement.getOperator().name()))
        .andExpect(jsonPath("$.requirements[0].value").value(requirement.getValue()))
        .andExpect(jsonPath("$.requirements[0].goalId").value(requirementsDTO.getGoalId()))
        .andExpect(jsonPath("$.requirements[0].actStartDate").value(requirement.getActStartDate()))
        .andExpect(jsonPath("$.requirements[0].actEndDate").value(requirement.getActEndDate()));
    }


    @Test
    @Transactional
    public void createGoal_withRequirements_Badge() throws Exception {
      GoalRequirements requirement = createGoalRequirementBadge();

      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);

      String json = performSaveGoal(goalDTO, post("/api/goals"), status().isCreated())
        .andReturn().getResponse().getContentAsString();
      goalDTO = objectMapper.readValue(json, GoalDTO.class);
      GoalRequirementsDTO requirementsDTO = goalDTO.getRequirements().get(0);

      restGoalMockMvc.perform(get("/api/goals/{id}", goalDTO.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(goalDTO.getId()))
        .andExpect(jsonPath("$.name").value(goalDTO.getName()))
        .andExpect(jsonPath("$.info").value(goalDTO.getInfo()))
        .andExpect(jsonPath("$.requirements[0].type").value(requirement.getType().name()))
        .andExpect(jsonPath("$.requirements[0].operator").value(requirement.getOperator().name()))
        .andExpect(jsonPath("$.requirements[0].value").value(requirement.getValue()))
        .andExpect(jsonPath("$.requirements[0].goalId").value(requirementsDTO.getGoalId()))
        .andExpect(jsonPath("$.requirements[0].actStartDate").value(requirement.getActStartDate()))
        .andExpect(jsonPath("$.requirements[0].actEndDate").value(requirement.getActEndDate()));
    }


    @Test
    @Transactional
    public void createGoal_withRequirements_Goal() throws Exception {
      GoalRequirements requirement = createGoalRequirementGoal();

      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);

      String json = performSaveGoal(goalDTO, post("/api/goals"), status().isCreated())
        .andReturn().getResponse().getContentAsString();
      goalDTO = objectMapper.readValue(json, GoalDTO.class);
      GoalRequirementsDTO requirementsDTO = goalDTO.getRequirements().get(0);

      restGoalMockMvc.perform(get("/api/goals/{id}", goalDTO.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(goalDTO.getId()))
        .andExpect(jsonPath("$.name").value(goalDTO.getName()))
        .andExpect(jsonPath("$.info").value(goalDTO.getInfo()))
        .andExpect(jsonPath("$.requirements[0].type").value(requirement.getType().name()))
        .andExpect(jsonPath("$.requirements[0].operator").value(requirement.getOperator().name()))
        .andExpect(jsonPath("$.requirements[0].value").value(requirement.getValue()))
        .andExpect(jsonPath("$.requirements[0].goalId").value(requirementsDTO.getGoalId()))
        .andExpect(jsonPath("$.requirements[0].actStartDate").value(requirement.getActStartDate()))
        .andExpect(jsonPath("$.requirements[0].actEndDate").value(requirement.getActEndDate()));
    }


    @Test
    @Transactional
    public void createGoal_withRequirements_Activity() throws Exception {
      GoalRequirements requirement = createGoalRequirementActivity();

      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);

      String json = performSaveGoal(goalDTO, post("/api/goals"), status().isCreated())
        .andReturn().getResponse().getContentAsString();
      goalDTO = objectMapper.readValue(json, GoalDTO.class);
      GoalRequirementsDTO requirementsDTO = goalDTO.getRequirements().get(0);

      restGoalMockMvc.perform(get("/api/goals/{id}", goalDTO.getId()))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.id").value(goalDTO.getId()))
        .andExpect(jsonPath("$.name").value(goalDTO.getName()))
        .andExpect(jsonPath("$.info").value(goalDTO.getInfo()))
        .andExpect(jsonPath("$.requirements[0].type").value(requirement.getType().name()))
        .andExpect(jsonPath("$.requirements[0].operator").value(requirement.getOperator().name()))
        .andExpect(jsonPath("$.requirements[0].value").value(requirement.getValue()))
        .andExpect(jsonPath("$.requirements[0].attributesId").value(requirement.getAttributes().getId()))
        .andExpect(jsonPath("$.requirements[0].goalId").value(requirementsDTO.getGoalId()));
      // TODO comparison of Date fails because of of presented format
//        .andExpect(jsonPath("$.requirements[0].actStartDate").value(requirement.getActStartDate()))
//        .andExpect(jsonPath("$.requirements[0].actEndDate").value(requirement.getActEndDate()));
    }

    @Test
    @Transactional
    public void updateGoal_withRequirements() throws Exception {
      int databaseSizeBeforeCreate = goalRepository.findAll().size();

      GoalRequirements requirement = createGoalRequirementCompetence();


      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);
      performSaveGoal(goalDTO, post("/api/goals"), status().isCreated());

      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
      Goal testGoal = goalList.get(goalList.size() - 1);
      assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
      assertThat(testGoal.getInfo()).isEqualTo(DEFAULT_INFO);
      assertThat(testGoal.getRequirements().size()).isEqualTo(1);
      GoalRequirements genReq = testGoal.getRequirements().get(0);
      assertGoalRequirement(requirement, genReq);
    }


    @Test
    @Transactional
    public void updateGoal_withRequirements_requirementIsNotDuplicatedOnUpdate() throws Exception {
      int databaseSizeBeforeCreate = goalRepository.findAll().size();

      GoalRequirements requirement = createGoalRequirementCompetence();


      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);
      performSaveGoal(goalDTO, post("/api/goals"), status().isCreated());

      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
      Goal testGoal = goalList.get(goalList.size() - 1);
      assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
      assertThat(testGoal.getInfo()).isEqualTo(DEFAULT_INFO);
      assertThat(testGoal.getRequirements().size()).isEqualTo(1);
      GoalRequirements genReq = testGoal.getRequirements().get(0);
      assertGoalRequirement(requirement, genReq);

      performSaveGoal(goalMapper.toDto(testGoal), put("/api/goals"), status().isOk());

      //validate requirement is not duplicated
      goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
      testGoal = goalList.get(goalList.size() - 1);
      assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
      assertThat(testGoal.getInfo()).isEqualTo(DEFAULT_INFO);
      assertThat(testGoal.getRequirements().size()).isEqualTo(1);
      genReq = testGoal.getRequirements().get(0);
      assertGoalRequirement(requirement, genReq);
    }


    @Test
    @Transactional
    public void updateGoal_removeRequirement() throws Exception {
      int databaseSizeBeforeCreate = goalRepository.findAll().size();

      GoalRequirements requirement = createGoalRequirementCompetence();


      goal.getRequirements().add(requirement);

      GoalDTO goalDTO = goalMapper.toDto(goal);
      performSaveGoal(goalDTO, post("/api/goals"), status().isCreated());

      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
      Goal testGoal = goalList.get(goalList.size() - 1);
      assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
      assertThat(testGoal.getInfo()).isEqualTo(DEFAULT_INFO);
      assertThat(testGoal.getRequirements().size()).isEqualTo(1);
      GoalRequirements genReq = testGoal.getRequirements().get(0);
      assertGoalRequirement(requirement, genReq);

      em.detach(testGoal);
      testGoal.getRequirements().clear();
      testGoal.setRequirements(new ArrayList<>());
      performSaveGoal(goalMapper.toDto(testGoal), put("/api/goals"), status().isOk());

      // validate requirement was removed

      testGoal = goalRepository.findById(testGoal.getId()).get();
      assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
      assertThat(testGoal.getInfo()).isEqualTo(DEFAULT_INFO);
      assertThat(testGoal.getRequirements()).isNull();

    }


    @Test
    @Transactional
    public void createGoalWithExistingId() throws Exception {
      int databaseSizeBeforeCreate = goalRepository.findAll().size();

      // Create the Goal with an existing ID
      goal.setId(1L);
      GoalDTO goalDTO = goalMapper.toDto(goal);

      // An entity with an existing ID cannot be created, so this API call must fail
      performSaveGoal(goalDTO, post("/api/goals"), status().isBadRequest());

      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void updateGoal() throws Exception {
      // Initialize the database
      goalRepository.saveAndFlush(goal);

      int databaseSizeBeforeUpdate = goalRepository.findAll().size();

      // Update the goal
      Goal updatedGoal = goalRepository.findById(goal.getId()).get();
      // Disconnect from session so that the updates on updatedGoal are not directly saved in db
      em.detach(updatedGoal);
      updatedGoal
        .name(UPDATED_NAME)
        .info(UPDATED_INFO);
      GoalDTO goalDTO = goalMapper.toDto(updatedGoal);

      performSaveGoal(goalDTO, put("/api/goals"), status().isOk());

      // Validate the Goal in the database
      updatedGoal = goalRepository.findById(updatedGoal.getId()).get();
      assertGoalWasCreated(updatedGoal, UPDATED_NAME, UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingGoal() throws Exception {
      int databaseSizeBeforeUpdate = goalRepository.findAll().size();

      // Create the Goal
      GoalDTO goalDTO = goalMapper.toDto(goal);

      // If the entity doesn't have an ID, it will throw BadRequestAlertException
      performSaveGoal(goalDTO, put("/api/goals"), status().isBadRequest());

      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGoal() throws Exception {
      // Initialize the database
      goalRepository.saveAndFlush(goal);

      int databaseSizeBeforeDelete = goalRepository.findAll().size();

      // Delete the goal
      restGoalMockMvc.perform(delete("/api/goals/{id}", goal.getId())
          .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent());

      // Validate the database contains one less item
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Nested
    @SpringBootTest(classes = IvolunteerManagerApp.class)
    @AutoConfigureMockMvc
    @WithMockUser
    @Transactional
    class GoalRequestTests {

      @BeforeEach
      public void setup() {

      }

      @Test
      @Transactional
      public void getAllGoals() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get all the goalList
        restGoalMockMvc.perform(get("/api/goals?sort=id,desc"))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(jsonPath("$.[*].id").value(hasItem(goal.getId().intValue())))
          .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
          .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO)));
      }

      @Test
      @Transactional
      public void getGoal() throws Exception {
        // Initialize the database
        goalRepository.saveAndFlush(goal);

        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", goal.getId()))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
          .andExpect(jsonPath("$.id").value(goal.getId().intValue()))
          .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
          .andExpect(jsonPath("$.info").value(DEFAULT_INFO));
      }

      @Test
      @Transactional
      public void getNonExistingGoal() throws Exception {
        // Get the goal
        restGoalMockMvc.perform(get("/api/goals/{id}", Long.MAX_VALUE))
          .andExpect(status().isNotFound());
      }
    }


  }


  @Nested
  @SpringBootTest(classes = IvolunteerManagerApp.class)
  @AutoConfigureMockMvc
  @WithMockUser
  @Transactional
  class GoalCreationActions {

    public static final String API_USER_GOALS_SUBSCRIBE_GOAL = "/api/user-goals/subscribe/goal/{goalId}";
    public static final String API_USER_GOALS_FINISH = "/api/user-goals/{id}/finish";
    public static final String API_GET_MY_GOALS = "/api/my-goals/";
    public static final String API_POST_ACTIVITY_PROGRESS = "/api/activity/progress";

    private int databaseSizeBeforeCreate;
    private Goal goal;
    private GoalRequirements requirement;
    private GoalDTO createdGoal;

    @BeforeEach
    @Transactional
    public void setup() throws Exception {
      goal = createEntity(em);

      databaseSizeBeforeCreate = goalRepository.findAll().size();
      requirement = createGoalRequirementActivity();
      goal.getRequirements().add(requirement);
      GoalDTO goalDTO = goalMapper.toDto(goal);
      String json = performSaveGoal(goalDTO, post("/api/goals"),
        status().isCreated()).andReturn().getResponse().getContentAsString();
      this.createdGoal = objectMapper.readValue(json, GoalDTO.class);
    }

    @Test
    @Transactional
    public void goal_setupTest_withActivityRequirements() throws Exception {
      // Validate the Goal in the database
      List<Goal> goalList = goalRepository.findAll();
      assertThat(goalList).hasSize(databaseSizeBeforeCreate + 1);
      Goal testGoal = goalList.get(goalList.size() - 1);
      assertThat(testGoal.getName()).isEqualTo(DEFAULT_NAME);
      assertThat(testGoal.getInfo()).isEqualTo(DEFAULT_INFO);
      assertThat(testGoal.getRequirements().size()).isEqualTo(1);
      GoalRequirements genReq = testGoal.getRequirements().get(0);
      assertGoalRequirement(requirement, genReq);
    }

    @Test
    @Transactional
    @WithMockUser
    public void goal_subscribeUserToGoal() throws Exception {
      requestSubscribeUserToGoal(status().isCreated());
    }

    @Test
    @Transactional
    @WithMockUser
    public void goal_subscribeUserToGoal_alreadySubscribed() throws Exception {
      // TODO Test for subsribing user to goal if already finished
      requestSubscribeUserToGoal(status().isCreated());
      requestSubscribe(status().isInternalServerError());

    }


    @Test
    @Transactional
    @WithMockUser
    public void goal_finishUserGoal_notAchieved() throws Exception {
      String json = requestSubscribeUserToGoal(status().isCreated());

      UserGoalsDTO userGoals = objectMapper.readValue(json, UserGoalsDTO.class);

      //TODO this test should work
      requestFinishUserGoal(userGoals.getId(), status().isInternalServerError());

    }

    @Test
    @Transactional
    @WithMockUser
    public void goal_finishUserGoal_achieved() throws Exception {
      String json = requestSubscribeUserToGoal(status().isCreated());

      UserGoalsDTO userGoals = objectMapper.readValue(json, UserGoalsDTO.class);

      ActivityProgressDTO progressDTO = createActivityProgress(requirement.getData().getId(), 11d);

      requestPostActivityProgress(progressDTO);

      requestFinishUserGoal(userGoals.getId(), status().isOk());


    }

    @NotNull
    private void requestPostActivityProgress(ActivityProgressDTO progressDTO) throws Exception {
      restGoalMockMvc.perform(post(API_POST_ACTIVITY_PROGRESS)
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtil.convertObjectToJsonBytes(progressDTO)))
        .andExpect(status().isCreated());
    }

    @NotNull
    private ActivityProgressDTO createActivityProgress(Long activityId, Double value) {
      ActivityProgressDTO progressDTO = new ActivityProgressDTO();
      progressDTO.setActivityId(activityId);
      progressDTO.setValue(value);
      progressDTO.setDate(Instant.now().plus(1, ChronoUnit.DAYS));
      // attributesId not needed in model
      progressDTO.setAttributesId(null);
      return progressDTO;
    }

    @Test
    @Transactional
    @WithMockUser
    public void goal_finishUserGoal_achieved_resubscribe() throws Exception {
      String json = requestSubscribeUserToGoal(status().isCreated());

      UserGoalsDTO userGoals = objectMapper.readValue(json, UserGoalsDTO.class);

      ActivityProgressDTO progressDTO = createActivityProgress(requirement.getData().getId(), 11d);
      requestPostActivityProgress(progressDTO);

      requestFinishUserGoal(userGoals.getId(), status().isOk());
      requestSubscribe(status().isInternalServerError());
    }


    @NotNull
    private void requestFinishUserGoal(Long id, ResultMatcher status) throws Exception {
      restGoalMockMvc.perform(post(API_USER_GOALS_FINISH, id)
          .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status);
    }

    @NotNull
    private ResultActions requestSubscribe(ResultMatcher status) throws Exception {
      return restGoalMockMvc.perform(post(API_USER_GOALS_SUBSCRIBE_GOAL, createdGoal.getId())
          .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status);
    }

    @NotNull
    private String requestSubscribeUserToGoal(ResultMatcher status) throws Exception {
      return requestSubscribe(status)
        .andExpect(jsonPath("$.goalId").value(createdGoal.getId()))
        .andExpect(jsonPath("$.goalName").value(createdGoal.getName()))
        .andExpect(jsonPath("$.finished").value(false)
        ).andReturn().getResponse().getContentAsString();
    }


    @Test
    @Transactional
    @WithMockUser
    public void myGoals_noGoalSubscribed() throws Exception {
      restGoalMockMvc.perform(get(API_GET_MY_GOALS))
        .andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    @WithMockUser
    public void my_goals_empty_after_goal_finished() throws Exception {

      String json = requestSubscribeUserToGoal(status().isCreated());

      UserGoalsDTO userGoals = objectMapper.readValue(json, UserGoalsDTO.class);

      ActivityProgressDTO progressDTO = createActivityProgress(requirement.getData().getId(), 11d);
      requestPostActivityProgress(progressDTO);

      requestFinishUserGoal(userGoals.getId(), status().isOk());

      restGoalMockMvc.perform(get(API_GET_MY_GOALS))
        .andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());

    }


    @Test
    @Transactional
    @WithMockUser
    public void my_goals_goalCorrectlyReturned() throws Exception {
      String json = requestSubscribeUserToGoal(status().isCreated());

      restGoalMockMvc.perform(get(API_GET_MY_GOALS))
        .andExpect(status().isOk()).andDo(print())
        .andExpect(jsonPath("$[0].goal.id").value(createdGoal.getId()))
        .andExpect(jsonPath("$[0].goal.name").value("AAAAAAAAAA"))
        .andExpect(jsonPath("$[0].goal.requirements").isArray())
        .andExpect(jsonPath("$[0].goal.requirements[0].type").value("ACTIVITY"))
        .andExpect(jsonPath("$[0].goal.requirements[0].operator").value("FINISHED"))
        .andExpect(jsonPath("$[0].goal.requirements[0].attributesUnitName").value("km"))
        .andExpect(jsonPath("$[0].goal.requirements[0].entityName").value("Running"))
        .andExpect(jsonPath("$[0].goal.requirements[0].value").value(10.0d))
        .andExpect(jsonPath("$[0].userActivities[0].userId").value(4))
        .andExpect(jsonPath("$[0].userActivities[0].name").value("Running"))
        .andExpect(jsonPath("$[0].userActivities[0].attributeName").value("Kilometer"))
        .andExpect(jsonPath("$[0].userActivities[0].attributeUnitName").value("km"))
        .andExpect(jsonPath("$[0].userActivities[0].progressAmount").value(0.0d));


      ActivityProgressDTO progressDTO = createActivityProgress(requirement.getData().getId(), 11d);
      requestPostActivityProgress(progressDTO);

      restGoalMockMvc.perform(get(API_GET_MY_GOALS))
        .andExpect(status().isOk()).andDo(print())
        .andExpect(jsonPath("$[0].goal.id").value(createdGoal.getId()))
        .andExpect(jsonPath("$[0].goal.name").value("AAAAAAAAAA"))
        .andExpect(jsonPath("$[0].goal.requirements").isArray())
        .andExpect(jsonPath("$[0].goal.requirements[0].type").value("ACTIVITY"))
        .andExpect(jsonPath("$[0].goal.requirements[0].operator").value("FINISHED"))
        .andExpect(jsonPath("$[0].goal.requirements[0].attributesUnitName").value("km"))
        .andExpect(jsonPath("$[0].goal.requirements[0].entityName").value("Running"))
        .andExpect(jsonPath("$[0].goal.requirements[0].value").value(10.0d))
        .andExpect(jsonPath("$[0].userActivities[0].userId").value(4))
        .andExpect(jsonPath("$[0].userActivities[0].name").value("Running"))
        .andExpect(jsonPath("$[0].userActivities[0].attributeName").value("Kilometer"))
        .andExpect(jsonPath("$[0].userActivities[0].attributeUnitName").value("km"))
        .andExpect(jsonPath("$[0].userActivities[0].progressAmount").value(11.0d));


    }
  }

  // TODO Test for finishing user Goal


  private void assertGoalAward(GoalAward award, GoalAward genAward) {
    assertThat(genAward.getId()).isNotNull();
    assertThat(genAward.getAwardType()).isEqualTo(award.getAwardType());
    assertThat(genAward.getData().getId()).isEqualTo(award.getData().getId());
    assertThat(genAward.getIncreaseLevel()).isEqualTo(award.getIncreaseLevel());
  }


  @NotNull
  private GoalAward createGoalAwardCompetence() {
    Competence c = new Competence();
    c.setName("Test entity");
    em.persist(c);

    GoalAward award = new GoalAward();
    award.setAwardType(GlobalType.COMPETENCE);
    award.setData(c);
    award.setIncreaseLevel(1d);
    return award;
  }

  @NotNull
  private GoalAward createGoalAwardBadge() {
    Badge badge = new Badge();
    badge.setName("Reward Badge");
    em.persist(badge);

    GoalAward award = new GoalAward();
    award.setAwardType(GlobalType.BADGE);
    award.setData(badge);
    return award;
  }


  @NotNull
  private GoalRequirements createGoalRequirementCompetence() {
    Competence c = new Competence();
    c.setName("Test entity");
    em.persist(c);

    GoalRequirements requirement = new GoalRequirements();

    requirement.setData(c);
    requirement.setOperator(RuleOperator.BIGGER_THAN);
    requirement.setType(GlobalType.COMPETENCE);
    requirement.setValue(2d);
    return requirement;
  }

  @NotNull
  private GoalRequirements createGoalRequirementActivity() {
    Activity a = new Activity();
    a.setName("Running");
    a.setAttributes(createRuleAttribute());
    em.persist(a);

    GoalRequirements requirement = new GoalRequirements();
    requirement.setData(a);
    requirement.setOperator(RuleOperator.FINISHED);
    requirement.setType(GlobalType.ACTIVITY);
    requirement.setAttributes(a.getAttributes());
    requirement.setActStartDate(Instant.now());
    requirement.setActEndDate(Instant.now().plus(30, ChronoUnit.DAYS));
    requirement.setValue(10d);
    return requirement;
  }

  @NotNull
  private GoalRequirements createGoalRequirementGoal() {
    Goal g = new Goal();
    g.setName("Requirement Goal");
    g.setInfo("This is a test goal");
    g.setIsPersonal(false);
    em.persist(g);

    GoalRequirements requirement = new GoalRequirements();
    requirement.setData(g);
    requirement.setOperator(RuleOperator.FINISHED);
    requirement.setType(GlobalType.GOAL);
    return requirement;
  }

  @NotNull
  private GoalRequirements createGoalRequirementBadge() {
    Badge b = new Badge();
    b.setName("Requirement Badge");
    em.persist(b);

    GoalRequirements requirement = new GoalRequirements();
    requirement.setData(b);
    requirement.setOperator(RuleOperator.AWARDED);
    requirement.setType(GlobalType.BADGE);
    return requirement;
  }


  @NotNull
  private RuleAttribute createRuleAttribute() {
    RuleAttribute ra = new RuleAttribute();
    ra.setName("Kilometer");
    ra.setRuleType(GlobalType.ACTIVITY);
    ra.setUnitName("km");
    em.persist(ra);
    return ra;
  }


  private ResultActions performSaveGoal(GoalDTO goalDTO, MockHttpServletRequestBuilder post,
                                        ResultMatcher status) throws Exception {
    return restGoalMockMvc.perform(post
        .contentType(MediaType.APPLICATION_JSON)
        .content(TestUtil.convertObjectToJsonBytes(goalDTO)))
      .andExpect(status);
  }


  //------------------ asserts ----------------------

  private void assertGoalWasCreated(List<Goal> goalList, int i, String defaultName, String defaultInfo) {
    assertThat(goalList).hasSize(i);
    Goal testGoal = goalList.get(goalList.size() - 1);
    assertThat(testGoal.getName()).isEqualTo(defaultName);
    assertThat(testGoal.getInfo()).isEqualTo(defaultInfo);
  }


  private void assertGoalWasCreated(Goal goal, String defaultName, String defaultInfo) {
    assertThat(goal.getName()).isEqualTo(defaultName);
    assertThat(goal.getInfo()).isEqualTo(defaultInfo);
  }


  private void assertGoalRequirement(GoalRequirements expected, GoalRequirements actual) {
    assertThat(actual.getId()).isNotNull();
    assertThat(actual.getOperator()).isEqualTo(expected.getOperator());
    assertThat(actual.getType()).isEqualTo(expected.getType());
  }
}
