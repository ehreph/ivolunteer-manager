package com.ivolunteer.repository;

import com.ivolunteer.domain.BaseData;
import com.ivolunteer.domain.Competence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Competence entity.
 */
@SuppressWarnings("unused")
//@Repository
@NoRepositoryBean
public interface BaseDataRepository<T extends BaseData, ID> extends JpaRepository<T, ID> {
}
