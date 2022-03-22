package com.ivolunteer.user;

import com.ivolunteer.security.AuthoritiesConstants;
import com.ivolunteer.user.dto.StatisticsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

/**
 * REST controller for managing {@link com.ivolunteer.domain.UserGoals}.
 */
@RestController
@RequestMapping("/api")
public class StatisticsResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsResource.class);

    private static final String ENTITY_NAME = "statistics";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticService statisticService;

    public StatisticsResource(StatisticService statisticService) {
        this.statisticService = statisticService;
    }

    /**
     * {@code GET  /statistics} : get user statistics.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userGoals in body.
     */
    @GetMapping("/statistics")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<StatisticsDTO> getUserStatistics() {
        log.debug("REST request to get current User statistics");
        StatisticsDTO statisticsDTO = statisticService.getUserStatistics();
        return ResponseEntity.ok().body(statisticsDTO);
    }

    @GetMapping("/statistics/{userId}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<StatisticsDTO> getUserStatisticsByUserId(@PathVariable("userId") Long userId) {
        log.debug("REST request to get User statistics by user id ");
        StatisticsDTO statisticsDTO = statisticService.getUserStatisticsByUserId(userId);
        return ResponseEntity.ok().body(statisticsDTO);
    }


}
