package com.ivolunteer.repository;

import com.ivolunteer.domain.UserCompetence;

import com.ivolunteer.service.dto.UserCompetenceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Spring Data  repository for the UserCompetence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserCompetenceRepository extends JpaRepository<UserCompetence, Long> {
    Page<UserCompetence> findAllByUserId(Long userId, Pageable pageable);

    @Query("select u.comp.name, u.userLevel from UserCompetence u where u.user.id = :userId")
    List<Tuple> findAllUserCompetencesMap(@Param("userId") Long userId);

    Optional<UserCompetence> findByIdAndUserId(Long id, Long userId);

    Optional<UserCompetence> findByCompIdAndUserId(Long compId, Long userId);

    @Query("SELECT NEW  com.ivolunteer.service.dto.UserCompetenceDTO(uc.id, uc.user.id, uc.userLevel," +
        " uc.comp.id,uc.comp.name)" +
        " FROM UserCompetence uc where uc.user.id =:userId and uc.comp.id IN :ids")
    List<UserCompetenceDTO> findAllByUserIdAndCompetenceIdIn(@Param("userId") Long userId,@Param("ids") List<Long> ids);
}
