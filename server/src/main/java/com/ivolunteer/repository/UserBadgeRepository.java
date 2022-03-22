package com.ivolunteer.repository;

import com.ivolunteer.domain.UserBadge;

import com.ivolunteer.service.dto.UserBadgeDTO;
import com.ivolunteer.service.dto.UserCompetenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserBadge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBadgeRepository extends JpaRepository<UserBadge, Long> {

  @Query("SELECT NEW  com.ivolunteer.service.dto.UserBadgeDTO(ub.id, ub.user.id, ub.badge.id,ub.badge.name) " +
    " FROM UserBadge ub where ub.user.id =:userId and ub.badge.id IN :badgeIds")
  List<UserBadgeDTO> findAllByUserIdAndBadgeIdIn(@Param("userId") Long id, @Param("badgeIds") List<Long> badgeIds);


  Optional<UserBadge> findByBadgeIdAndUserId(Long badgeId, Long userId);

  Boolean existsByBadgeIdAndUserId(Long badgeId, Long userId);


  Page<UserBadge> findAllByUserId(Long userId, Pageable pageable);
}
