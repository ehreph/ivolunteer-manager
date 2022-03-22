package com.ivolunteer.repository;

import com.ivolunteer.domain.RuleAttribute;

import com.ivolunteer.domain.enumeration.GlobalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the RuleAttribute entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RuleAttributeRepository extends JpaRepository<RuleAttribute, Long> {

  Page<RuleAttribute> findAllByRuleType(Pageable pageable, GlobalType ruleType);
}
