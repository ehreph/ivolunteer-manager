package com.ivolunteer.user.impl;

import com.ivolunteer.domain.User;
import com.ivolunteer.domain.UserGoals;
import com.ivolunteer.repository.*;
import com.ivolunteer.service.UserService;
import com.ivolunteer.service.mapper.UserGoalsMapper;
import com.ivolunteer.user.GoalEntityService;
import com.ivolunteer.user.StatisticService;
import com.ivolunteer.user.dto.StatisticsDTO;
import com.ivolunteer.user.mapper.ActivityProgressMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Tuple;
import java.time.*;
import java.time.temporal.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserGoals}.
 */
@Service
@Transactional
public class StatisticServiceImpl implements StatisticService {

  private final Logger log = LoggerFactory.getLogger(StatisticServiceImpl.class);

  private final UserGoalsRepository userGoalsRepository;
  private final UserCompetenceRepository userCompetenceRepository;
  private final UserBadgeRepository userBadgeRepository;
  private final ActivityProgressRepository activityProgressRepository;
  private final UserService userService;
  private final GoalRepository goalRepository;
  private final UserGoalsMapper userGoalsMapper;
  private final GoalEntityService goalEntityService;
  private final ActivityProgressMapper activityProgressMapper;


  static final Map<String, TemporalAdjuster> ADJUSTERS = new HashMap<>();


  public StatisticServiceImpl(UserGoalsRepository userGoalsRepository,
                              UserCompetenceRepository userCompetenceRepository,
                              UserBadgeRepository userBadgeRepository,
                              ActivityProgressRepository activityProgressRepository,
                              UserService userService, GoalRepository goalRepository,
                              UserGoalsMapper userGoalsMapper, GoalEntityService goalEntityService,
                              ActivityProgressMapper activityProgressMapper) {
    this.userGoalsRepository = userGoalsRepository;
    this.userCompetenceRepository = userCompetenceRepository;
    this.userBadgeRepository = userBadgeRepository;
    this.activityProgressRepository = activityProgressRepository;
    this.userService = userService;
    this.goalRepository = goalRepository;
    this.userGoalsMapper = userGoalsMapper;
    this.goalEntityService = goalEntityService;
    this.activityProgressMapper = activityProgressMapper;

    ADJUSTERS.put("day", TemporalAdjusters.ofDateAdjuster(d -> d)); // identity
    ADJUSTERS.put("week", TemporalAdjusters.previousOrSame(DayOfWeek.of(1)));
    ADJUSTERS.put("month", TemporalAdjusters.firstDayOfMonth());
    ADJUSTERS.put("year", TemporalAdjusters.firstDayOfYear());
  }


  @Override
  @Transactional(readOnly = true)
  public StatisticsDTO getUserStatistics() {
    log.info("Request user Statistics");
    User user = userService.getUserWithAuthorities().get();
    return calculateUserStatistics(user);
  }

  @Override
  @Transactional(readOnly = true)
  public StatisticsDTO getUserStatisticsByUserId(Long userId) {
    log.info("Request user Statistics of user: " + userId);
    User user = userService.findById(userId);
    return calculateUserStatistics(user);
  }


  private StatisticsDTO calculateUserStatistics(User user) {
    StatisticsDTO statistics = new StatisticsDTO();
    statistics.setUserId(user.getId());
    statistics.setFirstName(user.getFirstName());
    statistics.setLastName(user.getLastName());

    List<UserGoals> finishedUserGoals = userGoalsRepository.findAllByUserIdAndFinishedTrue(user.getId());
    statistics.setGoals((long) finishedUserGoals.size());
    statistics.setFinishedGoals(finishedUserGoals.stream().filter(userGoals1 -> userGoals1.getFinished()).count());
    statistics.setOpenGoals(userGoalsRepository.countAllByUserIdAndFinishedFalse(user.getId()));


//        Map<LocalDate, List<UserGoals>> result = userGoals.stream().filter(userGoals1 -> userGoals1.getFinished())
//            .collect(Collectors.groupingBy(item -> new LocalDate(item.getFinishedDate())
//                .with(ADJUSTERS.get("month"))));

    if (finishedUserGoals != null && !finishedUserGoals.isEmpty()) {
      //calc per day
      final Map<Instant, List<UserGoals>> dayEntries =
        finishedUserGoals.stream().collect(Collectors.groupingBy(goal ->
          goal.getFinishedDate().truncatedTo(ChronoUnit.DAYS)));

      Map<Instant, Long> dayEntriesCount = calculateEntryCount(dayEntries);
      statistics.setFinishedGoalsPerDate(dayEntriesCount);

      //calc per month
      final Map<LocalDateTime, List<UserGoals>> monthEntries =
        finishedUserGoals.stream().collect(Collectors.groupingBy(goal ->
          LocalDateTime.ofInstant(goal.getFinishedDate(), ZoneOffset.UTC)
            .with(TemporalAdjusters.firstDayOfMonth())
            .truncatedTo(ChronoUnit.DAYS)
        ));
      Map<Instant, Long> monthEntriesCount = new TreeMap<>();
      monthEntries.forEach(
        (date, list) -> monthEntriesCount.merge(date.toInstant(ZoneOffset.UTC), (long) list.size(), Long::sum));


//            Map<Instant, Long> monthEntriesCount = calculateEntryCount(monthEntries);
      statistics.setFinishedGoalsPerMonth(monthEntriesCount);
    }
    List<Tuple> competences = userCompetenceRepository.findAllUserCompetencesMap(user.getId());
    Map<String, Double> competencesMap = competences.stream()
      .collect(Collectors.toMap(
        t -> t.get(0, String.class),
        t -> t.get(1, Double.class)));
    statistics.setLevelPerCompetence(competencesMap);

    return statistics;
  }

  private Map<Instant, Long> calculateEntryCount(Map<Instant, List<UserGoals>> dayEntries) {
    Map<Instant, Long> entryCount = new TreeMap<>();
    dayEntries.forEach((date, list) -> entryCount.merge(date, (long) list.size(), Long::sum));
    return entryCount;
  }

}
