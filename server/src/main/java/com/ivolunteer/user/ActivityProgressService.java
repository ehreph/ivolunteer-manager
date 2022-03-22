package com.ivolunteer.user;

import com.ivolunteer.service.dto.ActivityDTO;
import com.ivolunteer.user.dto.ActivityProgressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ActivityProgressService {

    /**
     * Save a activityProgress.
     *
     * @param activityProgressDTO the entity to save.
     * @return the persisted entity.
     */
    ActivityProgressDTO save(ActivityProgressDTO activityProgressDTO);

    /**
     * Get all the activities.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ActivityProgressDTO> findAll(Pageable pageable);



}
