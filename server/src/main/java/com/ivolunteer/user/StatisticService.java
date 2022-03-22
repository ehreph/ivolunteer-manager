package com.ivolunteer.user;

import com.ivolunteer.user.dto.StatisticsDTO;

public interface StatisticService {
    StatisticsDTO getUserStatistics();
    StatisticsDTO getUserStatisticsByUserId(Long userId);
}
