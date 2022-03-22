package com.ivolunteer.user;

import com.ivolunteer.user.dto.MyGoalDTO;
import com.ivolunteer.user.dto.UserGoalsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MyGoalsService {
    Page<MyGoalDTO> findAllForCurrentUser(Pageable pageable);
}
