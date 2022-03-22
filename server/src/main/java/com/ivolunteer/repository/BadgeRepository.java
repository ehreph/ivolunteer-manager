package com.ivolunteer.repository;

import com.ivolunteer.domain.Badge;

import com.ivolunteer.domain.Competence;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Badge entity.
 */
@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
  @Query("SELECT b FROM Badge b" +
    " WHERE b.id not in ( SELECT ub.badge.id FROM UserBadge ub where ub.user.id =:userId)")
  List<Badge> findAllNewBadgesForUser(@Param("userId") Long userId);
}
