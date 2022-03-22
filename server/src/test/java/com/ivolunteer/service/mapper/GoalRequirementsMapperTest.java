package com.ivolunteer.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class GoalRequirementsMapperTest {

    private GoalRequirementsMapper goalRequirementsMapper;

    @BeforeEach
    public void setUp() {
        goalRequirementsMapper = new GoalRequirementsMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(goalRequirementsMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(goalRequirementsMapper.fromId(null)).isNull();
    }
}
